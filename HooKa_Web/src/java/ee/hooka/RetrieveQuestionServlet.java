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
@WebServlet("/retrieveQuestion")
public class RetrieveQuestionServlet extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
        
        //get session totalQns and status
        HttpSession session = request.getSession();
        int sessionInProgress = Integer.parseInt(session.getAttribute("sessionInProgress").toString());
        
        Question question = new Question();
        int sessionRunningStatus = -2;
        int totalQns = -1;
        boolean airNextQn = true;
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        String sqlSelect = "SELECT * FROM session WHERE sessionId = ?";

        try {

            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "xxxx");

            statement = connection.prepareStatement(sqlSelect);
            statement.setInt(1,sessionInProgress);
            resultset = statement.executeQuery();

            resultset.next();
            sessionRunningStatus = resultset.getInt("sessionRunningStatus");
            totalQns = resultset.getInt("totalQns");

            //if status<totalQns 
            if(sessionRunningStatus<totalQns){
                //increase session status by 1
                sessionRunningStatus++; //current qn to air
                String sqlUpdate = "UPDATE session set sessionRunningStatus = ? where sessionId = ?";

                connection.setAutoCommit(false);
                statement = connection.prepareStatement(sqlUpdate);
                statement.setInt(1, sessionRunningStatus);
                statement.setInt(2, sessionInProgress);
                statement.executeUpdate();
                connection.commit();

                //update qn accesibility status to 0 (qn airing status)
                sqlUpdate = "UPDATE questions set `accessible` = 0 where (sessionId = ?) and (qnNumber = ?)";

                connection.setAutoCommit(false);
                statement = connection.prepareStatement(sqlUpdate);
                statement.setInt(1, sessionInProgress);
                statement.setInt(2,sessionRunningStatus);
                statement.executeUpdate();
                connection.commit();

                //get qn&options with updated status
                sqlSelect = "SELECT * FROM questions WHERE (sessionId = ?) and (qnNumber = ?)";

                statement = connection.prepareStatement(sqlSelect);
                statement.setInt(1,sessionInProgress);
                statement.setInt(2,sessionRunningStatus);
                resultset = statement.executeQuery();

                //qn
                resultset.next();
                
                question.setQnId(resultset.getInt("qnId"));
                question.setSessionId(resultset.getInt("sessionId"));
                question.setQnNumber(resultset.getInt("qnNumber"));
                question.setQnDesc(resultset.getString("qnDesc"));
                question.setAnswer(resultset.getString("answer"));
                question.setAccessible(resultset.getBoolean("accessible"));
                
                //options
                ArrayList<Option> options = new ArrayList<Option>();

                sqlSelect = "SELECT * FROM options WHERE qnId = ?";
                statement = connection.prepareStatement(sqlSelect);
                statement.setInt(1,resultset.getInt("qnId"));
                ResultSet optionsResultset = statement.executeQuery();

                while(optionsResultset.next()){

                Option option = new Option();

                option.setOptionId(optionsResultset.getInt("optionId"));
                option.setQnId(optionsResultset.getInt("qnId"));
                option.setSessionId(optionsResultset.getInt("sessionId"));
                option.setOptionLetter(optionsResultset.getString("optionLetter"));
                option.setOptionDesc(optionsResultset.getString("optionDesc"));

                options.add(option);

                }

                question.setOptions(options);
                
                //---test-----
//                request.setAttribute("message", 
//                                    "question options: " + question.getOptions().get(0).getOptionDesc());
//                    RequestDispatcher rd = request.getRequestDispatcher("/sessionLobby.jsp");
//                    rd.forward(request, response);
                ///

            }else{
                //completed last qn
                //redirect to final result page?
//                RequestDispatcher rd = request.getRequestDispatcher("/computeFinalResult");
//                rd.forward(request, response);
                airNextQn = false;
                
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

            //redirect to qn page
            if(airNextQn){
                session.setAttribute("questionTobeAired", (Object) question);
                response.sendRedirect(this.getServletContext().getContextPath() + "/question.jsp");
            }else{
                RequestDispatcher rd = request.getRequestDispatcher("/endSession");
                rd.forward(request, response);
            }
            
        }

    }
    
}
