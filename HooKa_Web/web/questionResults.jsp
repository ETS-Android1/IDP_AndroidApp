<%-- 
    Document   : barChart_QnResults
    Created on : 6 Apr 2022, 11:43:11 pm
    Author     : zhaoyiwu
--%>

<%@page import="ee.hooka.User"%>
<%@page import="ee.hooka.Session"%>
<%@page import="ee.hooka.Question"%>
<%@page import="ee.hooka.Option"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Timer"%>
<%@page import="java.util.TimerTask"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <title>HooKa - Sessions</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <jsp:include page="startSession" />
        <% 
                User c = (User) session.getAttribute("user");
            %>
        
        <div class="navbar">
<!--            <a href="index.jsp" ><img src="images/ZacZee's-logo-nav.png" alt="ZacZee's" width="30" Height="30"/></a>-->
             
            
            <% if(c == null){ %>
            
            <a href="register.jsp" >Register</a>
            <a href="login.jsp" >Login</a>
            
            <% }else{ %>
            
<!--            <form class="navForm" action="search" method="post" style="float: left">
                <input type="submit" value="Products"/>
            </form>
            <form class="navForm" action="cart" method="get" style="float: left">
                <input type="submit" value="Cart"/>
            </form> 
            <form class="navForm" action="profile" method="get" style="float: left">
                <input type="submit" value="Profile"/>
            </form>-->
<!--            <form class="navForm" action="logout" method="post">
                <input type="submit" value="Logout"/>
            </form>-->
            
            <% } %>
        </div>
        
        <br/><br/><br/>
        <div class="display">
            <% if(c == null){ %>
            <h1>Welcome to HooKa, Login to get started!</h1>

            <% }else{ 

            Question question = (Question) session.getAttribute("questionTobeAired");
            
            %>
            
            <h1>Question <%=question.getQnNumber()%>:</h1>
            <h1><%=question.getQnDesc()%></h1>
            
            <div>
            <% 
            if(question.getOptions() == null || question.getOptions().size() <= 0){
            %>
            <tr><td colspan="1">(No options)</td></tr>
            <%
            }else{
                for(Option option:question.getOptions()){
                %>
                
                <tr>
                    <td>
                        <%if(question.getAnswer().equals(option.getOptionLetter())){%>
                        
                        <font color="green"><%=option.getOptionLetter()%>.  <%=option.getOptionDesc()%></font><br>
                        
                        <%} else {%>
                        
                        <%=option.getOptionLetter()%>.  <%=option.getOptionDesc()%><br>
                        
                        <%}%>
                    </td>
                </tr>
                
                <%
                }
                
            }
            %>
            </div>
            
            <canvas id="myChart" style="width:100%;max-width:600px"></canvas>

            <%

            int[] counts = (int[]) session.getAttribute("resultCount");
            
            %>
            
            <script>
                var xValues = [];
                var yValues = [];
                var barColors = [];
                
                <%

                    for(int i=0;i<counts.length;i++){%>
                        xValues.push("<%=question.getOptions().get(i).getOptionLetter()%> (<%=counts[i]%>)");
                        yValues.push(<%=counts[i]%>);
                        barColors.push("purple");
                   <%}

                %>

                new Chart("myChart", {
                  type: "bar",
                  data: {
                    labels: xValues,
                    datasets: [{
                      backgroundColor: barColors,
                      data: yValues
                    }]
                  },
                  options: {
                    legend: {display: false},
                    title: {
                      display: true,
                      text: "Your Responses"
                    }
                  }
                });
            </script>
            
            <div>
            <form action="retrieveQuestion" method="post">
                <input type="submit" value="Next Question"/>
            </form>
            </div>
            
            <% } %>
            
        </div>
        
            
    </body>
</html>
