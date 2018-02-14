<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:url var="formAction" value="/addPlayer" />
<form method="GET" action="${formAction}">

<div class="row centered-text">
	<h2 style="color:blue;" id="gameboard-header">Current Game: ${selectedGame.nameOfGame}</h2>
	<input type="hidden" name="gameId" value="${selectedGame.gameId}" />
	</div>
</form>



<div class="row">
		<table class="table table-striped table-bordered">
	
		<tr>
			<th>Rank</th>
			<th>Players</th>
			<th>Balance</th>
		</tr>
		<c:set var="rank" value="1" />
		<c:forEach items="${totalBalances}" var="totalBalance">
			<tr>
				<td>${rank}</td>
				<td>${totalBalance.userName}</td>
				<td>$<fmt:formatNumber value = "${totalBalance.totalBalance}" maxFractionDigits = "2"/></td>
			</tr>
			<c:set var="rank" value="${rank + 1}" />
		</c:forEach>
	</table>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />