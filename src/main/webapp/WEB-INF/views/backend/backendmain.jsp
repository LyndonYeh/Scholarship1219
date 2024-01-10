<!--%@page import="Scholarship.entity.ScholarshipUpdateRecord"%> -->
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
<%@include file="../include/menu_backend.jspf" %>
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
				${ sessionInstitution }
					<td scope="row">${ sessionInstitution.institutionId}</td>
					<td>${ sessionInstitution.institutionName}</td>
					<td>${ sessionInstitution.contact}</td>
					<td>${ sessionInstitution.contactNumber}</td>

				</tbody>
			</table>
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<sp:form modelAttribute="scholarship" method="post" cssClass="width:auto"
					action="${pageContext.request.contextPath}/mvc/scholarship/backend">

					<h2>新增獎學金項目</h2>
					<!-- 名稱旁要有上傳檔案的按鈕 -->
					<sp:input path="scholarshipId" type="hidden" />
					<input name="_method" type="hidden" value="${ _method }" />
						
						&emsp;獎學金名稱:&nbsp;<sp:input path="scholarshipName" type="text" />
						<!-- &emsp;上傳時間:&nbsp;  --><sp:input path="updatedTime" type="hidden" /> 
						&emsp;開始日期:&nbsp; <sp:input path="startDate" type="date" /> 
						&emsp;結束日期:&nbsp; <sp:input path="endDate" type="date" />
						<!--&emsp;身分別:&nbsp; <sp:input path="entity" type="number" />-->
						 &emsp;身分別:&nbsp;<sp:select path="entity" >
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>  
					<!--<sp:select path="entity" items="${ entity }" itemLabel="name"
						itemValue="id" />-->
					
						&emsp;額度:&nbsp; <sp:input path="scholarshipAmount" type="number" />
						<p />
						<p />
						&emsp;聯絡人:&nbsp; <sp:input path="institution.contact" type="text" />
						&emsp;聯絡電話:&nbsp; <sp:input path="institution.contactNumber" type="text" />-->
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
				${ scholarships }
				
				<c:forEach items="${scholarships}" var="scholarship">
				 <tr>
					<td>${scholarship.scholarshipId }</td>
					<td>${scholarship.institution.institutionName }</td>
					<td>${scholarship.scholarshipName } </td>
					<td>${scholarship.scholarshipAmount} </td>
					<td>${scholarship.institution.contact }</td>
					<td>${scholarship.institution.contactNumber }</td>
					<td>
						<a type="button" class="btn btn-warning" href="${pageContext.request.contextPath}/mvc/scholarship/backend/copy/${ scholarship.scholarshipId }">複製</a>
					</td>
					<!--  <td>&nbsp;<input type="checkbox" id="isLaunch" name="isLaunch" /></td>
					<td>&nbsp;<input type="checkbox" id="pushrecycle" name="pushrecycle" /></td>-->
					<td>
					<button type="button" style="display: ${scholarship.isUpdated ? 'block' : 'block'}">
    					${scholarship.isUpdated ? '上架中' : '未上架'}</button>
					</td>
					<td>
						<a type="button" class="btn btn-warning" href="javascript:void(0);" onClick="deleteScholarship(${ scholarship.scholarshipId })">刪除</a>
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
