<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
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
<title>會員註冊</title>
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
}
</style>
</head>
<body>
	<div class="d-flex justify-content-center  align-items-center vh-100 "
		class="">
		<form class="row g-3 needs-validation " novalidate method="post"
			action="./register">
			<h4 class="text-center">註冊會員</h4>
			<div class="mb-3">
				<label for="username" class="form-label">使用者電子郵件</label>
				<div class="input-group">
					<input type="text" class="form-control" id="username"
						name="username" value="" required>
					<button class="btn btn-outline-primary" type="submit">發送驗證碼至信箱</button>
				</div>
				<div class="invalid-feedback">請輸入電子郵件</div>
			</div>
			<div class="mb-3">
				<label for="verificationCode" class="form-label">請輸入6位數驗證碼</label>
				<div class="input-group">
					<input type="text" class="form-control digit-input" id="digit1"
						name="digit1" maxlength="1" required> <input type="text"
						class="form-control digit-input" id="digit2" name="digit2"
						maxlength="1" required> <input type="text"
						class="form-control digit-input" id="digit3" name="digit3"
						maxlength="1" required> <input type="text"
						class="form-control digit-input" id="digit4" name="digit4"
						maxlength="1" required> <input type="text"
						class="form-control digit-input" id="digit5" name="digit5"
						maxlength="1" required> <input type="text"
						class="form-control digit-input" id="digit6" name="digit6"
						maxlength="1" required>
					<button class="btn btn-outline-primary" type="submit">驗證</button>
				</div>
			</div>
			
			<!--when mail is verified, disply the below-->
			
			<div class="mb-3">
				<label for="password" class="form-label">密碼
					(至少包含一個大寫及一個小寫英文字母及數字)</label> <input type="password" class="form-control"
					id="password" name="password" value="" required>
				<div class="invalid-feedback">請輸入密碼</div>
			</div>
			<div class="mb-3">
				<label for="cfpassword" class="form-label">密碼確認</label> <input
					type="password" class="form-control" id="cfpassword"
					name="cfpassword" value="" required>
				<div class="invalid-feedback">再次輸入密碼</div>
				<small class="text-danger" id="passwordMismatch">密碼不符</small>
			</div>
			<div class="col-md-5">
				<label for="text" class="form-label">機構名稱</label> <input type="text"
					class="form-control" id="password" name="password" value=""
					required>
				<div class="invalid-feedback">請輸入機構名稱</div>
			</div>
			<div class="col-md-7">
				<label for="password" class="form-label">機構統編</label> <input
					type="password" class="form-control" id="password" name="password"
					value="" required>
				<div class="invalid-feedback">請輸入機構統編</div>
			</div>
			<div class="col-md-5">
				<label for="password" class="form-label">聯絡人</label> <input
					type="password" class="form-control" id="password" name="password"
					value="" required>
				<div class="invalid-feedback">請輸入預設聯絡人姓名</div>
			</div>
			<div class="col-md-7">
				<label for="password" class="form-label">聯絡電話</label> <input
					type="password" class="form-control" id="password" name="password"
					value="" required>
				<div class="invalid-feedback">請輸入聯絡電話</div>
			</div>

			<div class=" d-flex justify-content-center my-3">
				<button class="btn btn-primary" type="submit">註冊</button>
			</div>
		</form>
	</div>

</body>
</html>
<script type="text/javascript">
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
		'use strict'

		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.querySelectorAll('.needs-validation')

		// Loop over them and prevent submission
		Array.prototype.slice
				.call(forms)
				.forEach(
						function(form) {
							form
									.addEventListener(
											'submit',
											function(event) {
												var password = document
														.getElementById('password').value;
												var cfpassword = document
														.getElementById('cfpassword').value;

												// Check if passwords match
												if (password !== cfpassword) {
													document
															.getElementById('passwordMismatch').style.display = 'block';
													event.preventDefault();
													event.stopPropagation();
												} else {
													document
															.getElementById('passwordMismatch').style.display = 'none';
												}

												// Check if password contains at least one uppercase letter, one lowercase letter, and one number
												if (!/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/
														.test(password)) {
													alert('密碼必須至少包含一個大寫及一個小寫英文字母及數字');
													event.preventDefault();
													event.stopPropagation();
												}

												if (!form.checkValidity()) {
													event.preventDefault();
													event.stopPropagation();
												}

												form.classList
														.add('was-validated');
											}, false)
							form.addEventListener('input', function(event) {
								var target = event.target;
								var maxLength = parseInt(target
										.getAttribute('maxlength'));
								var currentLength = target.value.length;

								if (currentLength === maxLength) {
									// Move focus to the next input field
									var nextInput = target.nextElementSibling;
									if (nextInput
											&& nextInput.classList
													.contains('digit-input')) {
										nextInput.focus();
									}
								}
							});
						})
	})();
</script>