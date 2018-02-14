<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:url var="createGameHref" value="/createGame">
</c:url>
<a href="${createGameHref}"><button type="submit">Create
		Game</button></a>
<div class="row padding-row"></div>
<div class="row centered-text">
	<h3>Current</h3>
</div>
<div class="row">

	<table class="table table-striped table-bordered">
		<tr>
			<th>Game Type</th>
			<th>Name</th>
			<th>Days Remaining</th>
			<th>Play</th>
		</tr>
		<c:forEach items="${userGames}" var="game">
			<c:if test="${game.currentGame}">
				<c:url var="gameLeaderboard" value="/gameLeaderboard">
					<c:param name="gameId" value="${game.gameId}" />
				</c:url>
				<tr>
					<c:choose>
						<c:when test="${game.realGame}">
							<td>Real</td>
						</c:when>
						<c:otherwise>
							<td>Mock</td>
						</c:otherwise>
					</c:choose>
					<td><a href="${gameLeaderboard}">${game.nameOfGame}</a></td>
					<td>${ game.getDaysRemaining()  }</td>
					<td><c:url var="userGameDashboard" value="/userGameDashboard">
							<c:param name="gameId" value="${game.gameId}"></c:param>
						</c:url> <a href="${userGameDashboard}">Play</a></td>

				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>
<br>
<br>
<div class="row padding-row"></div>
<div class="row centered-text">
	<h3>History</h3>
</div>
<div class="row">
	<table class="table table-striped table-bordered">
		<tr>
			<th>Name</th>

			<th>End Date</th>
		</tr>

		<c:forEach items="${userGames}" var="game">
			<c:if test="${!game.currentGame}">
				<c:url var="gameLeaderboard" value="/completedGameLeaderboard">
					<c:param name="gameId" value="${game.gameId}" />
				</c:url>
				<tr>
					<td><a href="${gameLeaderboard}">${game.nameOfGame}</a></td>
					<td>${game.endDate}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />