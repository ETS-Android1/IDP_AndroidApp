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

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultset = null;
                
                HttpSession session = request.getSession();
                
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
                
                try {

                    connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
                    
                    preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE mobile = ?");
                    preparedStatement.setString(1, mobile);
                    resultset = preparedStatement.executeQuery();
                    
                    resultset.next();
                    String userPassword = resultset.getString("password");
                    
                    if(userPassword.equals(hexPassword.toString()))
                    {
                        User user = new User();
                
                        user.setId(resultset.getInt("userId"));
                        user.setFullName(resultset.getString("fullname"));
                        user.setMobile(resultset.getString("mobile"));
                        user.setMobile(resultset.getString("joinedSession"));

                        session.setAttribute("user",user);
                        
                        //response.sendRedirect(this.getServletContext().getContextPath() + "/index.jsp");
                        RequestDispatcher rd = request.getRequestDispatcher("/search");
                        rd.forward(request, response);
                    }
                    else
                    {
                        request
                               .setAttribute("message",
                                "Wrong mobile number or password. Please try again");
                        
                        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                        rd.forward(request, response);
                    }
                       
                    
                } catch (SQLException ex) {

                    
                    try {
                        //Roll back if there is an error
                        connection.rollback();
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                        System.err.println(ex1.getMessage());
                    }
                    ex.printStackTrace();
                    System.err.println(ex.getMessage());
                    
                    request
                               .setAttribute("message",
                                "Wrong email or password. Please try again");
                        
                        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                        rd.forward(request, response);
                    
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
            

            Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);

        }
            
        }
    
}
