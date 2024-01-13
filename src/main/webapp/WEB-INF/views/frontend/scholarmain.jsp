<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>

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
<!-- 引入 PureCSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<!-- 引入 DataTables CSS -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
<!-- 引入 jQuery -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- 引入 DataTables -->
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<!-- 引入 DataTables 匯出列印功能 -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.print.min.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$('.pure-table').DataTable({
			// 設定語言為繁體中文
			"language" : {
				"url" : "/js/datatables_zh_tw.json"
			},
			// 設定匯出功能
			dom : 'lBfrtip',
			buttons : [ {
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

	function copySelectedValue() {
		// Get the selected value from the dropdown
		var selectedValue = document.getElementById("amountSelect").value;

		// Set the value of the input field on the right
		document.getElementById("scholarshipAmount").value = selectedValue;
	}
</script>
</head>
<meta charset="UTF-8">
<title>獎學網首頁</title>


</head>
<body>
	<!-- menu -->
	<%@include file="../include/menu.jspf"%>
	<tr>
		<td valign="top"><legend class="m-3">請輸入查詢條件</legend>
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<sp:form modelAttribute="scholarship" method="post"
					action="${pageContext.request.contextPath}/mvc/scholarship/frontend/"
					class="mb-3  ">
					&emsp;
					<p />
					&emsp;身分別:&nbsp;
						<sp:select path="entityId" >
						<sp:option value="0" label="全選"></sp:option>
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>							
						&emsp;獎學金額度(金額為以上):&nbsp; <select id="amountSelect"
						onchange="copySelectedValue()">
						<option value="0">全選</option>
						<option value="10000">5,000</option>
						<option value="10000">10,000</option>
						<option value="20000">20,000</option>
						<option value="50000">50,000</option>
						<option value="100000">100,000</option>
					</select>
					<sp:input type="number" path="scholarshipAmount"
						placeholder="例:50000"/>
					<button type="submit">送出</button>
				</sp:form>
			</div></td>
	<tr>
		<table class="pure-table pure-table-bordered">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">獎助機構</th>
					<th scope="col">身分別</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
				</tr>
			</thead>
			<tbody>
					<c:forEach items="${scholarships}" var="scholarship">
				<tr>

						<td>${scholarship.scholarshipId }</td>
						<td>${scholarship.institution.institutionName }</td>
						<td>${scholarship.entity.entityName}</td>
						<td>${scholarship.scholarshipName }</td>
						<td>${scholarship.scholarshipAmount}${contact}</td>
						<td>${scholarship.institution.contact }</td>
						<td>${scholarship.institution.contactNumber }</td>

				</tr>
					</c:forEach>
			</tbody>
		</table>
</body>
</html>