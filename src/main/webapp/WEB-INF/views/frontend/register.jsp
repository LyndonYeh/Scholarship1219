<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="../../images/icon.png">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<title>註冊頁(獎學網)</title>
<style type="text/css">
body {
background-color:rgb(200,200,200);
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
box-shadow: 0 0 10px rgba(0,0,0, 0.1);
width: 500px;
height: auto;
}


</style>
</head>
<body>
	<div class="d-flex justify-content-center  align-items-center vh-100 " class="">
		<form class="row g-3 needs-validation " novalidate method="post" action="./scholarmain.jsp">
			<h4 class="text-center">註冊資訊</h4>
			<div class="" >
				<label for="username" class="form-label">使用者帳號(信箱)</label> 
				<input type="text" class="form-control"
					id="username" name="username" value="" required>
				 <div class="invalid-feedback">請輸入帳號(信箱)</div>
			</div>
			<div class="">
				<label for="password" class="form-label">密碼</label>
				<input type="password" class="form-control" id="password"
					name="password" value="" required>
				  <div class="invalid-feedback">請輸入密碼</div>
			</div>
			<div class="">
				<label for="cfpassword" class="form-label">密碼確認</label>
				<input type="cfpassword" class="form-control" id="cfpassword"
					name="password" value="" required>
				  <div class="invalid-feedback">再次輸入密碼</div>
			</div>
			<div class="col-md-5">
				<label for="password" class="form-label">機構名稱</label>
				<input type="password" class="form-control" id="password"
					name="password" value="" required>
				  <div class="invalid-feedback">請輸入機構名稱</div>
			</div>
			<div class="col-md-7">
				<label for="password" class="form-label">機構統編</label>
				<input type="password" class="form-control" id="password"
					name="password" value="" required>
				  <div class="invalid-feedback">請輸入機構統編</div>
			</div>
			<div class="col-md-5">
				<label for="password" class="form-label">預設聯絡人</label>
				<input type="password" class="form-control" id="password"
					name="password" value="" required>
				  <div class="invalid-feedback">請輸入預設聯絡人姓名</div>
			</div>
			<div class="col-md-7">
				<label for="password" class="form-label">預設聯絡電話</label>
				<input type="password" class="form-control" id="password"
					name="password" value="" required>
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
				}

				form.classList.add('was-validated')
			}, false)
		})
	})()
</script>