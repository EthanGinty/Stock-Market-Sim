<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<title>Stock Market Bravo</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script
	src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.js "></script>
<script
	src="https://cdn.jsdelivr.net/jquery.timeago/1.4.1/jquery.timeago.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<c:url var="cssHref" value="/css/site.css" />
<link rel="stylesheet" type="text/css" href="${cssHref}">

<script type="text/javascript">
	$(document).ready(function() {
		$("time.timeago").timeago();

		$("#logoutLink").click(function(event) {
			$("#logoutForm").submit();
		});

		var pathname = window.location.pathname;
		$("nav a[href='" + pathname + "']").parent().addClass("active");

	});
</script>

</head>

<body>
	<header class="header">
		<img class="floating-img" src="<%=request.getContextPath()%>/img/gameLogoCropped.png"/>
		<h1 class="h1">Stock Market</h1>
	</header>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<ul class="nav navbar-nav">
				<c:url var="homePageHref" value="/" />
				<li><a href="${homePageHref}">Home</a></li>
				<c:if test="${not empty currentUser.userName}">
					<c:url var="userDashboardHref" value="/users/dashboard" />
					<li><a href="${userDashboardHref}">Dashboard</a></li>
					<c:url var="changePasswordHref" value="/users/changePassword" />
					<li><a href="${changePasswordHref}">Change Password</a></li>
				</c:if>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${empty currentUser}">
						<c:url var="newUserHref" value="/register" />
						<li><a href="${newUserHref}">Sign Up</a></li>
						<c:url var="loginHref" value="/login" />
						<li><a href="${loginHref}">Log In</a></li>
					</c:when>
					<c:otherwise>
						<c:url var="logoutAction" value="/logout" />
						<form id="logoutForm" action="${logoutAction}" method="POST">
							<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
						</form>
						<li><a id="logoutLink" href="#">Log Out</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</nav>
	<c:if test="${not empty currentUser}">
		<p id="currentUser">Current User: ${currentUser.userName}</p>
	</c:if>
	<div class="container">