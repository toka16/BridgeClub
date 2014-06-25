<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page import="bridge.User" %>
<%@ page import="bridge.Table" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BridgeClub</title>
        <link href="syle.css" rel="stylesheet" type="text/css">
        <script src="jquery-2.1.1.js"></script>
        <script src="variables.js"></script>
    </head>
<body>
<%@ include file="menu.jsp" %>
<%
HttpSession s = request.getSession();
User user = (User)s.getAttribute("user");
String again = (String)s.getAttribute("incorrect");

// for moderMenu()
String status;
if(user==null){
	status = "guest";
	user = new User("guest", User.Status.GUEST);
	Table curTable = (Table)request.getServletContext().getAttribute("table");
	user.setTable(curTable);
	s.setAttribute("user", user);
}else{
	status = user.getStatus().name().toLowerCase();
	if(!status.equals("guest"))
		response.sendRedirect("index.jsp");
}

//if(uname!=null){
//	response.sendRedirect("table.jsp");
//}
%>
<div id = "loginTable" style="color:#000; position:absolute; background-color:#06B;" align="center">
<br><br><br><br>
	<h1 class="title centered">Welcome to Bridge Club</h1>
<%if(again == null){ %>
<br>
	<h1 class="centered colored">Login</h1>
<%}else if(again.equals("yes")){ %>
<br><br><br>
	<h1 class="centered colored">Username or Password is incorrect</h1>
<br><br><br>
	<p class="centered colored">Try Again</p>
<%} %>
	<div class="form">
		<form method="get" action="Manager">
    		<input type="text" name="username" placeholder="Username" class="inp" required autofocus/><br><br>
        	<input type="password" name="password" placeholder="Password" class="inp" required/><br><br>
        	<input type=submit id="but" value="Login" class="inp"/>
    	</form>
    	<br><br>
	    <h2><a class="link centered" href="register.jsp"  style="color: #000">Create Account</a></h2>
	</div>
</div>
<script type="text/javascript">
$("#loginTable").width(w);
$("#loginTable").height(h);
$("#loginTable").css({"left" : leftDistance + "px", 
	 				 "top" : topDistance + "px"});
	 
var client = <%="'" + status + "'"%>;
//alert(client);
modernMenu(client);
formatMenu();

</script>	
</body>
</html>