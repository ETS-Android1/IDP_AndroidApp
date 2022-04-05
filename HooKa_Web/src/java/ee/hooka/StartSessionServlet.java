/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ee.hooka;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhaoyiwu
 */

@WebServlet("/startSession")
public class StartSessionServlet  extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
            
            String sessionId = request.getParameter("sessionId");
            
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultset = null;
            List<String> students = new ArrayList<String>();
            
            String sqlSelect = "SELECT * FROM user WHERE (userType = Student) and (joinedSession = ?)";
            
            try {

                // Get the connection from the DataSource
                connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                            "root", "xxxx");
                // Create a statement using the Connection
                statement = connection.prepareStatement(sqlSelect);
                statement.setString(1,sessionId);
                resultset = statement.executeQuery();

                //resultset is like a pointer
                while(resultset.next()){
                    students.add(resultset.getString("fullname"));
                }
            
            } catch (Exception ex) {
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
            
            HttpSession session = request.getSession();
            session.setAttribute("students", (ArrayList) students);
            response.sendRedirect(this.getServletContext().getContextPath() + "/sessionLobby.jsp");
            
        }
}
