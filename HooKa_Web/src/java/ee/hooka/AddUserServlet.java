/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.hooka;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.DriverManager;

/**
 *
 * @author fepit
 */
@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            String fullName = request.getParameter("fullName");
            String mobile = request.getParameter("mobile");
            String password = request.getParameter("password");
            
            StringBuilder hexPassword;
            byte[] hashedPassword;
            MessageDigest digest;
        try {
            
            digest = MessageDigest.getInstance("SHA-256");
            hashedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array into signum representation  
            BigInteger number = new BigInteger(1, hashedPassword);  

            // Convert message digest into hex value  
            hexPassword = new StringBuilder(number.toString(16));
            
            String sqlInsertUser = "INSERT INTO user (userType, fullName, mobile, password) VALUES(?, ?, ?, ?)";

           //Declare the connection, prepared statement and resultset objects
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultset = null;
            
            try {
            
            // Get the connection from the DataSource
            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
            //Set auto commit to false to control the transaction
            connection.setAutoCommit(false);
            // Create a statement using the Connection
            preparedStatement = connection.prepareStatement(sqlInsertUser);
            
            preparedStatement.setString(1, "Intructor");
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, mobile);
            preparedStatement.setString(4, hexPassword.toString());
            
            preparedStatement.executeUpdate();
            connection.commit();
            
            //"login after registering"
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE mobile = ?");
            preparedStatement.setString(1, mobile);
            resultset = preparedStatement.executeQuery();
            
            resultset.next();
            User user = new User();
                
                user.setId(resultset.getInt("userId"));
                user.setFullName(resultset.getString("fullName"));
                user.setMobile(resultset.getString("mobile"));
                user.setMobile(resultset.getString("joinedSession"));
            
            HttpSession session = request.getSession();
            session.setAttribute("customer",user);
            
            ///response.sendRedirect(this.getServletContext().getContextPath() + "/index.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("/search");
            rd.forward(request, response);
            
        } catch (SQLException ex) {
            
            request
                   .setAttribute("message",
                    "An error has occured. Please try again");
            
            RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
            rd.forward(request, response);

            try {
                //Roll back if there is an error
                connection.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                System.err.println(ex1.getMessage());
            }
            ex.printStackTrace();
            System.err.println(ex.getMessage());

        } finally{
                
            if(resultset != null){
                try {
                    resultset.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        }
    
}
