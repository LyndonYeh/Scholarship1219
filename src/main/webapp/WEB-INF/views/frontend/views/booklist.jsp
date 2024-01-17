<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Book List</title>
<style>
body {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
}

.left-column {
	width: 70%; /* Adjust the width of the left column */
}

.right-column {
	width: 28%; /* Adjust the width of the right column */
}
</style>
</head>
<body>

	<div class="left-column">
		<h2>Book and User List</h2>
		<table border="1">
        <thead>
            <tr>
                <th>User ID</th>
                <th>Username</th>
                <th>Book ID</th>
                <th>Book Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.user.userId}</td>
                    <td>${book.user.username}</td>
                    <td>${book.bookId}</td>
                    <td>${book.bookName}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	</div>
	

	<div class="right-column">
		<%@ include file="addbookanduser.jsp"%>
	</div>

</body>
</html>
