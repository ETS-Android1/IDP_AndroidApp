/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.hooka;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author fepit
 */

@WebServlet("/validate")
public class ValidationServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            
            String email = request.getParameter("email");
            EmailValidator emailValidator;
            emailValidator = EmailValidator.getInstance();
        
            if(emailValidator.isValid(email)) {
                
                boolean unique = true;
            
            //Declare connection, preparedstatement and resultset
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultset = null;
            
            try {
                //Get the connection, prepare the statement and run the query
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/shoeshop?allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root", "xxxx");
                preparedStatement = connection.prepareStatement("SELECT count(*) FROM customer WHERE email = ?");
                preparedStatement.setString(1, email);
                resultset = preparedStatement.executeQuery();
                
                //The SQL query will definitely yield one single row of result.
                //A while clause is not necessary
                resultset.next();
                int count = resultset.getInt(1);
                
                if(count >= 1) {
                    //A book with the same isbn has been found in the records
                    unique = false;
                }
            } catch(SQLException ex) {
                ex.printStackTrace();
            } finally {
                //Resultset, Statement and Connection are closed in the finally 
                // clause to ensure that they will be closed no matter what 
                // happens to the system.
                if(resultset != null) {
                    try {
                        resultset.close();
                    } catch(SQLException ex) {
                        ex.printStackTrace();
                        System.err.println(ex.getMessage());
                    }
                }
                if(preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch(SQLException ex) {
                        ex.printStackTrace();
                        System.err.println(ex.getMessage());
                    }
                }
                if(connection != null) {
                    try {
                        connection.close();
                    } catch(SQLException ex) {
                        ex.printStackTrace();
                        System.err.println(ex.getMessage());
                    }
                }
                
                //Now that database operations has been settled, process the routing
                if(unique) {
                    //If the book is unique, continue to add it in the database
                    RequestDispatcher rd = request.getRequestDispatcher("/adduser");
                    rd.forward(request, response);
                } else {
                    //else (ie. the book has been added before, return to add.jsp)
                    request.setAttribute("message", 
                                    "The email, \"" 
                                    + request.getParameter("email") 
                                    + "\", has already been registered.");
                    RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
                    rd.forward(request, response);
                }
            }
                
            }else{
                request
                   .setAttribute("message",
                    "Email entered is not valid");
                
                RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
                rd.forward(request, response);
            }
            
        }
    
}
