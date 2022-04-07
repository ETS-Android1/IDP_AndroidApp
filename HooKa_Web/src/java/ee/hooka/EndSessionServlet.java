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
import java.util.ArrayList;
import java.util.List;
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
        
        List<ScoreboardLine> scoreboard = new ArrayList<ScoreboardLine>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        
        HttpSession session = request.getSession();
        int sessionInProgress = Integer.parseInt(session.getAttribute("sessionInProgress").toString());
        
        Question question = (Question) session.getAttribute("questionTobeAired");

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
                
                sqlUpdate = "UPDATE questions set `accessible` = -1 where (sessionId = ?) and (qnNumber = ?)";

                connection.setAutoCommit(false);
                statement = connection.prepareStatement(sqlUpdate);
                statement.setInt(1, sessionInProgress);
                statement.setInt(2,question.getQnNumber());
                statement.executeUpdate();
                connection.commit();
                
                //search for scoreboard
                
                String sqlSelect = "Select A.fullname as fullname, sum(B.points) as totalPoints\n" +
                                    "from user as A inner join response as B\n" +
                                    "on A.userId = B.userId\n" +
                                    "Where sessionId = ?\n" +
                                    "group by fullname\n" +
                                    "Order by totalPoints desc;";
                
                statement = connection.prepareStatement(sqlSelect);
                statement.setInt(1,sessionInProgress);
                resultset = statement.executeQuery();
                
                while(resultset.next()){
                        
                        ScoreboardLine line = new ScoreboardLine();
                        
                        line.setFullname(resultset.getString("fullname"));
                        line.setTotalPoints(resultset.getInt("totalPoints"));

                        scoreboard.add(line);
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
            
            session.setAttribute("scoreboard", scoreboard);
            session.setAttribute("sessionInProgress", null);
            response.sendRedirect(this.getServletContext().getContextPath() + "/endOfSession.jsp");

        }
    }
}
