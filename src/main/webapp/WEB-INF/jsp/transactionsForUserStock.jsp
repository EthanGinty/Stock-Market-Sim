<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<div>
	<table>
		<tr>
			<c:choose>
				<c:when test="${transaction.stock.bidPrice >= transaction.price}">
					<td style="color: green">+$<fmt:formatNumber
							value="${(transaction.stock.bidPrice -transaction.price)*transaction.numberOfShares}"
							maxFractionDigits="2" /><br>
					</td>
				</c:when>
				<c:otherwise>
					<td style="color: red">-$<fmt:formatNumber
							value="${(transaction.price -
									transaction.stock.bidPrice)*transaction.numberOfShares}"
							maxFractionDigits="2" /><br>
					</td>
				</c:otherwise>
			</c:choose>
		<tr>
	</table>
</div>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />