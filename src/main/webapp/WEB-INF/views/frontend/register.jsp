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
	margin-top: 50px;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
</head>
<body>
	<div class="d-flex justify-content-center align-items-center vh-100">
		<form class="needs-validation" novalidate
			action="${pageContext.request.contextPath}/mvc/scholarship/frontend/register"
			method="post">
			<p class="text-center h5">請輸入信箱(使用者帳號)</p>
			<div>
					<p style="color: red"  class="fs-6">${registerErrorMessage}</p>
				<input type="text" class="form-control" id="username"
					name="username" value="dave.wenyu@gmail.com" required>
			</div>
			<div class="d-flex justify-content-evenly my-3">
				<a
					href="${pageContext.request.contextPath}/mvc/scholarship/frontend"
					class="btn btn-outline-secondary">回首頁</a>
				<button class="btn btn-outline-secondary" type="submit">寄送驗證碼</button>
			</div>
		</form>
	</div>
</body>
</html>

