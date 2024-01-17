
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

<<<<<<< HEAD
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
			<tr>
				<td>${ sessionInstitution.institutionId}</td>
				<td>${ sessionInstitution.institutionName}</td>
				<td>${ sessionInstitution.contact}</td>
				<td>${ sessionInstitution.contactNumber}</td>
			</tr>
		</tbody>
	</table>
	<div id="main" class="p-3 bg bg-light">
		<sp:form modelAttribute="scholarship" method="post" class="mb-3"
			action="${pageContext.request.contextPath}/mvc/scholarship/backend">
			<!-- 名稱旁要有上傳檔案的按鈕 -->
			<div class="row g-3">
				<sp:input path="scholarshipId" type="hidden" />
				<input name="_method" type="hidden" value="${ _method }" />
				<!-- hidden -->
				<div class="col-md-auto">
					<sp:input path="scholarshipName" type="text" placeholder="請輸入獎學金名稱"
						class="form-control rounded" />
				</div>
=======
				</tbody>
			</table>
			<div id="main" class="p-3 bg bg-light">
				<sp:form modelAttribute="scholarship" method="post"
					class="mb-3"
					action="${pageContext.request.contextPath}/mvc/scholarship/backend">
					
					<div class="row g-3">
					<sp:input path="scholarshipId" type="hidden" />
					<input name="_method" type="hidden" value="${ _method }" />

					<div class="col-md-auto">
						<sp:input path="scholarshipName" type="text"
							placeholder="請輸入獎學金名稱" class="form-control rounded"/>
					</div>
					<div class="col-md-auto">
					<sp:input path="webUrl" type="text" class="form-control rounded" placeholder="請輸入獎學金網址，例:https://tw.yahoo.com/" style="width: 350px"/>
					</div>
				
					<sp:input path="updatedTime" type="hidden" />

					<div class="col-md-auto">
						<sp:input path="startDate" type="date" class="form-control rounded"/>
					</div>

					<div class="col-md-auto">
						<sp:input path="endDate" type="date" class="form-control rounded"/>
					</div>

					<div class="col-md-auto">
						<sp:select path="entityId" class="form-select" >
							<sp:option value="0" style="color: grey; font-style: italic;"
								label="請選擇身分別"></sp:option>
							<sp:option value="1" label="幼稚園"></sp:option>
							<sp:option value="2" label="小學"></sp:option>
							<sp:option value="3" label="國中"></sp:option>
							<sp:option value="4" label="高中"></sp:option>
							<sp:option value="5" label="大學"></sp:option>
							<sp:option value="6" label="研究所"></sp:option>
						</sp:select>
					</div>

					<div class="col-md-auto">
						<sp:input class="form-control rounded" path="scholarshipAmount" type="number"
							placeholder="請輸入獎學金額度" />
					</div>

					<div class="col-md-auto">
						<sp:input class="form-control rounded" path="contact" type="text"
							placeholder="請輸入聯絡人" />
					</div>

					<div class="col-md-auto">
						<sp:input class="form-control rounded" path="contactNumber" type="text"
							placeholder="請輸入聯絡電話" />
					</div>

					<div class="col-md-auto">
						<button type="submit" type="submit"
							class="btn btn-outline-secondary">${ submitBtnName }</button>
					</div>
			</div> 
			</sp:form>
>>>>>>> 2f3435e55043b0e93302e958484b40795690c579

				<div class="col-md-auto">
					<sp:input path="webUrl" type="text" class="form-control rounded"
						placeholder="請輸入獎學金網址，例:https://123/" style="width: 300px" />
				</div>
				<div class="col-md-auto text-center">
					<label class="text-center mt-2">開始日期</label>
				</div>
				<div class="col-md-auto">

					<sp:input path="startDate" type="date" class="form-control rounded"
						placeholder="Start Date" />
				</div>
				<div class="col-md-auto">
					<label class="text-center mt-2">截止日期</label>
				</div>
				<div class="col-md-auto text-center">
					<sp:input path="endDate" type="date" class="form-control rounded"
						placeholder="End Date" />
				</div>
			</div>
			<div class="row g-3 mt-3">
				<div class="col-md-auto">
					<sp:select path="entityId" class="form-select">
						<sp:option value="0" style="color: grey; font-style: italic;"
							label="請選擇身分別"></sp:option>
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>
				</div>

				<div class="col-md-auto">
					<sp:input class="form-control rounded" path="scholarshipAmount"
						type="number" placeholder="請輸入獎學金額度" />
				</div>

				<div class="col-md-auto">
					<sp:input class="form-control rounded" path="contact" type="text"
						placeholder="請輸入聯絡人" />
				</div>

				<div class="col-md-auto">
					<sp:input class="form-control rounded" path="contactNumber"
						type="text" placeholder="請輸入聯絡電話" />
				</div>

				<div class="col-md-auto">
					<button type="submit" type="submit"
						class="btn btn-outline-secondary">${ submitBtnName }</button>
				</div>
			</div>
		</sp:form>


	</div>

	<table class="table table-light">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">獎助機構</th>
				<th scope="col">獎學金名稱</th>
				<th scope="col">獎學金額度</th>
				<th scope="col">聯絡人</th>
				<th scope="col">聯絡電話</th>
				<th scope="col">截止日期</th>
				<th scope="col">複製</th>
				<th scope="col">上架</th>
				<th scope="col">刪除</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${scholarships}" var="scholarship">
				<tr>
<<<<<<< HEAD
					<td>${scholarship.scholarshipId }</td>
					<td>${scholarship.institution.institutionName }</td>

					<td><a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
					</td>
					<td>${scholarship.scholarshipAmount}</td>
					<td>${scholarship.contact }</td>
					<td>${scholarship.contactNumber }</td>
					<td>${scholarship.endDate }</td>
					<td><a type="button" class="btn btn-warning"
						href="${pageContext.request.contextPath}/mvc/scholarship/backend/copy/${ scholarship.scholarshipId }">複製</a>
					</td>
					<td><a type="button"
						class="btn ${scholarship.isUpdated ? 'btn-success' : 'btn-secondary'}"
						href="${pageContext.request.contextPath}/mvc/scholarship/backend/changeLunch/${scholarship.scholarshipId}"
						style="display: inline-block;"> ${scholarship.isUpdated ? '已上架' : '未上架'}
					</a></td>
					<td><a type="button" class="btn btn-danger"
						href="javascript:void(0);"
						onClick="deleteScholarship(${ scholarship.scholarshipId })">刪除</a>
					</td>
=======
					<th scope="col">編號</th>
					<th scope="col">獎助機構</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
					<th scope="col">複製</th>
					<th scope="col">上架</th>
					<th scope="col">刪除</th>
>>>>>>> 2f3435e55043b0e93302e958484b40795690c579

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
