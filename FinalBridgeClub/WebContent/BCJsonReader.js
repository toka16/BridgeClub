function readJson(json){

	//alert(JSON.stringify(json));
	
	$.each(json, function(zone, sides){
		if(zone=="message"){ 
			$.each(sides, function(side, value){
				if (side == "playAgain"){
					var tm = confirm(value) ? "1" : "0";
					webSocketa.send("playAgain:" + tm);	
				} else if(sides = "chat"){
					;
				} else if(sides = "client"){
					modernMenu(value);
				}
			});
		}
		if(zone=="center"){ 
			$.each(sides, function(side, fields){
				var text = fields.empty == "1" ? "" : fields.numDeal;
				$("."+zone+"#"+side).text(text);
			});
		}
		else if(zone=="tablo"){
			$.each(sides, function(side, fields){
				if(side=="top"){
					var text = fields.empty == 1 ? "" : fields.nominal + getSuitHtml(fields.suit) + getDoubleQuotes(fields.double) + 
														"(" + fields.declarer + ")" + " +" + fields.plus + "-" + fields.minus;
					$("."+zone+"#"+side).text(text);
				}
				else if(side=="bottom"){
					var text = fields.empty == 1 ? "" : fields.pointPlus + " " + fields.compensation + ", imp " + fields.imp; 
					$("."+zone+"#"+side).text(text);	
				}
			});
		}
		else if(zone=="side"){
			$.each(sides, function(side, fields){
				$("."+zone+"#"+side).text(fields.value);
				$("#dealMark").css("border-"+side+"-color", fields.vulnerable == "1" ? "red" : "white"); 
			});
		}
		else if(zone=="arrow"){
			$("." + zone).remove();
			$.each(sides, function(side, fields){
				$("#Ground").append(htmlArrow(side, fields));
			});
		}
		else if(zone=="just"){
			$.each(sides, function(side, fields){
				if(fields.empty == 1){
					$(".timer#"+side).text("");
					$(".nickname#"+side).text("");
				}else{
				
				
					$.each(fields, function(field, values){
						if(field == "nickname"){
							$("."+ field +"#"+side).text(values);
						}
						else if(field == "timer"){
							var seconds = values.time;		
							$("."+ field +"#"+side).text(secundsToMMSS(seconds));
							if(values.active == "1"){
								clearInterval(tiker);
								tiker = setInterval(function(){
													seconds--;
													$("."+ field +"#"+side).text(secundsToMMSS(seconds));
													if(seconds <= 0)
													clearInterval(tiker);
												}, 1000);
							} 
						}
					});
				}
			});
		}
		else if(zone=="middle"){
			$.each(sides, function(side, fields){
				if(fields.empty == 1){
					$("." + zone + "#"+side).html("");
				} else {
					$.each(fields, function(field, values){
						if(field == "playedCard"){
							$("."+ zone +"#"+side).html(htmlCard(values.suit, values.nominal, 1));
						}
						else if(field == "dealedBids"){
							$("."+ zone +"#"+side).html(htmlBids(values));
						}
					});
				}
			});
		}
		else if(zone=="last"){
			$.each(sides, function(side, fields){
				$.each(fields, function(field, values){
					if(field == "cardsSection"){
						var empty = values.empty;
						$.each(values, function(suit, array){
							if(suit != "empty"){
								var html = empty == 1 ? "" : htmlSuitsCards(suit, array);
								$("."+ suit + "#" + side).html(html);
							}
						});
					}
					else if(field == "bidsBoxSection"){
						var html = values.empty == 1 ? "" : htmlBidBox(values.nominal, values.suit, values.double);
						$(".bidsBox#" + side).html(html);
					}
				});
			});
		}
		
	});
setLocationsAndSizes();
};
