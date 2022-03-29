
<%@page import="ee.hooka.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        
        <% 
                Customer c = (Customer) session.getAttribute("customer");
            %>
        
        <div class="navbar">
            <a href="index.jsp" ><img src="images/ZacZee's-logo-nav.png" alt="ZacZee's" width="30" Height="30"/></a>
             
            
            <% if(c == null){ %>
            
            <a href="register.jsp" >Register</a>
            <a href="login.jsp" >Login</a>
            
            <% }else{ %>
            
            <form class="navForm" action="search" method="post" style="float: left">
                <input type="submit" value="Products"/>
            </form>
            <form class="navForm" action="cart" method="get" style="float: left">
                <input type="submit" value="Cart"/>
            </form> 
            <form class="navForm" action="profile" method="get" style="float: left">
                <input type="submit" value="Profile"/>
            </form>
            <form class="navForm" action="logout" method="post">
                <input type="submit" value="Logout"/>
            </form>
            
            <% } %>
        </div>
        
        <br/><br/><br/>
        <div class="display">
            <% if(c == null){ %>
            <h1>Welcome to ZacZee's!</h1>

            <% }else{ %>
            
            <h1>Welcome to ZacZee's, <%=c.getFullName()%>!</h1>
            <% } %>
                
            <div class="displaySub">    
                <div><h2>New Arrivals</h2></div>
                <div><img src="images/landingPageDisplay.jpg" alt="chuck 70 high tops" width=85%/></div>
                <div><img src="images/landingPageDisplay2.jpg" alt="yeezy slides onxy" width=90%/></div>
            </div>
        </div>
        
    </body>
</html>
