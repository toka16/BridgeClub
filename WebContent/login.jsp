<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BridgeClub</title>
        <link href="style.css" rel="stylesheet" type="text/css">
    </head>
<body>
<%
HttpSession s=request.getSession();
String uname = (String)s.getAttribute("username");
String again = (String)s.getAttribute("incorrect");
if(uname!=null){
	response.sendRedirect("table.jsp");
}
%>
	<h1 class="title centered">Welcome to Bridge Club</h1>
<%if(again == null){ %>
	<h1 class="centered colored">Login</h1>
<%}else if(again.equals("yes")){ %>
	<h1 class="centered colored">Username or Password is incorrect</h1>
	<p class="centered colored">Try Again</p>
<%} %>
	<div class="form">
		<form method="get" action="Manager">
    		<input type="text" name="username" placeholder="Username" class="inp" required autofocus/>
        	<input type="password" name="password" placeholder="Password" class="inp" required/>
        	<input type=submit id="but" value="Login" class="inp"/>
    	</form>
	    <p class="centered colored"><a class="link centered" href="register.jsp">Create Account</a></p>
	</div>
</body>
</html>