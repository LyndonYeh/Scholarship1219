
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<script>
    function startCountdown(endDate, countdownElementId) {
        	endDate.setDate(endDate.getDate() + 30);
        function updateCountdown() {
            const now = new Date();
            const timeRemainingInSeconds = Math.floor((endDate - now) / 1000);

            const days = Math.floor(timeRemainingInSeconds / (24 * 60 * 60));
            const hours = Math.floor((timeRemainingInSeconds % (24 * 60 * 60)) / 3600);
            const minutes = Math.floor((timeRemainingInSeconds % 3600) / 60);
            const seconds = timeRemainingInSeconds % 60;

            const countdownString = days + "天 " +
            (hours < 10 ? "0" + hours : hours) + ":" +
            (minutes < 10 ? "0" + minutes : minutes) + ":" +
            (seconds < 10 ? "0" + seconds : seconds);

            if (timeRemainingInSeconds < 0) {
                clearInterval(countdownInterval);
                countdownElement.textContent = '時間到！';
            } else {
                countdownElement.textContent = countdownString;
            }
        }

        const countdownElement = document.getElementById(countdownElementId);
        updateCountdown();

        const countdownInterval = setInterval(updateCountdown, 1000);
    }


</script>
<script type="text/javascript">
			function deleteScholarship(scholarshipId) {
				const url = '${pageContext.request.contextPath}/mvc/scholarship/backend/garbageCollection/' + scholarshipId;
				if(confirm('是否要復原 ?')) {
					fetch(url, {method: 'DELETE'})
					.then(response => {
						if(response.ok || response.redirected) {
							console.log(response);

							location.href = response.url;
						} else {
							console.log('delete fail');
						}
					})
					.catch(error => {
						console.log('delete error: ', error);
					});
				}
			}
		</script>
		<style type="text/css">
		.expried{
		color: red;
		}
		
		
		
		</style>
<link rel="shortcut icon" type="image/x-icon"
	href="../../images/icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<meta charset="UTF-8">
<title>資源回收頁</title>
</head>
<body>
	<%@include file="../include/menu.jspf"%>
	<tr>
		<td valign="top">
			<table class="table">
				<thead class="bg bg-dark text-light">
					<tr>
						<th>統一編號</th>
						<th>機構名稱</th>
						<th>預設聯絡人</th>
						<th>預設聯絡電話</th>
					</tr>
				</thead>
				<tbody>
					<td scope="row">${ sessionInstitution.institutionId}</td>
					<td>${ sessionInstitution.institutionName}</td>
					<td>${ sessionInstitution.contact}</td>
					<td>${ sessionInstitution.contactNumber}</td>

				</tbody>
			</table>

		</td>
	<tr>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">獎助機構</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
					<th scope="col">永久刪除倒計時</th>
					<th scope="col">復原按鈕</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${scholarships}" var="scholarship">
					<tr>
						<td>${scholarship.scholarshipId }</td>
						<td>${scholarship.institution.institutionName }</td>
						<td><a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
						</td>
						<td>${scholarship.scholarshipAmount}</td>
						<td>${scholarship.contact }</td>
						<td>${scholarship.contactNumber }</td>
						<td>
							<div id="${scholarship.scholarshipId}"class="expried"></div> 
							 <script>
            				startCountdown(new Date('${scholarship.updatedTime}'), '${scholarship.scholarshipId}');
        					</script>
    					</td>

						<td><a type="button" class="btn btn-warning"
							href="javascript:void(0);"
							onClick="deleteScholarship(${ scholarship.scholarshipId })">復原</a>
						</td>

					</tr>
				</c:forEach>


			</tbody>
		</table>
</body>

</html>
