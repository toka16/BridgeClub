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
	<h1>ჩემი  ქულები</h1>
	<%List<Object[]> scores = GetterFromDB.getScoresHistory(user); %>
	<div>
	<table>
		<tr>
			<th style="background-color:red">თამაშის ნომერი</th>
			<th style="background-color:red">მხარე</th>
			<th style="background-color:red">აღებული ქულა</th>
			<th style="background-color:red">კომპენსაციის ქულა</th>
			<th style="background-color:red">იმპი</th>
		</tr>
		<%for(int i=0; i<scores.size(); i++){ %>
			<tr>
				<%for(int j=0; j<scores.get(i).length; j++){ %>
				<td style="background-color:white"><%=scores.get(i)[j] %></td>
				<%} %>
			</tr>
		<%}%>
	</table>
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