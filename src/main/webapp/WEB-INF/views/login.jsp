<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<title>獎學網登入頁</title>
<style type="text/css">
body {
	background-color: rgb(200, 200, 200);
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
}

form {
	background-color: #fff;
	padding: 20px;
	padding-top: 40px;
	padding-bottom: 10px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 500px;
	height: auto;
	margin: auto; /* Center the form horizontally */
	margin-top: 50px; /* Add some top margin */
}
</style>
</head>
<body>
	<div class="vh-100 d-flex justify-content-center align-items-center">
		<form class="needs-validation" novalidate method="post"
			action="./login">
			<h4 class="text-center">請登入</h4>
			<div class="">
				<label for="username" class="form-label">使用者帳號</label> <a
					style="color: red">${forgetErrorMessage}</a><input type="text"
					class="form-control" id="username" name="username"
					value="dave.wenyu@gmail.com" required>

			</div>
			<div class="">
				<label for="password" class="form-label">密碼</label> <input
					type="password" class="form-control" id="password" name="password"
					value="password1" required>
				<div class="invalid-feedback">請輸入密碼</div>
			</div>
			<div class="d-flex justify-content-around align-items-center mt-3">
				<button class="btn btn-outline-secondary" type="submit"
					onclick="location.href='./backend/main';">登入</button>
				<a class="btn btn-outline-danger"
					onclick="location.href='./frontend/forgetpassword';">忘記密碼</a> <a
					class="btn btn-outline-secondary"
					onclick="location.href='./frontend';">回到首頁</a>
			</div>

			<div style="color: red">${ loginMessage }</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	//Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
		'use strict'

		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.querySelectorAll('.needs-validation')

		// Loop over them and prevent submission
		Array.prototype.slice.call(forms).forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
					var inputs = form.querySelectorAll('input');
					Array.prototype.slice.call(inputs).forEach(function(input) {
						input.classList.add('is-invalid');
					});
				}

				form.classList.add('was-validated')
			}, false)
		})
	})
</script>
