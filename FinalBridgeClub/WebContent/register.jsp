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

String again = (String)s.getAttribute("exists");
%>
<div id = "loginTable" style="color:#000; position:absolute; background-color:#06B;" align="center">
	<h1 class="title centered">Register at Bridge Club</h1>
<%if(again == null){ %>
	<h1 class="centered colored">Register</h1>
<%}else if(again.equals("yes")){ %>
	<h1 class="centered colored">The Username Already exists</h1>
	<p class="centered colored">Enter another username</p>
<%} %>
	<div class="form">
		<form method="post" action="Manager">
			<input type="text" name="username" placeholder="Username" class="inp" required autofocus><br><br>
			<input type="password" name="password" placeholder="Password" class="inp" required><br><br>
			<input type="text" name="firstname" placeholder="First Name" class="inp" required><br><br>
			<input type="text" name="lastname" placeholder="Last Name" class="inp" required><br><br>
			<input type="email" name="mail" placeholder="E-mail" class="inp" required><br><br>
			<input type="tel" name="number" placeholder="Mobile Number" class="inp"><br><br>
			<input type="date" name="bdate" class="inp"><br><br>
			<input type="radio" name="sex" value="male">Male
			<input type="radio" name="sex" value="female">Female<br><br>
			<input type=submit id="but" value="Register" class="inp">
		</form>
		<h2>Go back to <a href="login.jsp" class="link">Log in</a></h2>
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