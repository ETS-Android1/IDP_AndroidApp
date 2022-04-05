<%@page import="ee.hooka.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos-white.png"/> <%--Favicon--%>
        <title>HooKa - New Session</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="js/validation.js" type="text/javascript"></script>
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
            </form> -->
            <form class="navForm" action="logout" method="post">
                <input type="submit" value="Logout"/>
            </form>
            
            <% } %>
        </div>
        <br/><br/><br/>
        <h1>New Session</h1>
        <div style="text-align: center">
                <font color="red">
                        <%=request.getAttribute("message")==null?"":request.getAttribute("message")%><br/>
                </font>
                <br/>
        </div>
        <form class="formborder" name="session" action="createSession" method="post">
            
            <div class="container">
                <label for="sessionName"><b>Session Name</b></label><br/>
                <input type="text" placeholder="Enter Session Name" name="sessionName" required><br/>
                <input type="hidden" name="productId" value="<%=c.getId()%>"/>
                
                <button class="loginRegiBttn" type="submit" value="create">Create</button>
            </div>
        </form>
    </body>
</html>
