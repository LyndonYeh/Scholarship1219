<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<title>Enter User and Book Data</title>
</head>
<body>

	<h2>Add User and Book</h2>
	<form:form modelAttribute="user" method="post"
		action="./addbookanduser">
		<!-- User fields -->
		<label for="username">Username:</label>
		<form:input path="username" id="username" required="true" />

	</form:form>

	<form:form modelAttribute="book" method="post"
		action="./addbookanduser">
		<!-- Book fields -->
		<label for="bookName">Book Name:</label>
		<form:input path="bookName" id="bookName" required="true" />

		<!-- Add more book fields as needed -->

		<input type="submit" value="Submit" />
	</form:form>
</body>
</html>
