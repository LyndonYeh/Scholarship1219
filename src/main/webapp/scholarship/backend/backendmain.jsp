<!--%@page import="Scholarship.entity.ScholarshipUpdateRecord"%> -->
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!-- % List<ScholarshipUpdateRecord> ScholarshipUpdateRecords = (List<ScholarshipUpdateRecord>) request.getAttribute("guestbooks");%> -->
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
<meta charset="UTF-8">
<title>獎學網後台</title>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">獎學網後台</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="../frontend/scholarmain.jsp">前台首頁</a></li>
				<!--<li class="nav-item"><a class="nav-link disabled">Disabled</a>
				</li>-->
			</ul>
			<form class="d-flex" action="../frontend/scholarmain.jsp">
				<button class="btn btn-outline-light" type="submit">登出</button>
			</form>
		</div>
	</div>
</nav>

</head>
<body>
	<tr>
		<td valign="top">
			<table class="table">
				<thead class="bg bg-dark text-light">
					<tr>
						<th>機構名稱</th>
						<th>統一編號</th>
						<th>預設聯絡人</th>
						<th>預設聯絡電話</th>
					</tr>
				</thead>
				<tbody>
					<th scope="row">行天宮</th>
					<td>XXXXXXX</td>
					<td>陳小姐</td>
					<td>02-22223333</td>

				</tbody>
			</table>
			<div class="p-3 border border-2 border-warning bg bg-warning">
				<form method="post" action="" class="mb-3">
					<h2>新增獎學金項目</h2>
					<!-- 名稱旁要有上傳檔案的按鈕 -->
					&emsp;獎學金名稱:&nbsp;<input type="text" id="sname" name="sname"
						placeholder="請輸入名稱" required />
						&emsp;開始日期:&nbsp; <input type="date" id="startDate" name="sDate" /> 
						&emsp;結束日期:&nbsp; <input type="date" id="endDate" name="eDate" required /> 
						&emsp;身分別:&nbsp;<select name="entity">
						<option value="middle school">middle school</option>
						<option value="high school">high school</option>
						<option value="undergraduate">undergraduate</option>
						<option value="graduate">graduate</option>
						<option value="PhD">PhD</option>
					</select>
					<p />
					<p />
					&emsp;最高額度:&nbsp; <input type="number" id="maxvalue" name="maxvalue"placeholder="例:50000" required />
				&emsp;聯絡人:&nbsp; <input type="text" id="contactner" name="contactner"placeholder="同上則免填" required />
				&emsp;聯絡電話:&nbsp; <input type="text" id="contactnernumber" name="contactnumber"placeholder="同上則免填" required />
				<button type="submit">建立</button>
			</form>
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
					<th scope="col">聯絡資訊</th>
					<th scope="col">複製</th>
					<th scope="col">上架</th>
					<th scope="col">刪除</th>
					<th scope="col">狀態更改</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">1</th>
					<td>行天宮</td>
					<td>清寒獎助學金</td>
					<td>50000</td>
					<td>陳小姐 0912345678</td>
					<td><button type="button" onclick="" id="copy" name="copy">複製</button></td>
					<td>&nbsp;<input type="checkbox" id="ontable" name="ontable" /></td>
					<td>&nbsp;<input type="checkbox" id="pushrecycle"
						name="pushrecycle" /></td>
					<td>&nbsp;
						<button type="submit" id="pushconfirm" name="pushconfirm">確認</button>
					</td>
				</tr>
				<!--  <% //for(ScholarshipUpdateRecord sur : ScholarshipUpdateRecords) { %>
				 <tr>
					<td><%// out.print(gb.getId()); %></td>
					<td><%//=gb.getNickname() %></td>
					<td><%//=gb.getAge() %></td>
					<td><%//=gb.getSex() %></td>
					<td><%//=getDateFormatString(gb.getDate()) %></td>
					<td><%//=gb.getMessage() %></td>
					
				</tr>
			//}//} %>-->

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

 /* if (startDate > endDate) {
    startDateError.textContent = ""; // 清空开始日期错误消息
    endDateError.textContent = "结束日期必须大于开始日期";
    endDateInput.focus();
    return false;*/
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
