<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/common/header.jsp" />


<script type="text/javascript">
	$(document).ready(function () {
	
		$("form").validate({
			
			rules : {
				userName : {
					required : true
				},
				password : {
					required : true
				}
			},
			messages : {			
				confirmPassword : {
					equalTo : "Passwords do not match"
				}
			},
			errorClass : "error"
		});
	});
</script>

<div class="row login-image" >
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<c:url var="formAction" value="/login" />
		<form method="POST" action="${formAction}">
		<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
		<input type="hidden" name="destination" value="${param.destination}"/>
			<div class="form-group">
				<label for="userName">User Name: </label>
				<input type="text" id="userName" name="userName" placeHolder="User Name" class="form-control" />
			</div>
			<div class="form-group">
				<label for="password">Password: </label>
				<input type="password" id="password" name="password" placeHolder="Password" class="form-control" />
			</div>
			<c:url var="newUser" value ="/register"/>
			<a href="${newUser}"> New user sign up </a>
			<br>
			<br>
			<button type="submit" class="btn btn-default">Login</button>
			<c:url var = "cancel" value="/"/>
			<a href="${cancel}">
			<button type="button" class="btn btn-default">Cancel</button>
			</a>
		</form>
	</div>
	<div class="col-sm-4"></div>
</div>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />