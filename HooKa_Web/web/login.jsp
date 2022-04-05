<%@page import="ee.hooka.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css">
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <title>HooKa - Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <% 
                User c = (User) session.getAttribute("user");
            %>
        
        <div class="navbar">
<!--                <a href="index.jsp" ><img src="images/ZacZee's-logo-nav.png" alt="ZacZee's" width="30" Height="30"/></a>-->

                <% if(c == null){ %>

                <a href="register.jsp" >Register</a>
                <a href="login.jsp" >Login</a>

                <% }else{ %>

<!--                <form class="navForm" action="search" method="post" style="float: left">
                    <input type="submit" value="Products"/>
                </form>
                <form class="navForm" action="cart" method="get" style="float: left">
                    <input type="submit" value="Cart"/>
                </form> 
                <form class="navForm" action="profile" method="get" style="float: left">
                    <input type="submit" value="Profile"/>
                </form> -->
                <form class="navForm" action="logout" method="post">
                    <input type="submit" value="Logout"/>
                </form>

                <% } %>
        </div>
        
        <br/><br/><br/>
        <h1>Login</h1>
        <div style="text-align: center">
            <font color="red">
                        <%=request.getAttribute("message")==null?"":request.getAttribute("message")%><br/>
            </font>
            <br/>
        </div>
        <form class="formborder" name="customer" action="authenticate" method="post">
            <div class="container">
                <label for="mobile"><b>Mobile Number</b></label><br/>
                <input type="mobile" placeholder="Enter mobile" name="mobile" required><br/>
                
                <label for="password"><b>Password</b></label><br/>
                <input type="password" placeholder="Enter Password" name="password" required><br/>
                
                <button class="loginRegiBttn" type="submit">Login</button>
            </div>
        </form>
    </body>
</html>
