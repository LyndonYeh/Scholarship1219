<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Service User</title>
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
				<th>Service User List</th>
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
	
	<h2>Service Book List</h2>

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

    <form action="./service" method="post">
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

</body>
</html>
