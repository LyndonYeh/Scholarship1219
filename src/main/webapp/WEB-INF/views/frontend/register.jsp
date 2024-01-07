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
					<button id="send" class="btn btn-outline-primary" type="button" onclick="sendVerificationCode()">發送驗證碼至信箱</button>
				</div>
				<div class="invalid-feedback">請輸入電子郵件</div>
			</div>
			
			<div class="mb-3">
				<label for="verificationCode" class="form-label">請輸入6位數驗證碼</label>
				<div class="input-group" id="verificationCode">
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
					<button id="verifyCode"class="btn btn-outline-primary" type="submit" onclick="sendMail()">驗證</button>
				</div>
			</div>
			
			<!--when mail is verified, display the below-->
			<div id="register" class="registration-form-container">
			<div class="mb-3">
				<label for="password" class="form-label">密碼
					(至少包含一個大寫及一個小寫英文字母及數字)</label><input type="password" class="form-control"
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
				<label for="institutionName" class="form-label">機構名稱</label> <input type="text"
					class="form-control" id="institutionName" name="institutionName" value=""
					required>
				<div class="invalid-feedback">請輸入機構名稱</div>
			</div>
			<div class="col-md-7">
				<label for="institutionId" class="form-label">機構統編</label> <input
					type="text" class="form-control" id="institutionId" name="institutionId"
					value="" required>
				<div class="invalid-feedback">請輸入機構統編</div>
			</div>
			<div class="col-md-5">
				<label for="contact" class="form-label">聯絡人</label> <input
					type="text" class="form-control" id="contact" name="contact"
					value="" required>
				<div class="invalid-feedback">請輸入預設聯絡人姓名</div>
			</div>
			<div class="col-md-7">
				<label for="contactNumber" class="form-label">聯絡電話</label> <input
					type="text" class="form-control" id="contactNumber" name="contactNumber"
					value="" required>
				<div class="invalid-feedback">請輸入聯絡電話</div>
			</div>

			<div class=" d-flex justify-content-center my-3">
				<button class="btn btn-primary" type="submit">註冊</button>
			</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	// 調查這行 code
	function sendVerificationCode() {
        var username = document.getElementById('username').value;

        fetch('/Scholarship/mvc/scholarship/sendmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
            }),
        })
        .then(response => {
            if (response.ok) {
                console.log('Verification code sent successfully');
                
             
                sendMail();
            } else {
                console.error('Failed to send verification code');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }


 
</script>
</body>
</html>
