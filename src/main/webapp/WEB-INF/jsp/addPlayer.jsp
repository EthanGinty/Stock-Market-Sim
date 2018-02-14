<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<h3>Game Name: ${selectedGame.nameOfGame}</h3>

<c:url var="formAction" value="/addPlayer"/>
<form method="POST" action="${formAction}">
	<c:forEach items="${usersNotInGame}" var="user">
		<br><input type="checkbox" name="userIds" value="${user.userId}">${user.userName}
	</c:forEach>
	<br><label for="submitBtn"></label>
	<input class="formSubmitButton" id="submitBtn" name="submitBtn" type="submit" value="Submit" />
</form>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />