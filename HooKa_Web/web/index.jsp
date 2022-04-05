
<%@page import="ee.hooka.User"%>
<%@page import="ee.hooka.Session"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <title>HooKa - Sessions</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        
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
            <form class="navForm" action="logout" method="post">
                <input type="submit" value="Logout"/>
            </form>
            
            <% } %>
        </div>
        
        <br/><br/><br/>
        <div class="display">
            <% if(c == null){ %>
            <h1>Welcome to HooKa, Login to get started!</h1>

            <% }else{ %>
            
            <h1><%=c.getFullName()%>'s sessions</h1>
            
            <form action="search" method="Post">
            <p>
                <div class="searchField">
                <input type="text" placeholder="Search by Session Name" name="searchterm"" required>
                <button class="searchBttn" type="submit" value="Search"/><img icon="search" src="images/search.ico" width="20px" height='20px'/></button><br/>
                </div>
            </p>
            <br/><br/>
            </form>
            
                        <button><a href="newSession.jsp" >+ Create New</a></button>

            
            <div style="text-align: center">
                <font color="red">
                        <%=request.getAttribute("message")==null?"":request.getAttribute("message")%><br/>
                </font>
                <br/>
            </div>
            
            <div class="outergrid">
            <% 
            List<Session> searchresult = (ArrayList<Session>) session.getAttribute("searchresult");
            
            if(searchresult == null || searchresult.size() <= 0){
            %>
            <tr><td colspan="5">(No sessions is found)</td></tr>
            <%
            }else{
                for(Session createdSession:searchresult){
                %>
                
                    <div class="innergrid">
                        <div><%=createdSession.getSessionName()%></div>
                        <div>
                            <form action="editSession" method="post">
                            <input type="hidden" name="sessionId" value="<%=createdSession.getSessionId()%>"/>
                            <input type="submit" value="Edit"/>
                            </form>
                            
                            <form action="startSession" method="post">
                            <input type="hidden" name="sessionId" value="<%=createdSession.getSessionId()%>"/>
                            <input type="submit" value="Start"/>
                            </form>
                        </div>
                    </div>
                
                <%
                }
                
            }
            %>
            </div>
            
            <% } %>
                
            <!--<div class="displaySub">    
                <div><h2>New Arrivals</h2></div>
                <div><img src="images/landingPageDisplay.jpg" alt="chuck 70 high tops" width=85%/></div>
                <div><img src="images/landingPageDisplay2.jpg" alt="yeezy slides onxy" width=90%/></div>
            </div>-->
        </div>
        
    </body>
</html>
