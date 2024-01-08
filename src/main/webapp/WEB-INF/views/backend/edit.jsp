<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>修改資訊</title>
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
	${user}
	<div>
		<h2>Test Area</h2>
		<p>User Id: ${user.userId}</p>
		<p>User Name: ${user.username}</p>
		<p>Session: ${session}</p>
		<p>SessionUser: ${session.username}</p>
		<p>${sessionInstitution.get().getContactNumber()}</p>
		<p>SessionInstitution: ${sessionInstitution}</p>
	</div>
	<div class="d-flex justify-content-center  align-items-center vh-100 "
		class="">
		<form class="row g-3 needs-validation" novalidate method="post"
			action="./${user.userId}/confirmed">
			<h4 class="text-center">修改資訊</h4>
			<div class="col-md-12">
				<label for="username" class="form-label">使用者帳號(信箱)</label> <input
					type="text" class="form-control" id="username" name="username"
					value="${session.username}" required>
				<div class="invalid-feedback">請輸入帳號(信箱)</div>
			</div>
			<!-- Other input fields for password, cfpassword, etc. -->
			<div class="col-md-5">
				<label for="contact" class="form-label">預設聯絡人</label> <input
					type="text" class="form-control" id="contact" name="contact"
					value="${sessionInstitution.get().getContact()}" required>
				<div class="invalid-feedback">修改聯絡人姓名</div>
			</div>
			<div class="col-md-7">
				<label for="contactNumber" class="form-label">修改聯絡電話</label> <input
					type="text" class="form-control" id="contactNumber"
					name="contactNumber"
					value="${sessionInstitution.get().getContactNumber()}" required>
				<div class="invalid-feedback">請輸入聯絡電話</div>
			</div>
			<!-- Other input fields for verification code, mail button, and confirm button -->
			<div class="col-md-5 mt-5">
				<form id="verificationForm" action="/backend/mail" method="post">
					<!-- Your form fields go here -->
					<button type="submit" class="btn btn-outline-danger">寄送驗證碼至信箱</button>
				</form>
				<div class=" d-flex justify-content-center my-3">
					<button class="btn btn-primary" type="submit">確認修改</button>
				</div>
			</div>

			<!-- Placeholder for form validation messages -->
			<c:if test="${not empty param.error}">
				<div class="alert alert-danger mt-3" role="alert">
					${param.error}</div>
			</c:if>

			<!-- Placeholder for specific validation messages -->
			<!-- Add more if needed -->

		</form>
	</div>

</body>
<script type="text/javascript">
	
</script>
</html>
