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
import java.util.ArrayList;
import java.util.List;
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

@WebServlet("/search")
public class SearchSessionsServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultset = null;
            List<Session> results = new ArrayList<Session>();
            
            HttpSession reqSession = request.getSession();
            User user = (User) reqSession.getAttribute("user");
            int userId = user.getId();
            
            String searchterm = request.getParameter("searchterm");
            String sqlSelect = "SELECT * FROM session WHERE (sessionName LIKE ?) and (userId = ?)";
            
            if(searchterm != null)
            {
                try {
            
                    // Get the connection from the DataSource
                    connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/shoeshop?allowPublicKeyRetrieval=true&serverTimezone=UTC",
                                "root", "xxxx");
                    // Create a statement using the Connection
                    statement = connection.prepareStatement(sqlSelect);

                    statement.setString(1, "%" + searchterm + "%");
                    statement.setInt(1,userId);
                    
                    // Make a query to the DB using ResultSet through the Statement
                    resultset = statement.executeQuery();

                    //resultset is like a pointer
                    while(resultset.next()){
                        
                        Session session = new Session();
                        
                        session.setSessionId(resultset.getInt("sessionId"));
                        session.setUserId(resultset.getInt("userId"));
                        session.setSessionName(resultset.getString("sessionName"));
                        session.setSessionRunningStatus(resultset.getInt("sessionRunningStatus"));

                        results.add(session);
                    }
            
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
            
        }
            }
            else
            {
                try {
            
            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/shoeshop?allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root", "xxxx");
            sqlSelect = "SELECT * FROM shoes";
            statement = connection.prepareStatement(sqlSelect);
            resultset = statement.executeQuery();
            
            //resultset is like a pointer
            while(resultset.next()){
                
                Session session = new Session();
                
                session.setSessionId(resultset.getInt("sessionId"));
                session.setUserId(resultset.getInt("userId"));
                session.setSessionName(resultset.getString("sessionName"));
                session.setSessionRunningStatus(resultset.getInt("sessionRunningStatus"));

                results.add(session);
            }
            
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
            
        }
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("searchresult", (Object) results);
            response.sendRedirect(this.getServletContext().getContextPath() + "/products.jsp");
            
        }
    
    
}
