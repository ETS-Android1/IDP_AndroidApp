
<%@page import="ee.hooka.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos_white.png"/> <%--Favicon--%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
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
        <h1>Change Password</h1>
        <div style="text-align: center">
            <font color="red">
                    <%=request.getAttribute("message")==null?"":request.getAttribute("message")%><br/>
            </font>
            <br/>
        </div>
        
        <form class="formborder" name="changePassword" action="changePassword" method="post">
            <div class="container">
                <%--
                Current Password: <input type="password" name="currentPassword" required/><br/>
                <br/>
                New Password: <input type="password" name="newPassword" required/><br/>
                Confirm New Password: <input type="password" name="confirmPassword" required/><br/>

                <input type="submit" value="Save"/>
                --%>
                
                <label for="currentPassword"><b>Current Password</b></label><br/>
                <input type="password" placeholder="Enter Current Password" name="currentPassword" required><br/>
                
                <label for="newPassword"><b>New Password</b></label><br/>
                <input type="password" placeholder="Enter New Password" name="newPassword" required><br/>
                
                <label for="confirmPassword"><b>Confirm New Password</b></label><br/>
                <input type="password" placeholder="Enter New Password Again" name="confirmPassword" required><br/>
                
                <button class="loginRegiBttn" type="submit">Save</button>
            </div>
        </form>
    </body>
</html>
