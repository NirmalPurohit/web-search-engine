<!--
--  @author Nirmal Purohit
--  Help for GUI was taken form Yash Mistry email: yashmistry1995@gmail.com
-->


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Engine</title>

<link rel="stylesheet" type="text/css"
	href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">

<!--===============================================================================================-->
</head>
<body>
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<div class="hero_home version_1">
		<div class="content">
                    <img src="logo4.png" width="700" height="250" style="padding-top: 100px">
		<h3 style="padding: 2%;color: #17101C">Search</h3>
			<form method="post" >
				<div id="custom-search-input">
					<div class="input-group">
						<input type="text" class=" search-query" id="sname" name="sname"
							onkeypress="onClick();" placeholder="Enter keyword here"> 
                                                <input type="button" id="submit_search" class="btn_search" style="background: #17101C" value="Go" onclick="onSearch()"/>
					</div>
				</div>
			</form>

			<div id="ajaxGetUserServletResponse" ></div>

		</div>
	</div>

	<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script>
		$('.js-pscroll').each(function() {
			var ps = new PerfectScrollbar(this);

			$(window).on('resize', function() {
				ps.update();
			})
		});
		function onClick() {
			var sname = $('#sname').val();
			$.ajax({
				async : true,
				url : 'homepageactivity',
				data : {
					buttonType : "editdistance",
					sname : sname
				},
				timeout : 100000,
				success : function(responseJson) {
					var availableTags = "";
					$.each(responseJson, function(index, item) {
						availableTags += item + ",";
					});
					console.log(availableTags);
					$("#sname").autocomplete({
						source : availableTags.split(",")
					});
				}
			});
		}
                function onSearch() {
			var sname = $('#sname').val();
			$.ajax({
				async : true,
				url : 'homepageactivity',
				data : {
					buttonType : "frequency",
					sname : sname
				},
				timeout : 100000,
				success : function(responseJson) {
					var availableTags = "";
					$.each(responseJson, function(index, item) {
						availableTags += item + ",";
					});
					console.log(availableTags);
					$("#sname").autocomplete({
						source : availableTags.split(",")
					});
				}
			});
		}
	</script>
</body>
</html>