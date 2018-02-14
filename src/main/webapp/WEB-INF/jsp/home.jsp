
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<div class="intro homepage_image">
	<c:choose>
		<c:when test="${not empty currentUser}">
			<c:url var="newUser" value="/users/dashboard" />
		</c:when>
		<c:otherwise>
			<c:url var="newUser" value="/login" />
		</c:otherwise>
	</c:choose>
	<div class="border">
		<h2 class="welcome" style="color:blue;">Welcome to Stock Market Bravo, The Stock
			Market Game!!</h2>
		<p class="info">&nbsp;&nbsp;&nbsp;The goal of this web application
			is to simulate a real stock exchange using fake money but real
			prices. As a user you have the choice of either using real data with
			real stock market time frames or a mock simulated data source that
			can be played around the clock. Using our web application, you can
			sharpen your stock market skills along side your friends in simulated
			instances that you create. Our hope for each user of this application
			is that you sharpen your skills as a stock trader.</p>
		<h2 style="color:blue;">Rules:</h2>
		<ul>
			<li>When a user creates a game they determine the number of days
				that the match will last for.</li>
			<li>Each player in each game starts with $100,000 at the
				beginning of the match. Players use this money to purchase shares in
				various stocks from the provided list of stocks.</li>
			<li>The winner of the match is determined by which player holds
				the most money when the number of days left turns to zero.</li>
			<li>Come the end of the match, all shares currently in the
				user's possession are automatically sold and the profits are added
				to the users balance.</li>
		</ul>
	</div>
</div>
<br>
<a href="${newUser}"><button type="button" class="btn btn-success"
		id="homeSubmit">Play!</button></a>





<c:import url="/WEB-INF/jsp/common/footer.jsp" />
