
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<!-- % List<ScholarshipUpdateRecord> ScholarshipUpdateRecords = (List<ScholarshipUpdateRecord>) request.getAttribute("cholarshipUpdateRecord");%> -->
<html>
<head>
<script type="text/javascript">
			function deleteScholarship(scholarshipId) {
				const url = '${pageContext.request.contextPath}/mvc/scholarship/backend/delete/' + scholarshipId;
				if(confirm('是否要刪除 ?')) {
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
<title>獎學網後台</title>
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
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<sp:form modelAttribute="scholarship" method="post"
					cssClass="width:auto"
					action="${pageContext.request.contextPath}/mvc/scholarship/backend">

					<h2>新增獎學金項目</h2>
					<sp:input path="scholarshipId" type="hidden" />
					<input name="_method" type="hidden" value="${ _method }" />
						
						&emsp;獎學金名稱:&nbsp;<sp:input path="scholarshipName" type="text" />
						&emsp;獎學金連結:&nbsp;<sp:input path="webUrl" type="text" placeholder="例:https://tw.yahoo.com/" style="width: 300px"/>
						&emsp;開始日期:&nbsp; <sp:input path="startDate" type="date" /> 
						&emsp;結束日期:&nbsp; <sp:input path="endDate" type="date" />
						 &emsp;身分別:&nbsp;<sp:select path="entityId">
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>  
				</P>
						&emsp;額度:&nbsp; <sp:input path="scholarshipAmount" type="number" />
						&emsp;聯絡人:&nbsp; <sp:input path="contact" type="text" />
						&emsp;聯絡電話:&nbsp; <sp:input path="contactNumber"
						type="text" />
					<button type="submit">${ submitBtnName }</button>
				</sp:form>
			</div>
		</td>
	<tr>

		<table class="table table-warning">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">獎助機構</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
					<th scope="col">複製</th>
					<th scope="col">上架</th>
					<th scope="col">刪除</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${scholarships}" var="scholarship">
					<tr>
						<td>${scholarship.scholarshipId }</td>
						<td>${scholarship.institution.institutionName }</td>
						<td>
						<a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
						</td>
						<td>${scholarship.scholarshipAmount}</td>
						<td>${scholarship.contact }</td>
						<td>${scholarship.contactNumber }</td>
						<td><a type="button" class="btn btn-warning"
							href="${pageContext.request.contextPath}/mvc/scholarship/backend/copy/${ scholarship.scholarshipId }">複製</a>
						</td>
						<td><a type="button"
							class="btn ${scholarship.isUpdated ? 'btn-danger' : 'btn-secondary'}"
							href="${pageContext.request.contextPath}/mvc/scholarship/backend/changeLunch/${scholarship.scholarshipId}"
							style="display: inline-block;"> ${scholarship.isUpdated ? '上架中...' : '未上架'}
						</a></td>
						<td><a type="button" class="btn btn-warning"
							href="javascript:void(0);"
							onClick="deleteScholarship(${ scholarship.scholarshipId })">刪除</a>
						</td>

					</tr>
				</c:forEach>


			</tbody>
		</table>
</body>
<script>
	function validateDates() {
		var startDateInput = document.getElementById("startDate");
		var endDateInput = document.getElementById("endDate");
		/* var startDateError = document.getElementById("startDateError");
		 var endDateError = document.getElementById("endDateError");*/

		var startDate = new Date(startDateInput.value);
		var endDate = new Date(endDateInput.value);

		if (startDate > endDate) {
			/*startDateError.textContent = ""; // 清空开始日期错误消息
			endDateError.textContent = "结束日期必须大于开始日期";*/
			endDateInput.focus();
			return false;
		}

		// 如果需要执行其他操作，可以在这里添加代码

		// 如果日期验证通过，清空错误消息
		//startDateError.textContent = "";
		//endDateError.textContent = "";

		// 如果日期验证通过，可以继续提交表单或执行其他操作
		//alert("日期验证通过！可以继续提交表单或执行其他操作");
	}

	// 设置结束日期的最小值为初始日期
	document.getElementById("startDate").addEventListener("input", function() {
		document.getElementById("endDate").min = this.value;
	});
</script>
</html>
