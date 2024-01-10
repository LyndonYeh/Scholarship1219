<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="${pageContext.request.contextPath}/mvc/scholarship/backend/reset/${strUUID}" method="post"
                    class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">輸入新密碼</label>

                        <div class="mb-3 input-group">
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                        </div>

                    </div>

                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">確認新密碼</label>

                        <div id="confirmPasswordDiv">
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            <div id="passwordMatch" style="color: red;"></div>
                        </div>
                        
                    </div>
                    <div style="color: red">${resetPasswordErrorMessage}</div>
                    <button id="resetPassword" class="btn btn-outline-primary" type="submit" onclick="validatePassword()">重設密碼</button>
                </form>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        function validatePassword() {
            var newPassword = document.getElementById("newPassword").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var passwordMatch = document.getElementById("passwordMatch");

            if (newPassword !== confirmPassword) {
                passwordMatch.innerHTML = "密碼不一致";
                return false;
            } else {
                passwordMatch.innerHTML = "密碼不一致";
                return true;
            }
        }

        (function() {
            'use strict'

            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.querySelectorAll('.needs-validation')

            // Loop over them and prevent submission
            Array.prototype.slice.call(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!validatePassword() || !form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
        })();
    </script>
</body>
</html>
