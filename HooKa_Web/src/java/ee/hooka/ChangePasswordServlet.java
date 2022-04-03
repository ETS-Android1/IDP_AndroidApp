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
@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
        try {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultset = null;
            
            HttpSession session = request.getSession();
            
            User customer = (User) session.getAttribute("customer");
            
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            
            StringBuilder hexPassword;
            StringBuilder hexNewPassword;
            byte[] hashedPassword;
            MessageDigest digest;
            
            digest = MessageDigest.getInstance("SHA-256");
            hashedPassword = digest.digest(currentPassword.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hashedPassword);
            
            // Convert message digest into hex value
            hexPassword = new StringBuilder(number.toString(16));
            
            
            //hash input
            //check if current password is correct
            //if yes, hash update with new password
            
            try {

                    connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
                    connection.setAutoCommit(false);
                    
                    preparedStatement = connection.prepareStatement("SELECT password FROM customer WHERE customerId = ?");
                    preparedStatement.setInt(1, customer.getId());
                    resultset = preparedStatement.executeQuery();
                    
                    resultset.next();
                    String customerPassword = resultset.getString(1);
                    
                    if(customerPassword.equals(hexPassword.toString()))
                    {
                        if(newPassword.equals(confirmPassword))
                        {
                            digest = MessageDigest.getInstance("SHA-256");
                            hashedPassword = digest.digest(newPassword.getBytes(StandardCharsets.UTF_8));

                            // Convert byte array into signum representation
                            number = new BigInteger(1, hashedPassword);

                            // Convert message digest into hex value
                            hexNewPassword = new StringBuilder(number.toString(16));
                            
                            preparedStatement = connection.prepareStatement("UPDATE customer SET password = ? WHERE customerId = ?");
                            preparedStatement.setString(1, hexNewPassword.toString());
                            preparedStatement.setInt(2, customer.getId());
                            preparedStatement.executeUpdate();
                            connection.commit();
                            
                            request
                               .setAttribute("message",
                                "Password successfully updated!");
                        }
                        else
                        {
                            request
                               .setAttribute("message",
                                "new password and confirm password does not match. Please try again");
                        }
                    }
                    else
                    {
                        request
                               .setAttribute("message",
                                "Wrong current password. Please try again");
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
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/changePassword.jsp");
                    rd.forward(request, response);
                }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
    
}
