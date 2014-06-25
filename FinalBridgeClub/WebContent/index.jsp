<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BridgeClub</title>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="bridge.User" %>
<%@ page import="bridge.Table" %>

<script src="jquery-2.1.1.js"></script>
<script src="formater.js"></script>
<script src="variables.js"></script>
<script src="functions.js"></script>
<script src="BCJson.js"></script>
<script src="BCJsonReader.js"></script>

</head>

<body>
<%@ include file="menu.jsp" %>
<div id="table" style="background-color:#690"></div>
<!-- zone Center: Number of Deal, Sides, Vulnerable -->	
	<div id="Ground" style="background-color:#000; position:absolute;">
		<div id="top" class="tablo" align="center">&nbsp;</div>
        <div id="bottom" class="tablo" align="center">&nbsp;</div>
        <div id="dealMark" class="center" align="center" style="vertical-align:center; position:absolute; background:#EEE; border-style: solid;"></div>

        <div id="top" class="side" align="center">N</div>
        <div id="right" class="side" align="center">E</div>
        <div id="bottom" class="side" align="center">S</div>
        <div id="left" class="side" align="center">W</div>
    
        <!--Here iJQuary drows  arrow like this: div id="top,right,bottom,left" class="arrow" align="center"></div-->
    </div>
    
<!-- zone Just: timer, nickname.  Maybe it is possible write this by JQuary or JavaScript, i think "yes".  -->    
    <div id="top" class="just" align="center">
        <div id="top" class="timer"></div>
        <div id="top" class="timerMerge">&nbsp;</div>            
        <div id="top" class="nickname"></div>
    </div>
    <div id="right" class="just" align="center">
        <div id="right" class="timer"></div>
        <div id="right" class="timerMerge">&nbsp;</div>
        <div id="right" class="nickname"></div>
	</div>
	<div id="bottom" class="just" align="center">
        <div id="bottom" class="timer"></div>
        <div id="bottom" class="timerMerge">&nbsp;</div>            
        <div id="bottom" class="nickname"></div>
	</div>
	<div id="left" class="just" align="center">
        <div id="left" class="timer"></div>
        <div id="left" class="timerMerge">&nbsp;</div>            
        <div id="left" class="nickname"></div>
	</div>
    

<!-- zone Middle: Array of Bids, Played Card -->    
    <div id="top" class="middle" align="center"></div>
    <div id="right" class="middle" align="center"></div>
	<div id="bottom" class="middle" align="center"></div>
	<div id="left" class="middle" align="center"></div>

<!-- zone Last: Array of Bids, Played Card -->    
    <div id="top" class="last" align="center">
        <div id="top" class="clubs"></div>
        <div id="top" class="suitMerge">&nbsp;</div>
        <div id="top" class="diams"></div>
        <div id="top" class="suitMerge">&nbsp;</div>
        <div id="top" class="hearts"></div>
        <div id="top" class="suitMerge">&nbsp;</div>
        <div id="top" class="spades"></div>
        <div id="top" class="shirt"></div>
    </div>
	<div id="right" class="last" align="center">
        <div id="right" class="clubs"></div>
        <div id="right" class="suitMerge">&nbsp;</div>
        <div id="right" class="diams"></div>
        <div id="right" class="suitMerge">&nbsp;</div>
        <div id="right" class="hearts"></div>
        <div id="right" class="suitMerge">&nbsp;</div>
        <div id="right" class="spades"></div>
        <div id="right" class="shirt"></div>
	</div>       
	<div id="bottom" class="last" align="center">
        <div id="bottom" class="clubs"></div>
        <div id="bottom" class="suitMerge">&nbsp;</div>
        <div id="bottom" class="diams"></div>
        <div id="bottom" class="suitMerge">&nbsp;</div>
        <div id="bottom" class="hearts"></div>
        <div id="bottom" class="suitMerge">&nbsp;</div>
        <div id="bottom" class="spades"></div>
        <div id="bottom" class="suitMerge">&nbsp;</div>
        <div id="bottom" class="suitMerge">&nbsp;</div>
        <div id="bottom" class="bidsBox" style="display:inline-block" align="center"></div>
    </div>       
	<div id="left" class="last" align="center">
        	<div id="left" class="clubs"></div>
        	<div id="left" class="suitMerge">&nbsp;</div>
        	<div id="left" class="diams"></div>
        	<div id="left" class="suitMerge">&nbsp;</div>
        	<div id="left" class="hearts"></div>
        	<div id="left" class="suitMerge">&nbsp;</div>
        	<div id="left" class="spades"></div>
        	<div id="left" class="shirt"></div>
    </div> 
    
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
}
%>

<script>
var webSocketa = new WebSocket(socketURL);

/*
* WebSocket functions
*/
webSocketa.onopen = function(e){
//	alert("onOpen");
//	webSocketa.send(JSON.stringify(jsonTest));
};

webSocketa.onmessage = function(e){
//	alert(e.data);
	json = JSON.parse(e.data);
	readJson(json);
};

webSocketa.onclose = function(e){
	alert("onClose");
	webSocketa = new WebSocket(socketURL);
};

webSocketa.onerror = function(e){
//	alert("onError");
};
var client = <%="'" + status + "'"%>;
//alert(client);
modernMenu(client);
//modernMenu("user");
setLocationsAndSizes();
//readJson(jsonTest);
</script>



</body>
</html>




