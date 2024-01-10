<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<body>
<!-- menu -->
		<%@include file="../include/menu_frontend.jspf" %>
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
				<c:forEach items="${scholarships}" var="scholarship">
					<tr>
						<td>${scholarship.scholarshipId }</td>
						<td>${scholarship.institution.name }名稱需注入</td>
						<td>${scholarship.scholarshipName }</td>
						<td>${scholarship.scholarshipAmount}</td>
						<td>${scholarship.institution.contact }聯絡人需注入</td>
						<td>${scholarship.institution.contactNumber }聯絡電話需注入</td>

					</tr>
				</c:forEach>


			</tbody>
		</table>
</body>
</html>