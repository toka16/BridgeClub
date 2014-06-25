var jsonTest = 
	{"message"	:   {"playAgain" : "questionText", 
					 "chat":"chatText", 	//სამომავლოდ
					 "client":"user"},   	//გაუქმებულია
	"center" 	:   {"dealMark" : {"empty":"0", "numDeal":"15"}},
	"tablo"		:   {"top":{"empty":"0", "nominal":"4", "suit":"3", "double":"1", "declarer" : "W", "plus":"6", "minus":"2"}, 
					 "bottom":{"empty":"0", "pointPlus":"120", "compensation":"-210", "imp":"-6"}
					},
	"side"     	: 	{"top":{"value":"E","vulnerable":"1"}, 
					 "right":{"value":"S","vulnerable":"0"},
					 "bottom":{"value":"W","vulnerable":"1"},
					 "left":{"value":"N","vulnerable":"0"}
					},
	"arrow"		: 	{"top":{"empty":"1", "valid":"1"}, 
					 "right":{"empty":"0", "valid":"1"},
					 "bottom":{"empty":"0", "confirm":"1", "valid":"0"},
					 "left":{"empty":"1", "valid":"0"}
					},
	"just" 		: 	{"top"   	: 	{"empty"	:	"1", 
									 "timer"  	: 	{"time" : "5", "active" :"1"},
									 "nickname" : 	"brigeMan"
									},
					 "right"	: 	{"empty"	:	"1", 
									 "timer"  	: 	{"time" : "350", "active" :"0"},
									 "nickname" : 	"brijist"
									},
					 "bottom" 	: 	{"empty"	:	"1", 
									 "timer"  	: 	{"time" : "215", "active" :"0"},
									 "nickname" : 	"brigel"
					  				},
					 "left"		: 	{"empty"	:	"1", 
									 "timer"  	: 	{"time" : "10", "active" :"1"},
									 "nickname" : 	"woman"
									}
					},

	"middle" 	: 	{"top" 		: 	{"empty"		:	"1", 
									 "playedCard"	: 	{"suit":"0", "nominal":"K"},
						  		 	 "dealedBids"  	: 	[{"suit":"3", "nominal":"1", "double":"0"},{"suit":"0", "nominal":"3", "double":"0"},{"suit":"2", "nominal":"3", "double":"2"}]
						  			},
					 "right"	: 	{"empty"		:	"1", 
									 "playedCard"  	: 	{"suit":"0", "nominal":"2"}
						  			},
					"bottom" 	: 	{"empty"		:	"1", 
									 "playedCard"  	: 	{"suit":"2", "nominal":"V"},
						  			 "dealedBids"  	: 	[{"suit":"0", "nominal":"2", "double":"0"}]
						  			},
				 	"left"		: 	{"empty"		:	"1", 
									 "playedCard"  	: 	{"suit":"3", "nominal":"10"},
						  			 "dealedBids"  	: 	[{"suit":"3", "nominal":"5", "double":"1"}]
						  			}
					},

	"last" 		: 	{
					 "top" 		: 	{"cardsSection" :  	{"empty"	:	"1",
						 								 "clubs"	: 	[{"nominal":"5", "valid":"0"}, {"nominal":"9", "valid":"0"}],
														 "diams"  	: 	[{"nominal":"9", "valid":"0"}, {"nominal":"Q", "valid":"0"}],
														 "hearts" 	:	[{"nominal":"9", "valid":"0"}, {"nominal":"V", "valid":"0"}],
														 "spades" 	: 	[{"nominal":"7", "valid":"0"}, {"nominal":"V", "valid":"0"}],
														 "shirt" 	: 	["2"]
														}
						 			},
					 "right"	: 	{"cardsSection" :   {"clubs"  	: 	[{"nominal":"2", "valid":"0"}, {"nominal":"A", "valid":"0"}],
											 			 "diams"  	: 	[{"nominal":"5", "valid":"0"}, {"nominal":"A", "valid":"0"}],
											 			 "hearts" 	: 	[{"nominal":"3", "valid":"0"}, {"nominal":"Q", "valid":"0"}],
											 			 "spades" 	: 	[{"nominal":"3", "valid":"0"}, {"nominal":"A", "valid":"0"}],
											 			 "shirt" 	: 	["5"]
														}
						 			},
					 "bottom" 	: 	{"cardsSection" :  	{"clubs"  : [{"nominal":"8", "valid":"0"}, {"nominal":"V", "valid":"0"}],
											 			 "diams"  : [{"nominal":"4", "valid":"1"}, {"nominal":"V", "valid":"1"}],
											 			 "hearts" : [{"nominal":"K", "valid":"0"}, {"nominal":"A", "valid":"0"}],
											 			 "spades" : [{"nominal":"2", "valid":"0"}, {"nominal":"5", "valid":"0"}, {"nominal":"Q", "valid":"0"}]
														},
						  			"bidsBoxSection":	{"empty"  : "0",
						  								 "nominal"  : "4",
											 			 "suit"  : "1",
											 			 "double" : "1"
											 			}					 
						 			},
					"left": 		{"cardsSection" :  {"clubs"  : [{"nominal":"3", "valid":"1"}, {"nominal":"10", "valid":"1"}],
											 			"diams"  : [{"nominal":"2", "valid":"0"}, {"nominal":"6", "valid":"0"}, {"nominal":"8", "valid":"0"}, {"nominal":"K", "valid":"0"}],
											 			"hearts" : [{"nominal":"8", "valid":"1"}, {"nominal":"10", "valid":"1"}],
											 			"spades" : [{"nominal":"4", "valid":"0"}, {"nominal":"6", "valid":"0"}, {"nominal":"9", "valid":"0"}, {"nominal":"10", "valid":"0"}, {"nominal":"K", "valid":"0"}],
					 									"shirt" : ["3"]
														}
						  			}
					}
	};