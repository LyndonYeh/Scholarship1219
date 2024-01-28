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
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
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
	// 立即執行函數 (IIFE) 以避免變數污染全域空間
	(function() {
	    'use strict';

	    // 取得所有希望套用自訂 Bootstrap 驗證樣式的表單
	    var forms = document.querySelectorAll('.needs-validation');

	    // 迴圈遍歷這些表單並阻止提交，同時套用 Bootstrap 驗證樣式
	    Array.prototype.slice.call(forms).forEach(function(form) {
	        form.addEventListener('submit', function(event) {
	            // 如果表單未通過驗證，阻止預設的提交行為並停止事件冒泡
	            if (!form.checkValidity()) {
	                event.preventDefault();
	                event.stopPropagation();
	            }

	            // 添加 'was-validated' 類別以套用 Bootstrap 的驗證樣式
	            form.classList.add('was-validated');
	        }, false);
	    });
	})();

	</script>
</body>
</html>
