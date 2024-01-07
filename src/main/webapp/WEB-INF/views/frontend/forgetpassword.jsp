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
					<form action="./forget" method="post" >
						<div class="mb-3 input-group">
							<input type="text" class="form-control" id="username"
								name="username" required>
							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit">傳送驗證碼</button>
						</div>
					</form>
					
				</div>

				<div class="mb-3">
					<label for="verificationCode" class="form-label">請輸入6位數驗證碼</label>
					<form>
						<div class="input-group" id="verificationCode">
							<input type="text" class="form-control digit-input" id="digit1"
								name="digit1" maxlength="1" required> <input type="text"
								class="form-control digit-input" id="digit2" name="digit2"
								maxlength="1" required> <input type="text"
								class="form-control digit-input" id="digit3" name="digit3"
								maxlength="1" required> <input type="text"
								class="form-control digit-input" id="digit4" name="digit4"
								maxlength="1" required> <input type="text"
								class="form-control digit-input" id="digit5" name="digit5"
								maxlength="1" required> <input type="text"
								class="form-control digit-input" id="digit6" name="digit6"
								maxlength="1" required>
							<button id="verifyCode" class="btn btn-outline-primary"
								type="submit" onclick="sendMail()">驗證</button>
						</div>
					</form>
				</div>


				</form>
			</div>
		</div>
	</div>
	<script>
    function moveToNextInput(currentInput, nextInputId) {
        var maxLength = parseInt(currentInput.getAttribute('maxlength'));
        var currentLength = currentInput.value.length;

        if (currentLength === maxLength) {
            document.getElementById(nextInputId).focus();
        }
    }
</script>
</body>
</html>
