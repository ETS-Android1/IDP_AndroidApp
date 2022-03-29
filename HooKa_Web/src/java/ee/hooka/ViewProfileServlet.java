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
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.Resource;
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

@WebServlet("/profile")
public class ViewProfileServlet extends HttpServlet{
    
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultset = null;
            int customerPoints = 0;
            HttpSession session = request.getSession();
            
            //retrieve customerId from session Customer
            Customer c = (Customer) session.getAttribute("customer");
            int customerId = c.getId();
            
            try {
            
            // Get the connection from the DataSource
            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/shoeshop?allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root", "xxxx");
            //Set auto commit to false to control the transaction
            connection.setAutoCommit(false);
            // Create a statement using the Connection
            
            preparedStatement = connection.prepareStatement("SELECT orderpoints from orders where customerId = ?");
            preparedStatement.setInt(1, customerId);
            resultset = preparedStatement.executeQuery();
            
            while(resultset.next()){
                customerPoints += resultset.getInt(1);
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
            
            
            
            session.setAttribute("customerPoints",customerPoints);
            response.sendRedirect(this.getServletContext().getContextPath() + "/profile.jsp");
        }
            
        }
    
}
