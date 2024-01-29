<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
<title>Reset Password</title>
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<form
					action="${pageContext.request.contextPath}/mvc/scholarship/backend/reset/${jwt}"
					method="post" class="needs-validation" novalidate>
					<div class="mb-3">
						<label for="newPassword" class="form-label">輸入新密碼</label>

						<div class="mb-3 input-group">
							<input type="password" class="form-control" id="newPassword"
								name="newPassword" required>
						</div>

					</div>

					<div class="mb-3">
						<label for="confirmPassword" class="form-label">確認新密碼</label>

						<div id="confirmPasswordDiv">
							<input type="password" class="form-control" id="confirmPassword"
								name="confirmPassword" required>
						</div>

					</div>
					<div id="passwordMatch">${resetErrorMessage}</div>
					<button id="resetPassword" class="btn btn-outline-primary"
						type="submit" onclick="validatePassword()">重設密碼</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		// 定義一個名為 validatePassword 的函數，用於檢驗密碼規則及確認密碼是否一致
		function validatePassword() {
			// 取得新密碼和確認密碼的值以及顯示結果的元素
			var newPassword = document.getElementById("newPassword").value;
			var confirmPassword = document.getElementById("confirmPassword").value;
			var passwordMatch = document.getElementById("passwordMatch");
			// 定義密碼的正則表達式，要求至少包含一個大寫、一個小寫字母和一個數字
			var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/;

			// 檢查新密碼和確認密碼是否一致
			if (newPassword !== confirmPassword) {
				// 如果不一致，顯示錯誤訊息並返回 false
				passwordMatch.innerHTML = "密碼不一致";
				passwordMatch.style.color = "red";
				return false;
			} else if (!passwordRegex.test(newPassword)) {
				// 如果新密碼不符合規則，顯示錯誤訊息並返回 false
				passwordMatch.innerHTML = "至少包含一個大寫及一個小寫英文字母及數字";
				passwordMatch.style.color = "red";
				return false;
			} else {
				// 如果通過檢驗，顯示成功訊息並返回 true
				passwordMatch.innerHTML = "重設成功";
				passwordMatch.style.color = "green";
				return true;
			}
		}

		// 立即執行函數 (IIFE) 以避免變數污染全域空間
		(function() {
			'use strict';

			// 取得所有希望套用自訂 Bootstrap 驗證樣式的表單
			var forms = document.querySelectorAll('.needs-validation');

			// 迴圈遍歷這些表單並阻止提交，同時檢查密碼是否通過驗證
			Array.prototype.slice.call(forms).forEach(
					function(form) {
						form.addEventListener('submit', function(event) {
							// 如果密碼驗證不通過或表單驗證不通過，阻止預設的提交行為
							if (!validatePassword() || !form.checkValidity()) {
								event.preventDefault();
								event.stopPropagation();
								// 將表單中的每個輸入元素添加 'is-invalid' 類別以套用驗證樣式
								var inputs = form.querySelectorAll('input');
								Array.prototype.slice.call(inputs).forEach(
										function(input) {
											input.classList.add('is-invalid');
										});
							}
						}, false);
					});
		})();
	</script>
</body>
</html>
