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


	<div class="d-flex justify-content-center  align-items-center vh-100 "
		class="">
		<form class="row g-3 needs-validation" novalidate method="post"
			action="${pageContext.request.contextPath}/mvc/scholarship/backend/edit/${session.userId}">
			<h4 class="text-center">修改資訊</h4>


			<div class="input-group mb-3">
				<span class="input-group-text">目前使用者</span><input type="text"
					class="form-control" aria-label="currentUsername"
					value="${session.username}" readonly>
			</div>


			<div class="input-group mb-3">
				<span class="input-group-text">目前預設聯絡人</span><input type="text"
					class="form-control" aria-label="currentContact"
					value="${sessionInstitution.get().getContact()}" readonly>
			</div>


			<div class="input-group mb-3">
				<span class="input-group-text">欲修改聯絡人</span><input type="text"
					class="form-control" aria-label="Contact" id="contact"
					name="contact" value="" maxlength="50"required>
					<div class="invalid-feedback">請輸入聯絡人</div>
			</div>


			<div class="input-group mb-3">
				<span class="input-group-text">目前預設聯絡電話</span><input type="text"
					class="form-control" aria-label="currentContactNumber"
					value="${sessionInstitution.get().getContactNumber()}" readonly>
			</div>


			<div class="input-group mb-3">
				<span class="input-group-text">欲修改連絡電話</span><input type="text"
					class="form-control" aria-label="currentContact" id="contactNumber"
					name="contactNumber" value="" maxlength="10" required>
				<div class="invalid-feedback">請輸入聯絡電話(最大長度10碼)</div>
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text">請輸入密碼</span><input type="password"
					class="form-control" aria-label="password" id="password"
					name="password" value="" required>

				<div style="color: red">${editErrorMessage}</div>
			</div>
			<p>
				如欲修改密碼請至<a class="fw-light"
					href="${pageContext.request.contextPath}/mvc/scholarship/frontend/forgetpassword">重設密碼</a>
			</p>

			<div class="row g-3 justify-content-between">
				<div class="col-md-auto ">
					<a type="text" class="btn btn-outline-secondary"
						onclick="location.href='${pageContext.request.contextPath}/mvc/scholarship/backend'">回後台</a>
				</div>

				<div class="col-md-auto">
					<button type="submit" type="submit" class="btn btn-outline-danger">確認修改</button>
				</div>
			</div>

		</form>

	</div>


</body>
<script type="text/javascript">
	//匿名函數，立即執行函數 (IIFE) 以避免變數污染全域空間
	(function() {
		'use strict'

		// 取得所有希望套用自訂 Bootstrap 驗證樣式的表單
		var forms = document.querySelectorAll('.needs-validation')

		// 迴圈遍歷這些表單並阻止預設提交行為
		Array.prototype.slice.call(forms).forEach(function(form) {
			form.addEventListener('submit', function(event) {
				// 如果表單未通過驗證，阻止事件的預設行為並停止事件冒泡
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}
				// 添加 'was-validated' 類別以套用 Bootstrap 的驗證樣式
				form.classList.add('was-validated')
			}, false)
		})
	})()
</script>
</html>
