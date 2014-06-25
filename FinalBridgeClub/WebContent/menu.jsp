<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page pageEncoding="UTF-8" %>
<link href="menu.css" rel="stylesheet" type="text/css?v=1.1">
<script type="text/javascript">
function formatMenu(){
	$(".menuMain").height(heightScreen * 0.75 * kMenu);
	$(".menuMain").css({"background-color":"#06B", 
						"font-size":"" + heightScreen * 0.020 , 
						"font-weight":"bold"});

	$("ul.menuMain > li > a").css({"padding":"0 " + heightScreen * 0.016 + "px",
								   "color":"#000", 
								   "text-decoration":"none"});
	$("ul.menuSub").css({"background-color":"#06B"}); 
	$("ul.menuSub > li > a").css({"padding":heightScreen * 0.006 + "px " + heightScreen * 0.005 + "px", 
								  "color":"#000"});
	$("a").hover(function(){$(this).css({"color":"#FFF", "font-size":"" + heightScreen * 0.022});}, 
	 function(){$(this).css({"color":"#000", "font-size":"" + heightScreen * 0.020});});
};


function modernMenu(client){
	$(".menuMain").children("#private").remove();
	$(".menuMain").children("#users").remove();
	$(".menuMain").children("#system").remove();
	if(client == "guest"){
		$(".menuMain").append(
		"<li id='system' class='list' style='float:right'><a>სისტემაში შესვლა</a>"
			+ "<ul class='menuSub'>"
				+ "<li><a href='login.jsp'>მომხმარებელი</a></li>"
				+ "<li><a href='register.jsp'>რეგისტრაცია</a></li>"
			+ "</ul>"
		+ "</li>"
		);
	} else if(client == "user" || client == "administrator"){
		$(".menuMain").append(
			"<li id='private' class='list' style='float:left'><a>პირადი</a>"
				+ "<ul class='menuSub'>"
					+ "<li><a href='myInfo.jsp'>კონფიგურაცია</a></li>"
					+ "<li><a href='myScores.jsp'>შედეგები</a></li>"
				+ "</ul>"
			+ "</li>"
			);
		if(client == "administrator")
			$(".menuMain").append(
				"<li id='users' style='float:left'><a href='admin.jsp'>მომხმარებლები</a></li>"
			);
		$(".menuMain").append(
			"<li id='system' class='list' style='float:right'><a href='LogoutManager'>გამოსვლა</a></li>"
			);
	}
};
</script>


<ul class="menuMain">
	<li style="float:left"><a href="index.jsp">მაგიდა</a></li>
    <li class="list" style="float:left"><a>რეიტინგები</a>
        <ul class="menuSub">
            <li><a href="usersByScore.jsp">პირადი</a></li>
            <li><a href="pairsByScore.jsp">წყვილთა</a></li>
        </ul>
	</li>
	<li style="float:left"><a href="http://ka.wikipedia.org/wiki/%E1%83%91%E1%83%A0%E1%83%98%E1%83%AF%E1%83%98">წესები</a></li>
</ul>

