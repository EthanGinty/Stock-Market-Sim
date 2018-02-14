<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />
<script type="text/javascript">
	$(document).ready(function () {
	
		$("form").validate({
			
			rules : {
				nameOfGame : {
					required : true
				},
				duration : {
					required : true
				}
			},
			messages : {			
				nameOfGame : {
					required : "please enter a name. ya turd"
				},
				duration : {
					required : " think you can get away with blank?"
				}
			},
			errorClass : "error"
		});
	});
</script>
<div class="border">
<div class="container-fluid" >
	<div class="row">
		<div class="col-xs-12">
			<h1 class="centered-text">Create your Game Here</h1>
</div>
	</div>
<c:url var="formAction" value="/createGame">
<param name = "stockData" value = "mock game"></param>
</c:url>

<form method="POST" action="${formAction}">
	<div class="row">
		<div class="col-xs-2">Name of Game:</div> 	
		<input type="text" id="nameOfGame" name="nameOfGame" placeHolder="Name of Game" class="nameOfGame" />
	</div>
	<div class="row">
		<div class="col-xs-2">Duration:</div>
		<input type="text" id="duration" name="duration" placeHolder="Number of Days" class="duration" />
	</div>
	<div class="row">
		<div class="col-xs-2">Mock Game:
		<input type = "checkbox" name = "isFakeGame">
	</div>
	</div>
		<h4 style="color: black;">Which Player's would you like to invite to your game?</h4>
				
	<div>
		<c:forEach items="${users}" var="user">
			<c:if test="${user.userName != currentUser.userName}">
				<label class="players" for="userIds">${user.userName}</label>
				<input type="checkbox" name="userIds" value="${user.userId}" />   
			</c:if>	
		</c:forEach>
		<input type="hidden" name="userIds" value="${currentUser.userId}"/>
	</div>
	<br>
	<div class="formInputGroup">
		<label for="submitBtn"></label>
		<input class="formSubmitButton mt-4 btn btn-primary" id="submitBtn" name="submitBtn" type="submit" value="Submit" />
	</div>
	
</form>
</div>
</div>
<br>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />