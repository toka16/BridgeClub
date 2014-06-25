/*
* Auxiliary Constants:
*/
//var socketURL = "ws://46.49.92.173:8080/FinalBridgeClub/OurSocket";
var socketURL = "ws://192.168.77.140:8080/FinalBridgeClub/OurSocket";
//var socketURL = "ws://localhost:8080/FinalBridgeClub/OurSocket";
var widthScreen = window.innerWidth;
var heightScreen = window.innerHeight;
var w,h;
var kMenu = 0.04;
if (widthScreen > 4*(heightScreen*(1-kMenu))/3){
	w = 4*(heightScreen*(1-kMenu))/3;
	h = (heightScreen*(1-kMenu));
} else {
	w = widthScreen;
	h = 3*widthScreen/4;
}
var leftDistance = (widthScreen - w)/2;
var topDistance =heightScreen*kMenu + (heightScreen*(1-kMenu) - h)/2;


var numDealHalfSide = Math.floor(w/80);
var widthGround = 14*numDealHalfSide;
var heightGround = 10*numDealHalfSide;
var numDealSide = 2*numDealHalfSide;
var lastHeight = h / 6;	
var middleHeight = h / 9;	
var justHeight = h / 30;


var cardHeight = middleHeight*0.8;
var cardWidth = cardHeight/2;

var tiker;