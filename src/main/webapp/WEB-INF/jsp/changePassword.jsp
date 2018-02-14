<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<script type="text/javascript">
	$(document).ready(function () {
		
		$.validator.addMethod('hasUpperCase', function(value) {
			return value.match(/[A-Z]/);
		}, "Password must contain an Upper Case Letter [A-Z]");
		
		$.validator.addMethod('hasLowerCase', function(value) {
			return value.match(/[a-z]/);
		}, "Password must contain a Lower Case Letter [a-z]");
		
		$.validator.addMethod('hasDigit', function(value) {
			return value.match(/\d/);
		}, "Password must contain a Digit [0-9]");
		
		$.validator.addMethod('hasSpecialChar', function(value) {
			return value.match(/[!@#$%^&*()]/);
		}, "Password must contain one of the following: !@#$%^&*()");
		
		$.validator.addMethod('noRepeats', function(value) {
			return !value.match(/(.)(?=\1\1)/);
		}, "Password can't contain the same character repeated three times in a row");
		
		$("form").validate({
			
			rules : {
				password : {
					minlength: 10,
					required : true,
					hasUpperCase: true,
					hasLowerCase: true,
					hasDigit: true,
					hasSpecialChar: true,
					noRepeats: true,
				},
				confirmPassword : {
					required : true,		
					equalTo : "#password"  
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

<c:url var="formAction" value="/users/changePassword" />
<form method="POST" action="${formAction}">
	<div class="row">


		<div class="col-sm-6">
			<div class="form-group">
				<label for="password">New Password: </label> <input type="password"
					id="password" name="password" placeHolder="Password"
					class="form-control" />
			</div>
			<div class="form-group">
				<label for="confirmPassword">Confirm New Password: </label> <input
					type="password" id="confirmPassword" name="confirmPassword"
					placeHolder="Re-Type Password" class="form-control" />
			</div>
			<button type="submit" class="btn btn-default">Change Password</button>
		</div>
		<div class="col-sm-6">
			<strong>Password Rules:</strong>
			<ul>
				<li>Must be at least 10 characters long</li>
				<li>Must contain:
					<ol>
						<li>Uppercase letter (A-Z)</li>
						<li>Lowercase letter (a-z)</li>
						<li>Number (0-9)</li>
						<li>A "special" character (#, $, space, etc)</li>
					</ol>
				</li>
				<li>No more than two identical characters in a row</li>
			</ul>
		</div>
	</div>
</form>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />