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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhaoyiwu
 */
@WebServlet("/computeQuestionResults")
public class ComputeQuestionResults extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        int sessionInProgress = Integer.parseInt(session.getAttribute("sessionInProgress").toString());
        Question question = (Question) session.getAttribute("questionTobeAired");
        
        int qnId = question.getQnId();
        
        //row = choiceIndex, count
        int[] resultCount = new int[question.getOptions().size()];
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        String sqlSelect = "SELECT count(*) FROM response WHERE (sessionId = ?) and (qnId = ?) and (choice = ?)";
        
        try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");
                
                // loop through each option
                for(int i=0;i<question.getOptions().size();i++){
                    
                    String choice = question.getOptions().get(i).getOptionLetter();
                    
                    statement = connection.prepareStatement(sqlSelect);
                    statement.setInt(1,sessionInProgress);
                    statement.setInt(2,qnId);
                    statement.setString(3,choice);
                    resultset = statement.executeQuery();
                    
                    resultset.next();
                
                    int count = resultset.getInt("count(*)");
                    resultCount[i] = count;
                    
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
            
            session.setAttribute("resultCount", (Object) resultCount);
            response.sendRedirect(this.getServletContext().getContextPath() + "/questionResults.jsp");

        }
        
    }
    
}
