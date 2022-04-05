<%-- 
    Document   : editSession
    Created on : 5 Apr 2022, 5:52:42 pm
    Author     : zhaoyiwu
--%>


<%@page import="ee.hooka.User"%>
<%@page import="ee.hooka.Session"%>
<%@page import="ee.hooka.Question"%>
<%@page import="ee.hooka.Option"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <title>HooKa - Edit Session</title>
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
            
            <h1>Edit "" Session </h1>
            

        </div>
        
    </body>
</html>
