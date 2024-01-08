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
					<form action="./forgetpassword" method="post" >
						<div class="mb-3 input-group">
							<input type="text" class="form-control" id="username"
								name="username" required>
							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit">傳送驗證碼</button>
								<!-- 比對 username; yes : 驗證碼已寄送 no : 無效的信箱地址 -->
						</div>
					</form>					
				</div>

				<div class="mb-3">
					<label for="verificationCode" class="form-label">請輸入6位數驗證碼</label>
					<form>
						<div class="input-group" id="verificationCode">
							<input type="text" class="form-control digit-input" id="verificationCode"
								name="verificationCode" maxlength="61" required> 
							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit" onclick="sendMail()">驗證</button>
								<!-- 比對 session 驗證碼; yes : 導至 reset password page no :  -->
						</div>
					</form>
				</div>


				</form>
			</div>
		</div>
	</div>
	<script>
   
</script>
</body>
</html>
