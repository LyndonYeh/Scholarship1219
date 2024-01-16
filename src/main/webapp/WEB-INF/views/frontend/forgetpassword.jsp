<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forget Password</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">

				<div class="mb-3 ">
					<label for="username" class="form-label">請輸入信箱</label>
					<form
						action="${pageContext.request.contextPath}/mvc/scholarship/frontend/forgetpassword"
						method="post">
						<div class="mb-3 input-group">
							<input type="text" class="form-control" id="username"
								name="username" required>

							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit">傳送驗證碼</button>
						</div>
						<p style="color: red">${forgetErrorMessage}</p>
					</form>
				</div>
			</div>
		</div>
	</div>
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
</body>
</html>
