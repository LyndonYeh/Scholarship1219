<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>User</title>
<script th:inline="javascript">
	/*<![CDATA[*/
	var expectedCode = /*[[${verificationCode}]]*/'';
	/*]]>*/
</script>
</head>
<body>

	<h2>User List</h2>

	<table border="1">
		<thead>
			<tr>
				<th>USER Name</th>
				<th>Password</th>
				<!-- Add more columns as needed -->
				<th>Action</th>
				<!-- New column for Delete button -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.username}</td>
					<td>${user.password}</td>
					<!-- Add more columns as needed -->
					<td>
						<form action="./deleteUser" method="post">
							<input type="hidden" name="userID" value="${user.userID}" />
							<button type="submit">Delete</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Book List</h2>

	<table border="1">
		<thead>
			<tr>
				<th>Book ID</th>
				<th>Book Name</th>
				<th>User Name</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${books}">
				<tr>
					<td>${book.bookId}</td>
					<td>${book.bookName}</td>
					<td>${book.username1}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	 <h2>Data Insert Form</h2>

    <form action="./adduser1" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>

        <label for="bookName">Book Name:</label>
        <input type="text" id="bookName" name="bookName" required>
        <br>

        <input type="submit" value="Submit">
    </form>
	<!--  
	<h2>Simple Registration</h2>
	
	<form action="./addsimpleuser" method="post" th:object="${user}">
		<label for="username">Username (Email):</label> <input type="text"
			id="username" name="username" th:field="*{username}" required><br>
 
		<label for="password">Password:</label> <input type="text"
			id="password" name="password" th:field="*{password}" required><br>

		<button type="submit">Register</button>
	</form>
	-->
	
	<h2>User Registration</h2>
	<form action="./sendmail" method="post" th:object="${user}"
		id="sendMailForm">

		<label for="username">Username (Email):</label> <input type="text"
			id="username" name="username" th:field="*{username}" required><br>


		<button type="button" id="sendMailButton" onclick="sendMail()">Send
			Mail</button>
		<span id="mailSentMessage" style="display: none;">Mail Sent</span>
	</form>


	<form id="verificationForm" action="./adduser" method="post"
		th:object="${user}">
		<label for="enterCode">Verification Code:</label> <input type="text"
			id="enterCode" name="enterCode" required><br> <input
			type="hidden" id="hiddenUsername" name="hiddenUsername"
			th:field="*{username}">
		<button type="button" id="verifyButton" onclick="verifyCode()">Verify</button>
		<span id="verificationSuccessMessage" style="display: none;">OK</span>

		<div>
			<label for="password">Password:</label> <input type="text"
				id="password" name="password" th:field="*{password}" required><br>
		</div>


		<button type="button" id="submitButton" onclick="submitForm()">Register</button>
	</form>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script>
		function sendMail() {
			var form = $('#sendMailForm');
			var username = $('#username').val();
			// Store the username in the hidden input of the second form
			$('#hiddenUsername').val(username);

			// Perform AJAX request to submit the form
			$.ajax({
				type : form.attr('method'),
				url : form.attr('action'),
				data : {
					username : username
				},
				success : function(data) {
					// Update the page content based on the response
					$('#sendMailButton').hide();
					$('#mailSentMessage').show();
					$('#verifyButton').prop('disabled', false); // Enable the verify button
				},
				error : function(error) {
					console.error('Error sending mail:', error);
				}
			});
		}

		function verifyCode() {
			var enteredCode = $('#enterCode').val();

			// Perform an AJAX request to get the expected verification code from the server
			$
					.ajax({
						type : 'GET',
						url : '/getVerificationCode', // Adjust the URL accordingly
						success : function(data) {
							var expectedCode = data; // Assuming the server responds with the verification code

							if (enteredCode === expectedCode) {
								$('#verifyButton').hide();
								$('#verificationSuccessMessage').show();
								$('#submitButton').prop('disabled', false);
							} else {
								alert('Verification failed. Please enter the correct code.');
							}
						},
						error : function(error) {
							console.error('Error getting verification code:',
									error);
						}
					});
		}

		function submitForm() {
			// Add any additional logic for form submission if needed
			$('#verificationForm').submit();
		}
	</script>
-->
</body>
</html>
