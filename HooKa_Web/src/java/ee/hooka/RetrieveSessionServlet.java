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

@WebServlet("/retrieveSession")
public class RetrieveSessionServlet extends HttpServlet{
    
    @Override
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultset = null;
            ResultSet optionsResultset = null;
            List<Question> questions = new ArrayList<Question>();
            
            String sqlSelect = "SELECT * FROM questions WHERE sessionId = ?";
            
            String sessionToEdit = request.getParameter("sessionToEdit");
            int sessionToEditId = 0;
            
            if(sessionToEdit != null){
                sessionToEditId = Integer.parseInt(sessionToEdit);
            } else {
                HttpSession reqSession = request.getSession();
                Session newSession = (Session) reqSession.getAttribute("newSession");
                sessionToEditId = newSession.getSessionId();
                
            }
            
            try {
            
                // Get the connection from the DataSource
                connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                            "root", "xxxx");
                // Create a statement using the Connection
                statement = connection.prepareStatement(sqlSelect);

                statement.setInt(1,sessionToEditId);
                
                resultset = statement.executeQuery();
                
                //resultset is like a pointer
                while(resultset.next()){

                    Question question = new Question();

                    question.setQnId(resultset.getInt("qnId"));
                    question.setSessionId(resultset.getInt("sessionId"));
                    question.setQnDesc(resultset.getString("qnDesc"));
                    question.setAnswer(resultset.getString("answer"));
                    question.setAccessible(resultset.getBoolean("accessible"));
                    
                    ArrayList<Option> options = new ArrayList<Option>();
                    
                    sqlSelect = "SELECT * FROM questions WHERE sessionId = ?";
                    statement = connection.prepareStatement(sqlSelect);
                    statement.setInt(1,sessionToEditId);

                    optionsResultset = statement.executeQuery();
                    
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

                    questions.add(question);
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
            session.setAttribute("questions", (Object) questions);
            response.sendRedirect(this.getServletContext().getContextPath() + "/editSession.jsp");
            
        }
    
}
