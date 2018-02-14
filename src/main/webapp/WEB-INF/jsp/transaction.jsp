<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<script>
	function increase() {
		var textBox = document.getElementById('numberOfShares');
		textBox.value++;
	}
	function decrease() {
		var textBox = document.getElementById('numberOfShares');
		if (textBox.value > 0) {
			textBox.value--;
		}
	}
	function multiplyShares() {
		var numberOfShares = document.getElementById('numberOfShares').value;
		var price = document.getElementById('askPrice').innerText;
		var totalCost = numberOfShares * price;
		var roundedTotalCost = totalCost.toFixed(2);
		document.getElementById('results').innerText = roundedTotalCost;
	}
</script>

<c:url var="submit" value="/transaction"></c:url>
<form method="POST" action="${submit}">
	<input type="hidden" name="stockId" value="${detailedStock.stockId}">
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<h1 class="centered-text">
					<c:if test="${transactionType.equals('buy')}">Buy Stock
					<input type="hidden" name="buySell" value="${transactionType}">
					</c:if>
					<c:if test="${transactionType.equals('sell')}">Sell Stock
					<input type="hidden" name="buySell" value="${transactionType}">
					</c:if>
				</h1>
			</div>
		</div>
		
		<div class="border">
<div class="container-fluid" >
		<div class="row">
			<div class="col-xs-6">Player: ${currentUser.userName}</div>
			<div class="col-xs-6">
				<div class="row">
					<div class="col-xs-12 right-aligned">
						Total Balance: $<fmt:formatNumber value = "${totalBalance}" maxFractionDigits = "2"/>
						<!-- TODO -->
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 right-aligned">
						Available Balance: $<fmt:formatNumber value="${availableBalance}" maxFractionDigits="2"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4 transaction">
				<h2>
					Stock: <br>${detailedStock.companyName}</h2>
			</div>
			<div class="col-xs-2"></div>
			<div class="col-xs-4">

				<c:if test="${transactionType.equals('buy')}">
					<h2>
						Current Price: $<span id="askPrice"><fmt:formatNumber value = "${detailedStock.askPrice}" maxFractionDigits="2"/></span>
					</h2>
				</c:if>
				<c:if test="${transactionType.equals('sell')}">
					<h2>
						Current Bid: $<span id="askPrice"><fmt:formatNumber value = "${detailedStock.bidPrice}" maxFractionDigits = "2"/></span>
					</h2>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4">
				 <input onblur = "multiplyShares();"name="numberOfShares" type="text" value="1"
					id=numberOfShares />
				<button onclick="increase(); multiplyShares();" type="button"
					class="increment-btn">
					<i class="arrow up"></i>
				</button>
				<button onclick="decrease(); multiplyShares();" type="button"
					class="decrement-btn">
					<i class="arrow down"></i>
				</button>
			</div>
			<div class="col-xs-2"></div>
			<div class="col-xs-4">

				<c:if test="${transactionType.equals('buy')}">
					<h2 id="totalCost">
						Total Cost: $<span id="results"><fmt:formatNumber
								value="${detailedStock.askPrice}" maxFractionDigits="2" /></span>
					</h2>
					</c:if>
				<c:if test="${transactionType.equals('sell')}">
					<h2 id="totalCost">
						Total Return: $<span id="results"><fmt:formatNumber
								value="${detailedStock.bidPrice}" maxFractionDigits="2" /></span>
					</h2>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-offset-6 col-xs-2">
			<input class="btn btn-success btn-lg" id="submit" type="submit"
					name="submit" value="submit" />
		</div>
		</div>
			</div>
		</div>
		</div>
		<br>
		<div class="row">

			<div class="col-xs-12">
			
			Industry: ${detailedStock.industry},&nbsp;  
					
			Exchange: ${detailedStock.exchange},&nbsp; 
				 
			CEO: ${detailedStock.CEO}.
				 	
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<p>${detailedStock.description}</p>
			</div>
	</div>
	
	
</form>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />