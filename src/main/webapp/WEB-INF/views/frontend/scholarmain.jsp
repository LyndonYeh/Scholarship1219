<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
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
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<!-- 引入 DataTables CSS scholarship 參考 -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.pure-table').DataTable({
			"language" : {
				"url" : "/js/datatables_zh_tw.json"
			},
			dom : 'lBfrtip',
			buttons : [ {
				extend : 'copy',
				text : '複製',
				className : 'pure-button pure-button-primary'
			}, {
				extend : 'csv',
				text : 'CSV',
				className : 'pure-button'
			}, {
				extend : 'excel',
				text : 'EXCEL',
				className : 'pure-button'
			}, {
				extend : 'print',
				text : 'PDF/列印',
				className : 'pure-button pure-button-primary'
			} ]
		});
	});
</script>
</head>
<meta charset="UTF-8">
<title>獎學網首頁</title>


</head>
<body>
	<!-- menu -->
	<%@include file="../include/menu_frontend.jspf"%>

	<tr>

${entId}</p>
${amount}

		<td valign="top"><legend class="m-3">請輸入查詢條件</legend>
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<sp:form modelAttribute="scholarship" method="post"
					action="${pageContext.request.contextPath}/mvc/scholarship/frontend/"
					class="mb-3  ">
					&emsp;
					<p />
					&emsp;身分別:&nbsp;
						<sp:select path="entity">
						<sp:option value="0" label="請選擇"></sp:option> 
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>
							
						&emsp;獎學金額度:&nbsp; <sp:input type="number" path="scholarshipAmount"
						 placeholder="例:50000" />
					<button type="submit">送出</button>
				</sp:form>
			</div></td>
	<tr>
		<!--
	<tr>
		<td valign="top"><legend class="m-3">請輸入查詢條件</legend>
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<form method="post" action="" class="mb-3  ">
					&emsp;
					<p />
					&emsp;身分別:&nbsp;
						<select name="entity">
							<option value="middle school">middle school</option>
							<option value="high school">high school</option>
							<option value="undergraduate">undergraduate</option>
							<option value="graduate">graduate</option>
							<option value="PhD">PhD</option>
						</select> &emsp;年齡:&nbsp; <input class="col-md-2" type="number"
							id="personAge" name="personAge" placeholder="請輸入年齡" max="30"
							min="14" required /> &emsp;獎學金額度:&nbsp; <input type="number"
							id="scholarDegree" name="scholarDegree" placeholder="例:50000"
							required />
						<button type="submit">送出</button></form>
			</div></td>
	<tr>
-->
		<table class="table table-warning">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">獎助機構</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">1</th>
					<td>行天宮</td>
					<td>清寒獎助學金</td>
					<td>50000</td>
					<td>陳小姐 0912345678</td>
					<td>0912345678</td>
				</tr>
				<tr>
					<th scope="row">2</th>
					<td>華山文教基金會</td>
					<td>偏鄉繁星獎勵金</td>
					<td>80000</td>
					<td>劉小姐 0988811112</td>
					<td>0988811112</td>
				</tr>
				<tr>
					<th scope="row">3</th>
					<td>崇禮文教基金會</td>
					<td>小狀元就學獎勵金</td>
					<td>30000</td>
					<td>蔡先生 0977716665</td>
					<td>0977716665</td>
				</tr>
				<tr th:each="scholarship, state : ${ scholarships }">
				<c:forEach items="${scholarships}" var="scholarship">
					<tr>
					<td th:text=${scholarship.scholarshipId }></td>
					<td>${scholarship.institution.institutionName }</td>
					<td>${scholarship.scholarshipName } </td>
					<td>${scholarship.scholarshipAmount} </td>
					<td>${scholarship.institution.contact }</td>
					<td>${scholarship.institution.contactNumber }</td>

					</tr>
				</c:forEach>


			</tbody>
		</table>
</body>
</html>