<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
var url="ws://localhost:8080/BridgeClube/tableSocket";
var ws = new WebSocket(url);
ws.onopen = function(e){
	document.getElementById("area").textContent = "opened";
	ws.send("init connection");
};

ws.onmessage = function(e){
	document.getElementById("area").textContent += "\n"+e.data;
};


ws.onerror = function(e){
	document.getElementById("area").textContent+="error";
}

function sendmsg(side){
	ws.send("new message");
}

function takePlaceEast(){
	ws.send("place:east");	
}
function takePlaceWest(){
	ws.send("place:west");
}
function takePlaceNorth(){
	ws.send("place:north");
}
function takePlaceSouth(){
	ws.send("place:south");
}

function send(){
	var msg = document.getElementById("input").value;
	ws.send("move:"+msg);
}

</script>

<body>
	<textarea id="area" readonly></textarea><br>
	<button type="submit" id="east" onclick="takePlaceEast()">east</button>
	<button type="submit" id="west" onclick="takePlaceWest()">west</button>
	<button type="submit" id="north" onclick="takePlaceNorth()">north</button>
	<button type="submit" id="south" onclick="takePlaceSouth()">south</button>
	<input type="text" id="input">
	<button type=submit onclick="send()">send</button>
</body>
</html>