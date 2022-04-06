/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ee.hooka;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhaoyiwu
 */
@WebServlet("/storeSession")
public class StoreStartedSessionServlet extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {

        // store started session id in session
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        HttpSession session = request.getSession();
        session.setAttribute("sessionInProgress",sessionId);
        
        //update session status to 0 (waiting page status)
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        
        String sqlUpdate = "UPDATE session set sessionRunningStatus = 0 where sessionId = ?";
        
        try {
            
            // Get the connection from the DataSource
            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
            
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setInt(1, sessionId);
            preparedStatement.executeUpdate();
            connection.commit();
            
            
            
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
            
            RequestDispatcher rd = request.getRequestDispatcher("/startSession");
            rd.forward(request, response);
        }
        
        

    }
    
}
