<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<script>
	
</script>

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<h2 class="centered-text">${selectedGame.nameOfGame}&nbsp;Dashboard</h2>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-6">Player: ${currentUser.userName}</div>
		<div class="col-xs-6">
			<div class="row">
				<div class="col-xs-12 right-aligned">
					Total Balance: $<fmt:formatNumber value = "${totalBalance}" maxFractionDigits = "2"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 right-aligned">
					Available Balance: $<fmt:formatNumber value = "${availableBalance}" maxFractionDigits = "2"/>
				</div>
			</div>
		</div>
	</div>

	<div class="row padding-row"></div>
	<div class="row centered-text">
		<h2>Player Stock</h2>
	</div>
	<div class="row">
		<table class="table table-striped table-bordered">
			<tr>
				<th>Action</th>
				<th>Symbol</th>
				<th>Number of Shares</th>
				<th>Current Price</th>
			</tr>


			<tr>
				<c:forEach items="${allTransactions}" var="transaction">
					<tr>
						<c:url var="doTransaction" value="/transaction">
							<c:param name="transaction" value="sell"></c:param>
							<c:param name="symbol" value="${transaction.stock.symbol}"></c:param>
						</c:url>
						<td><a href="${doTransaction}">Sell</a></td>
						<td>${transaction.stock.symbol}</td>
						<td>${transaction.numberOfShares}</td>
						<td>$<fmt:formatNumber value="${transaction.stock.bidPrice}"
								maxFractionDigits="2" /></td>
						
					</tr>
				</c:forEach>
			</tr>
		</table>
		<span>*refer to table below for most current price*</span>
	</div>

	<div class="row padding-row"></div>
	<div class="row centered-text">
		<h2>Market</h2>
	</div>
	<div class="row">
		<table class="table table-striped table-bordered">
			<tr>
				<th>Action</th>
				<th>Symbol</th>
				<th>Current Price</th>
			</tr>
			<c:forEach items="${allStocks}" var="stock">
				<c:url var="doTransaction" value="/transaction">
					<c:param name="transaction" value="buy"></c:param>
					<c:param name="symbol" value="${stock.symbol}"></c:param>
				</c:url>
				<tr>
					<td><a href="${doTransaction}">Buy</a></td>
					<td>${stock.symbol}</td>
					<td>$<fmt:formatNumber value="${stock.askPrice}"
							maxFractionDigits="2" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</div>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />