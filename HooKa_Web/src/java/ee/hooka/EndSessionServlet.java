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

@WebServlet("/endSession")
public class EndSessionServlet extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        
        HttpSession session = request.getSession();
        int sessionInProgress = Integer.parseInt(session.getAttribute("sessionInProgress").toString());

        String sqlUpdate = "UPDATE session SET sessionRunningStatus = -1 WHERE sessionId = ?";
        
        try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
            
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(sqlUpdate);
                statement.setInt(1, sessionInProgress);
                statement.executeUpdate();
                connection.commit();
            
            session.setAttribute("sessionInProgress", null);
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchSessionsServlet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        } finally{

            if(resultset != null){
                try {
                    resultset.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SearchSessionsServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SearchSessionsServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SearchSessionsServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            response.sendRedirect(this.getServletContext().getContextPath() + "/endOfSession.jsp");

        }
    }
}
