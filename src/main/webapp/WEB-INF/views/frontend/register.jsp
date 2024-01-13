<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
	href="../../images/icon.png">
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
	margin-top: 50px;
}
</style>
</head>
<body>
	<div class="d-flex justify-content-center align-items-center vh-100">
		<form class="needs-validation" novalidate
			action="${pageContext.request.contextPath}/mvc/scholarship/frontend/register"
			method="post">
			<h4 class="text-center">請輸入使用者帳號</h4>
			<div>
				<label for="username" class="form-label">註冊使用者帳號</label> <a
					style="color: red">${registerErrorMessage}</a> <input type="text"
					class="form-control" id="username" name="username"
					value="dave.wenyu@gmail.com" required>
			</div>
			<div class="d-flex justify-content-evenly my-3">
				<a href="${pageContext.request.contextPath}/mvc/scholarship/frontend"
					class="btn btn-outline-secondary">回首頁</a>
				<button class="btn btn-outline-primary" type="submit">註冊</button>
			</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	(function() {
		'use strict'
		var forms = document.querySelectorAll('.needs-validation')

		Array.prototype.slice.call(forms).forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
	})()
</script>
