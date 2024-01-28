<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>請輸入驗證碼</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
</head>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">

				<div class="mb-3">
					<label for="verificationCode" class="form-label">請輸入6位數驗證碼</label>
					<form
						action="${pageContext.request.contextPath}/mvc/scholarship/frontend/forgetpasswordverify"
						method="post">
						<div class="input-group" id="verificationCode">
							<input type="text" class="form-control digit-input"
								id="verifyCode" name="verifyCode" maxlength="6" required>
							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit">驗證</button>
						</div>
						<div style="color: red">${forgetErrorMessage}</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>