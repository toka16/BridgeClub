<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>BridgeClub</title>
	<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
HttpSession s = request.getSession();
String username = (String)s.getAttribute("username");
if(username!=null){
	response.sendRedirect("table.jsp");
}
String again = (String)s.getAttribute("exists");
%>
	<h1 class="title centered">Register at Bridge Club</h1>
<%if(again == null){ %>
	<h1 class="centered colored">Register</h1>
<%}else if(again.equals("yes")){ %>
	<h1 class="centered colored">The Username Already exists</h1>
	<p class="centered colored">Enter another username</p>
<%} %>
	<div class="form">
		<form method="post" action="Manager">
			<input type="text" name="username" placeholder="Username" class="inp" required autofocus>
			<input type="password" name="password" placeholder="Password" class="inp" required>
			<input type="text" name="firstname" placeholder="First Name" class="inp" required>
			<input type="text" name="lastname" placeholder="Last Name" class="inp" required>
			<input type="email" name="mail" placeholder="E-mail" class="inp" required>
			<input type="tel" name="number" placeholder="Mobile Number" class="inp">
			<input type="date" name="bdate" class="inp">
			<input type="radio" name="sex" value="male">Male
			<input type="radio" name="sex" value="female">Female
			<input type=submit id="but" value="Register" class="inp">
		</form>
		<p class="centered colored">Go back to <a href="login.jsp" class="link">Log in</a></p>
	</div>

</body>
</html>