
function setLocationsAndSizes(){
/*
* zone Center: Number of Deal, Sides, Vulnerable, Arrows
*/	
	$("#table").width(w);
	$("#table").height(h);
	$("#table").setRoundDiv(w, h, leftDistance, topDistance,0);
	$("#Ground").setRoundDiv(widthGround, heightGround, widthScreen/2 , heightScreen/2, 0);	$("#Ground").css({"border-color":"#000", 
					  "border-width":"1px", 
					  "border-style":"solid", 
					  "margin":(-heightGround/2) + "px 0 0 " + (-widthGround/2)+ "px", 
					  "font-size" : numDealSide*0.85 + "px", 
					  "font-weight" : "bold"});

	$("#dealMark").setRoundDiv(numDealSide, numDealSide, 0, numDealSide, 0);
	$("#dealMark").css({"border-width":(numDealSide) + "px " + (3*numDealSide) + "px " + (numDealSide) + "px " + (3*numDealSide) + "px"});
	
	$(".tablo").css({"position":"absolute",
					 "background-color":"#EEE"});					
	$(".tablo#top").setRoundDiv(widthGround, numDealSide, 0, 0, 0);
	$(".tablo#bottom").setRoundDiv(widthGround, numDealSide, 0, 4*numDealSide, 0);
	
	$(".side").css("position","absolute");					
	$(".side#top").setRoundDiv(numDealSide, numDealSide, 3*numDealSide , numDealSide, 180);
	$(".side#right").setRoundDiv(numDealSide, numDealSide, 6*numDealSide, 2*numDealSide , 270);
	$(".side#bottom").setRoundDiv(numDealSide, numDealSide, 3*numDealSide , 3*numDealSide, 0);
	$(".side#left").setRoundDiv(numDealSide, numDealSide, 0, 2*numDealSide , 90);

	$(".arrow").css({"position":"absolute", 
					 "color":"#063"});					
	$(".arrow#top").setRoundDiv(numDealSide, numDealSide, 3*numDealSide , -2*numDealSide, 180);
	$(".arrow#right").setRoundDiv(numDealSide, numDealSide, 9*numDealSide, 2*numDealSide , 270);
	$(".arrow#bottom").setRoundDiv(numDealSide, numDealSide, 3*numDealSide , 6*numDealSide, 0);
	$(".arrow#left").setRoundDiv(numDealSide, numDealSide, -3*numDealSide, 2*numDealSide , 90);
	
	$(".arrow#yes").setRoundDiv(5*numDealSide, numDealSide, -2*numDealSide , 5.5*numDealSide, 0);
	$(".arrow#yes").css({"background-color":"#070", 
						 "border-color":"#000", 
						 "border-style":"solid", 
						 "border-width":"1px",
						 "color":"#000"});
	$(".arrow#no").setRoundDiv(5*numDealSide, numDealSide, 4*numDealSide , 5.5*numDealSide, 0);
	$(".arrow#no").css({"background-color":"#B00", 
						 "border-color":"#000", 
						 "border-style":"solid", 
						 "border-width":"1px",
						 "color":"#000"});
	
/*
*  Sizes and Locations of Zones
*/

	$("#table, .just, .middle, .last").css({"position": "absolute"});

//Just
	setFourDiv(".just", justHeight, leftDistance + lastHeight + 1.2*middleHeight, topDistance + lastHeight + 1.2*middleHeight);
	$(".just").css({"font-weight":"bold",
					"border-bottom-color":"#000", 
					"border-bottom-width":"1px",
					"border-bottom-style":"solid"});				 
	$(".timer, .nickname, .timerMerge").css({"display":"inline-block"});
	$(".timer").css({"font-size":0.8*numDealSide + "px"});
	$(".nickname").css({"font-size":0.75*numDealSide + "px"});
	$(".timer").css("background-color","#CCC");
	$(".timerMerge").width(numDealSide);

//Middle
	setFourDiv(".middle", middleHeight, leftDistance + lastHeight + 0.1*middleHeight, topDistance + lastHeight + 0.1*middleHeight);
	$(".bid").width(4*cardWidth/7);
	$(".bid").height(3*cardHeight/4);
	$(".bid").css({"background-color":"#FC9", 
					"border-color":"#000", 
					"font-weight":"bold", 
					"font-size": 4*cardWidth/7 + "px", 
					"display":"inline-block",
					"border-width":"1px 0px 1px 1px",
					"border-style":"solid"});
	$(".bid#last").width(cardWidth);
	$(".bid#last").css({"border-width":"1px"});

//Last
	$(".last").css({"border-top-color":"#000", 
					"border-top-width":"1px",
					"border-top-style":"solid"});
	setFourDiv(".last", lastHeight, leftDistance, topDistance);
	$(".card").width(4*cardWidth/7);
	$(".card").height(cardHeight);
	$(".card").css({"background-color":"#CCC", 
					"border-color":"#000", 
					"font-weight":"bold", 
					"font-size": 4*cardWidth/7 + "px", 
					"display":"inline-block",
					"border-width":"1px 0px 1px 1px",
					"border-style":"solid"});
	$(".card#last").width(cardWidth);
	$(".card#last").css({"border-width":"1px"});
	$(".suit").css("margin-top", cardHeight/3 + "px");
	$("div.diams > div").css("color", "#F00");
	$("div.hearts > div").css("color", "#F00");	
	$(".card[validate='1']").hover(function(){$(this).css("background-color","#EEE");}, function(){$(this).css("background-color","#CCC");});
	$(".suitMerge").width(cardWidth/4);
	$(".suitMerge").css({"display":"inline-block"});
	$(".shirt > .card").css({"background-color":"#999"});

	$(".clubs, .diams, .hearts, .spades, .shirt, .suitMerge").css({"display":"inline-block"});
/*
*  BidsBox visual configuration
*/
	$(".bidPlate").width(0.9 * cardWidth);
	$(".bidPlate").height(0.3 * cardHeight);
	$(".bidPlate#pas").height(0.9*cardHeight);
	$(".bidPlate").css({"background-color":"#FC9", 
					"border-color":"#000", 
					"font-weight":"bold", 
					"font-size": 3*cardWidth/7 + "px", 
					"border-width":"1px",
					"border-style":"solid"});
	$(".bidPlate#pas").css({ 
					"color":"#063", 
					"border-width":"3px 1px 3px 1px"});
	$(".bidPlate[validate='1']").hover(function(){$(this).css("background-color","#FFC");}, 
									   function(){$(this).css("background-color","#FC9");});
	$(".arrow[validate='1']").hover(function(){$(this).css("color","#0F0");}, 
									function(){$(this).css("color","#063");});
	
	$(".arrow#yes[validate='1'], .arrow#no[validate='1']").hover(function(){$(this).css("color","#FFF");}, 
			function(){$(this).css("color","#000");});

	$("div[validate='1']").css( 'cursor', 'pointer' );

	formatMenu();
	
	
}