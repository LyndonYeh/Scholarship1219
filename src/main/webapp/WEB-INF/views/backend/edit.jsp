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
	
	<div>
		<h2>Test Area</h2>
		<p>${session}</p>
		<p>${session.password}</p>
		<p>${sessionInstitution}</p>
	</div>
	<div class="d-flex justify-content-center  align-items-center vh-100 "
		class="">
		<form class="row g-3 needs-validation" novalidate method="post"
      action="${pageContext.request.contextPath}/mvc/scholarship/backend/edit/${session.userId}">
    <h4 class="text-center">修改資訊</h4>
    <div class="col-md-12">
        <label for="username" class="form-label">使用者帳號(信箱)</label>
        <input type="text" class="form-control" id="username" name="username"
               value="${session.username}" disabled required>
        <div class="invalid-feedback">請輸入帳號(信箱)</div>
    </div>
    <div class="col-md-5">
        <label for="currentContact" class="form-label">目前預設聯絡人</label>
        <input type="text" class="form-control" id="currentContact"
               value="${sessionInstitution.get().getContact()}" readonly>
        <label for="contact" class="form-label">預設聯絡人</label>
        <input type="text" class="form-control" id="contact" name="contact"
               value="" required maxlength="50">
        <div class="invalid-feedback">請輸入修改聯絡人姓名</div>
    </div>
    <div class="col-md-7">
        <label for="currentContactNumber" class="form-label">目前聯絡電話</label>
        <input type="text" class="form-control" id="currentContactNumber"
               value="${sessionInstitution.get().getContactNumber()}" readonly>
        <label for="contactNumber" class="form-label">欲修改聯絡電話</label>
        <input type="text" class="form-control" id="contactNumber"
               name="contactNumber" value="" required maxlength="10">
        <div class="invalid-feedback">請輸入聯絡電話(最大長度10碼)</div>
    </div>

    <div class="col-md-5">
        <label for="password" class="form-label">請輸入密碼</label>
        <input type="password" class="form-control" id="password" name="password"
               value="" required>
        <div class="invalid-feedback">請輸入密碼</div>
        <div style="color: red">${editErrorMessage}</div>
    </div>
    <button class="btn btn-primary" type="submit">確認修改</button>
</form>

	</div>
	

</body>
<script type="text/javascript">
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
			}

			form.classList.add('was-validated')
		}, false)
	})
})()
</script>
</html>
