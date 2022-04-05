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
@WebServlet("/createSession")
public class CreateSessionServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            
            String sessionName = request.getParameter("sessionName");
            
            HttpSession reqSession = request.getSession();
            User user = (User) reqSession.getAttribute("user");
            int userId = user.getId();
            
            String sqlInsert = "INSERT INTO session (userId, sessionName, sessionPin, sessionRunningStatus) VALUES(?, ?, ?, ?)";

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
            preparedStatement = connection.prepareStatement(sqlInsert);
            
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, sessionName);
            preparedStatement.setInt(3, -1);
            preparedStatement.setInt(4, 0);
            
            preparedStatement.executeUpdate();
            connection.commit();
            
            //"login after registering"
            preparedStatement = connection.prepareStatement("SELECT * FROM session WHERE userId = ? ORDER BY ID DESC LIMIT 1");
            preparedStatement.setInt(1, userId);
            resultset = preparedStatement.executeQuery();
            
            resultset.next();
            Session newSession = new Session();
                
            newSession.setSessionId(resultset.getInt("sessionId"));
            newSession.setUserId(resultset.getInt("userId"));
            newSession.setSessionName(resultset.getString("sessionName"));
            newSession.setSessionPin(resultset.getInt("sessionPin"));
                
            HttpSession session = request.getSession();
            session.setAttribute("newSession",newSession);
            
            RequestDispatcher rd = request.getRequestDispatcher("/edit");
            rd.forward(request, response);
            
        } catch (SQLException ex) {
            
            request
                   .setAttribute("message",
                    "An error has occured. Please try again");
            
            RequestDispatcher rd = request.getRequestDispatcher("/newSession.jsp");
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
            
        }
    
}
