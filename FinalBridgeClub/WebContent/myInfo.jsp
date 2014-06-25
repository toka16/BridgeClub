<%@page import="bridge.GetterFromDB"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page import="bridge.User" %>
<%@ page import="bridge.Table" %>
<%@ page import="bridge.Table.Side" %>
<%@ page import="bridge.User.Status" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BridgeClub</title>
        <script src="jquery-2.1.1.js"></script>
        <script src="variables.js"></script>
    </head>
<body>
<%@ include file="menu.jsp" %>
<%
HttpSession s = request.getSession();
User user = (User)s.getAttribute("user");
String status;
if(user==null || user.getStatus().equals(Status.GUEST)){
	status = "guest";
	user = new User("guest", User.Status.GUEST);
	Table curTable = (Table)request.getServletContext().getAttribute("table");
	user.setTable(curTable);
	s.setAttribute("user", user);
	response.sendRedirect("index.jsp");
}else{
	status = user.getStatus().name().toLowerCase();
}
%>

<div id = "loginTable" style="color:#000; position:absolute; background-color:#06B;" align="center">
	<h1 class="title centered">პირადი გვერდი</h1>
	<div>
	<table style="overflow:scroll;">
		<tr>
			<td style="background-color:white">მომხმარებელი</td>
			<td style="background-color:white"><%=user.getUsername() %></td>
		</tr>
		<tr>
			<td style="background-color:white">სტატუსი</td>
			<td style="background-color:white"><%=user.getStatus() %></td>
		</tr>
		<tr>
			<td style="background-color:white">სახელი</td>
			<td style="background-color:white"><%=user.getFirstName() %></td>
		</tr>
		<tr>
			<td style="background-color:white">გვარი</td>
			<td style="background-color:white"><%=user.getLastName() %></td>
		</tr>
		<tr>
			<td style="background-color:white">ფოსტა</td>
			<td style="background-color:white"><%=user.getMail() %></td>
		</tr>
		<tr>
			<td style="background-color:white">დაბადების თარიღი</td>
			<td style="background-color:white"><%=user.getBirthDate() %></td>
		</tr>
		<tr>
			<td style="background-color:white">სქესი</td>
			<td style="background-color:white"><%=user.getSex() %></td>
		</tr>
		<tr>
			<td style="background-color:blue">ჯამური იმპი</td>
			<%
			List<Object[]> list = GetterFromDB.getUsersTotalImp(user);
			int score = 0;
			if(list.size()>0)
				score = (Integer)list.get(0)[3];
			%>
			<td style="background-color:blue"><%=score %></td>
		</tr>
	</table>
	<form action="PasswordManager" method="GET">
		<input type="password" name="newPass" placeholder="Password" required/><br>
       	<input type=submit value="პაროლის შეცვლა"/>
	</form>
	<p id="response"></p>
	</div>
</div>
<script type="text/javascript">
$("#loginTable").width(w);
$("#loginTable").height(h);
$("#loginTable").css({"left" : leftDistance + "px", 
	 				 "top" : topDistance + "px"});
	 
var client = <%="'" + status + "'"%>;
modernMenu(client);
formatMenu();	 
</script>	
</body>
</html>