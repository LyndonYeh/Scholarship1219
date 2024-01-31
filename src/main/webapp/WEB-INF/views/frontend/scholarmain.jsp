<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="/Scholarship/images/icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
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
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- 引入 DataTables -->
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>

<script type="text/javascript"
	src="/Scholarship/js/datatables_zh_tw.json"></script>
<!-- 啟用 https 重導 url -->
<!--  <script type="text/javascript"
	src="https://localhost:8443/Scholarship/js/datatables_zh_tw.json"></script>-->


<!-- 引入 DataTables 匯出列印功能 -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.bootstrap5.min.js"></script>

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
				// 如果啟用 https, port 改為 8443
				"url" : "/Scholarship/js/datatables_zh_tw.json",
			//"url" : "https://localhost:8443/Scholarship/js/datatables_zh_tw.json",
			},
			//設定匯出功能
			dom : 'lBfrtip',
			buttons : [ 'copy', 'csv', 'excel', 'print' ]
		});

		table.buttons().container().appendTo('.pure-table_wrapper .col-md-6');
	});

	function copySelectedValue() {
		// 取得下拉清單中選擇的值
		var selectedValue = document.getElementById("amountSelect").value;

		// 設定右側輸入欄位的值
		document.getElementById("scholarshipAmount").value = selectedValue;
	}
</script>

<!--
body {
	padding-top: 100px;
}-->

<style>
body, html {
	margin: 0;
	padding: 0;
	overflow-x: hidden;
	position: relative;
}

-->
table {
	margin-top: 30px;
}

.full-width-img {
	width: 100vw;
	height: 50vw;
	display: block;
	filter: brightness(50%);
}

.image-title {
	position: absolute;
	top: 40%;
	left: 50%;
	transform: translate(-50%, -50%);
	color: white;
	text-align: center;
	width: 100%;
}

.img-text {
	font-size: 5vw;
}

a.href-text, a.href-text:hover {
	text-decoration: none;
	font-weight: none;
	color: white;
}
</style>
<title>獎學網首頁</title>
</head>
<body>
	<!-- menu -->
	<%@include file="../include/menu.jspf"%>
	<div class="full-width-container">
		<a href="#main"><img class="full-width-img" alt="graduation"
			src="/Scholarship/images/graduation.jpg"></a>
		<div class="image-title">
			<h1 class="img-text">
				<a class="href-text" href="#main">You study, we standby.</a>
			</h1>
		</div>
	</div>
	<div id="main" class="p-3 bg bg-light">
		<sp:form modelAttribute="scholarship" method="post"
			action="${pageContext.request.contextPath}/mvc/scholarship/frontend/"
			class="mb-3">
			<div class="row g-3">
				<div class="col-lg-3 col-md-12">
					<sp:select class="form-select" path="entityId">
						<sp:option value="0" style="color: grey; font-style: italic;"
							label="身分別:全選"></sp:option>
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>
				</div>
				<div class="col-lg-3 col-md-12">
					<select id="amountSelect" class="form-select"
						onchange="copySelectedValue()">

						<option value="0" style="color: grey; font-style: italic;">獎學金額度(以上):全選</option>

						<option value="5000">5,000</option>
						<option value="10000">10,000</option>
						<option value="20000">20,000</option>
						<option value="50000">50,000</option>
						<option value="100000">100,000</option>
					</select>
				</div>
				<div class="col-lg-3 col-md-12">
					<sp:input type="number" path="scholarshipAmount"
						class="form-control rounded" placeholder="或:手動輸入搜尋" />
				</div>
				<div class="col-lg-3 col-md-12">
					<button type="submit" class="btn btn-outline-secondary">送出</button>
				</div>
			</div>
		</sp:form>
	</div>
	<div class="mt-3" style="margin: 20px">
		<table class="pure-table pure-table-bordered">
			<thead>
				<tr>
					<th scope="col">編號</th>
					<th scope="col">獎學金機構</th>
					<th scope="col">身分別</th>
					<th scope="col">名稱</th>
					<th scope="col">額度</th>
					<th scope="col">截止日期</th>
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
						<td><a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
						</td>

						<td><fmt:formatNumber
								value="${scholarship.scholarshipAmount}" type="currency"
								pattern="#,##0.##" /></td>
						<td>${scholarship.stringEndDate}</td>
						<td>${scholarship.institution.contact }</td>
						<td>${scholarship.institution.contactNumber }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>