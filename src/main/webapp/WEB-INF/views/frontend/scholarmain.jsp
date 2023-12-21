<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@include file="header.jspf" %>
<body>
	<tr>
		<td valign="top">
				<legend class="m-3">請輸入查詢條件</legend>
				<div class="p-3 border border-2 border-warning bg bg-warning">
			<form method="post" action="" class="mb-3  " >
			&emsp;<p />
				&emsp;身分別:&nbsp;<select name="entity">
								<option value="middle school">middle school</option>
								<option value="high school">high school</option>
								<option value="undergraduate">undergraduate</option>
								<option value="graduate">graduate</option>
								<option value="PhD">PhD</option>
							  </select>
				 &emsp;年齡:&nbsp; <input class="col-md-2" type="number" id="personAge" name="personAge" placeholder="請輸入年齡" max="30" min="14" required />
				 &emsp;獎學金額度:&nbsp; <input type="number" id="scholarDegree" name="scholarDegree"
					placeholder="例:50000" required />
				<button type="submit">送出</button>
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
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">1</th>
					<td>行天宮</td>
					<td>清寒獎助學金</td>
					<td>50000</td>
					<td>陳小姐 0912345678</td>
				</tr>
				<tr>
					<th scope="row">2</th>
					<td>華山文教基金會</td>
					<td>偏鄉繁星獎勵金</td>
					<td>80000</td>
					<td>劉小姐 0988811112</td>
				</tr>
				<tr>
					<th scope="row">3</th>
					<td>崇禮文教基金會</td>
					<td>小狀元就學獎勵金</td>
					<td>30000</td>
					<td>蔡先生 0977716665</td>
				</tr>
				<tr>
					<th scope="row">4</th>
					<td colspan="4" >未完待續</td>
				</tr>
			</tbody>
		</table>
</body>
</html>