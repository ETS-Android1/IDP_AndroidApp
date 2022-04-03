<%@page import="ee.hooka.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/master.css"> <%--Link to css--%>
        <link rel="icon" type="image/png" href="images/ZacZee's-logos-white.png"/> <%--Favicon--%>
        <title>Registration Form</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="js/validation.js" type="text/javascript"></script>
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
        <h1>Instructor Registration</h1>
        <div style="text-align: center">
                <font color="red">
                        <%=request.getAttribute("message")==null?"":request.getAttribute("message")%><br/>
                </font>
                <br/>
        </div>
        <form class="formborder" name="customer" action="validate" method="post" onsubmit="return validateForm()">
            
            <div class="container">
                <label for="fullName"><b>Full Name</b></label><br/>
                <input type="text" placeholder="Enter Full Name" name="fullName" required><br/>
                
                <label for="mobile"><b>Mobile Number</b></label><br/>
                <input type="text" placeholder="Enter Mobile Number" name="mobile" required><br/>
                
                <label for="password"><b>Password</b></label><br/>
                <input type="password" placeholder="Enter Password" name="password" required><br/>
                
                <label for="confirmPassword"><b>Confirm Password</b></label><br/>
                <input type="password" placeholder="Enter Password Again" name="confirmPassword" required><br/>
                
                <button class="loginRegiBttn" type="submit" value="Register">Register</button>
            </div>
            <%--Full Name*: <input type="text" name="fullName" required/><br/>
            Email*: <input type="email" name="email" required/><br/>
            Address line1*: <input type="text" name="address1" required/><br/>
            Address line2 : <input type="text" name="address2" /><br/>
            Postal Code*: <input type="text" name="postalCode" required/><br/>
            Mobile Number*: <input type="text" name="mobile" required/><br/>
            Password*: <input type="password" name="password" required/><br/>
            Confirm Password*: <input type="password" name="confirmPassword" required/><br/>

            <input type="submit" value="Register"/>--%>
        </form>
    </body>
</html>
