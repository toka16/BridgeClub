

/* JavaScrip Auxiliary functions */
/* 
*
*/

function setFourDiv(zone, height, left, top) {
	var widthW = widthScreen - 2 * (left + height);
	var widthH = heightScreen - 2 * (top + height);
		$(zone+"#top").setRoundDiv(widthW, height, left + height, top, 180);
		$(zone+"#left").setRoundDiv(widthH, height, left + height - (widthH + height)*0.5, top + height + (widthH - height)*0.5, 90);
		$(zone+"#bottom").setRoundDiv(widthW, height, left + height, top + widthH + height, 0);
		$(zone+"#right").setRoundDiv(widthH, height, left + 2*height + widthW - (widthH + height)*0.5, top + height + (widthH - height)*0.5, 270);
	};


function getSuitHtml(s){
	switch(s) {
	case "0":
		return "\u2663"; //Hex code of Symbol  \u00A0 &nbsp;
		break;
	case "1":
		return "\u2666";
		break;
	case "2":
		return "\u2665";
		break;
	case "3":
		return "\u2660";
		break;
	case "4":
		return "NT";
		break;
	default:
		return "-";
	}
};
function getDoubleQuotes(s){
	switch(s) {
	case "0":
		return "";
		break;
	case "1":
		return "'";
		break;
	case "2":
		return "''";
		break;
	default:
		return "eRRoR";
	}
};
function secundsToMMSS(s){
	var mm = Math.floor(s/60);
	var ss = s%60;
	ss = ss < 10 ? "0" + ss : "" + ss;
	return mm + ":" + ss;
};


function htmlCard(s, n, isLast){
	n = n == 11 ? "J" :(n == 12 ? "Q" :(n == 13 ? "K" :(n == 14 ? "A" : n)));
	return "<div " + (isLast == 1 ? "id='last'" : "") + "class='card' " + (s==1 || s==2 ? " style='color:#F00;'" : "") + "align='left'>"
		   +	"<div class='nominal'>" + n + "</div>"
		   +    "<div class='suit'>" + getSuitHtml(s) + "</div>"
		   +    "</div>";
};
function htmlBids(bids){
	var rtrn = "";
	var l = bids.length;
	if(l > 0)
		for(var i = 0; i < l; i++){
			var content = bids[i].double == 1 ? "X<br />&nbsp;" : (bids[i].double == 2 ? "X<br />X" : (bids[i].nominal == 0 ? "P<br />&nbsp;" : bids[i].nominal + "<br />" + getSuitHtml(bids[i].suit)));
			var color = bids[i].double > 0 ? "#A00" : (bids[i].nominal == 0 ? "#063" : (bids[i].suit == 1 || bids[i].suit == 2 ? "#F00" : "#000"));
			rtrn = rtrn + "<div " + (i == l-1 ? "id='last'" : "") + "class='bid' style='color:" + color + ";' align = 'left'>"
						+ content
						+ "</div>";
		}
	return rtrn;
};
function htmlBidBox(n, s, d){
	var rtrn = "";
	for(var i = 1; i <=7; i++){
		rtrn += "<div class='bidNominal' style='float:left'>";
		for(var j = 0; j < 5; j++){
			var color = j == 1 || j == 2 ? "#F00" : "#000";
			var isClicked = i < n ? "" : (i == n && j <= s ? "" : " onClick='alertsWebSocket(this);' validate='1'");
			rtrn += "<div" + isClicked +  " class='bidPlate' plateStr = '" + j +":"+ i + ":0' style = 'color:" + color + ";'>" + i + "" + getSuitHtml("" + j) + "</div>";
		}
		rtrn += "</div>";
	}
	rtrn += "<div class='bidNominal' style='float:left'>";
	var isClicked = " onClick='alertsWebSocket(this);' validate='1'";
	rtrn += "<div" + (d == 1 ? isClicked : "") +  " class='bidPlate' plateStr = '0:0:1' style = 'color:#A00;'>X</div>";
	rtrn += "<div" + (d == 2 ? isClicked : "") +  " class='bidPlate' plateStr = '0:0:2' style = 'color:#A00;'>XX</div>";
	var isClicked = 7 < n ? "" : " onClick='alertsWebSocket(this);' validate='1'";
	rtrn += "<div id='pas'" + isClicked +  " class='bidPlate' plateStr = '0:0:0' >P<br />a<br />s</div>";
	rtrn += "</div>";
	return rtrn;
};

function htmlSuitsCards(suit, a){
	var rtrn = "";
	if (suit != "shirt"){
		var l = a.length;
		for(var i = l - 1; i >= 0; i--){
			var isClicked = a[i].valid == 1 ? " onClick='alertsWebSocket(this);' validate='1'" : "";
			var nominal = a[i].nominal;
			nominal = nominal == 11 ? "J" :(nominal == 12 ? "Q" :(nominal == 13 ? "K" :(nominal == 14 ? "A" : nominal)));
			var s = suit == "clubs" ? 0 : (suit == "diams" ? 1 : (suit == "hearts" ? 2 : 3));
			rtrn += "<div " + (i == 0 ? "id='last'" : "") + isClicked + " class='card' align='left'>"
				   +   "<div class='nominal'>" + nominal + "</div>"
				   +   "<div class='suit' suitId = '" + s + "'>&" + suit + ";</div>"
				   +"</div>";
		}
	} else {
		var l = a[0];	
		for(var i = l - 1; i >= 0; i--)
			rtrn += "<div " + (i == 0 ? "id='last'" : "") + "class='card' align='left'>"
				   +   "<div class='nominal'>&nbsp;</div>"
				   +   "<div class='suit'>&nbsp;</div>"
				   +"</div>";;
	}
	return rtrn;
};

function htmlArrow(side, f){
	if (f.empty == 1)
		return "";
	if(  f.confirm === undefined || f.confirm == 0){
		var isClicked = f.valid == 1 ? " onClick='alertsWebSocket(this);' validate='1'" : "";
		return "<div id='"+ side + "'" + isClicked + " class='arrow' align='center'>&dArr;</div>";
	} else {	
		var clicked = " onClick='alertsWebSocket(this);' validate='1'";
		return 	"<div id='no' class='arrow'" + clicked + " align='center'>No, Thanks</div>"+
				"<div id='yes' class='arrow'" + clicked + " align='center'>New Deal</div>";
	}
};

function alertsWebSocket(elem){
	
	//alert($(elem).attr("id") + ":" + $(elem).attr("class"));
	
	
	if($(elem).attr("class") == "arrow"){
		if ($(elem).attr("id") == "yes")
			webSocketa.send("playAgain:1");
		else if ($(elem).attr("id") == "no")
			webSocketa.send("playAgain:0");
		else
			webSocketa.send("arrow:" + $(elem).attr("id"));
	}
	else if($(elem).attr("class") == "card"){
		var nominal = $(elem).children(".nominal").text();
		nominal = nominal == "J" ? 11 :(nominal == "Q" ? 12 :(nominal == "K" ? 13 :(nominal == "A" ? 14 : nominal)));
		webSocketa.send("card:" + $(elem).children(".suit").attr("suitId") + ":" + nominal);
	}
	else if($(elem).attr("class") == "bidPlate")
		webSocketa.send("bid:" + $(elem).attr("plateStr"));	
	
};


/*
* JQuary Auxiliary functions
*/
jQuery.fn.setRoundDiv = function(width, height, left, top, degrees) {
	$(this).width(width);
	$(this).height(height);
	$(this).css({"left" : left + "px", 
				 "top" : top + "px"});
	$(this).rotate(degrees);
};
jQuery.fn.rotate = function(degrees) {
	$(this).css({"-webkit-transform" : "rotate("+ degrees +"deg)",
				 "-ms-transform" : "rotate("+ degrees +"deg)",
				 "-0-transform" : "rotate("+ degrees +"deg)",
				 "-moz-transform" : "rotate("+ degrees +"deg)",
				 "transform" : "rotate("+ degrees +"deg)"});
};
jQuery.fn.locationAlongDiagonal = function() {
	var coefAbscissa = $(this).attr("class") == "middle" ? 16/50 : 8/50;
	var coefOrdinate = $(this).attr("class") == "middle" ? 14/50 : 8/50;
	var left, top, width, height;			
	
	if ($(this).attr("id") == "top" || $(this).attr("id") == "bottom"){

		width = (1-2*coefAbscissa)*w;
		height = h / 6;
		left = coefAbscissa * w;
		top = $(this).attr("id") == "top" ? coefAbscissa * h - height : (1-coefAbscissa) * h;
	} else {
		width = (1-2*coefOrdinate)*h;
		height = h / 8;
		top = coefOrdinate * h + (width - height) / 2;
		left = $(this).attr("id") == "left" ? coefOrdinate * w - (width + height) / 2 : (1-coefOrdinate) * w + (height - width) / 2;
		$(this).rotate(90);	
	} 
	$(this).css({"left" : left + "px",
				 "top" : top + "px"});						   
	$(this).width(width);				 
	$(this).height(height);				
					   
}; 