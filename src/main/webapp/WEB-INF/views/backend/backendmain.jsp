<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>
<head>

<script type="text/javascript">
			//驗證刪除
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
			// 驗證過期資訊
			function updateScholarship(scholarshipId, isExpired) {
			    const url2 = '${pageContext.request.contextPath}/mvc/scholarship/backend/changeLunch/' + scholarshipId;
			    const url3 = '${pageContext.request.contextPath}/mvc/scholarship/backend';

			    if (isExpired) {
			        if (confirm('已過期無法上架')) {
			            fetch(url3, { method: 'GET' })
			                .then(response => {
			                    if (response.redirected) {
			                        console.log(response);
			                        location.href = response.url;
			                    } else {
			                        console.log('update fail');
			                    }
			                })
			                .catch(error => {
			                    console.log('update error: ', error);
			                });
			        }
			    } else {
			        fetch(url2, { method: 'GET' })
			            .then(response => {
			                if (response.redirected) {
			                    console.log(response);
			                    location.href = response.url;
			                } else {
			                    console.log('update fail');
			                }
			            })
			            .catch(error => {
			                console.log('update error: ', error);
			            });
			    }
			}
		</script>
<style type="text/css">
.expried {
	color: red;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="../../images/icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<meta name="viewport" charset="UTF-8">
<title>獎學網後台</title>
</head>
<body>
	<%@include file="../include/menu.jspf"%>
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
				<!-- jsp 渲染後臺資料 -->
				<td>${sessionInstitution.institutionId}</td>
				<td>${sessionInstitution.institutionName}</td>
				<td>${sessionInstitution.contact}</td>
				<td>${sessionInstitution.contactNumber}</td>
			</tr>
		</tbody>
	</table>
	<div id="main" class="p-3 bg bg-light container-fluid">
		<sp:form modelAttribute="scholarship" method="post" class="mb-3"
			action="${pageContext.request.contextPath}/mvc/scholarship/backend">
			<div class="row g-3">
				<sp:input path="scholarshipId" type="hidden" />

				<input name="_method" type="hidden" value="${ _method }" />
				<div class="col-xl-3 col-md-12">
					<sp:input path="scholarshipName" type="text" placeholder="請輸入獎學金名稱"
						class="form-control rounded" required="required" />
				</div>
				<div class="col-xl-3 col-md-12">
					<sp:input path="webUrl" type="text" class="form-control rounded"
						placeholder="請輸入獎學金網址，例:https://123/" required="required" />
				</div>
				<div class=" col-xl-1 col-md-12 ">
					<label class=" mt-2">開始日期</label>
				</div>
				<div class="col-xl-2 col-md-12">

					<sp:input path="startDate" type="date" class="form-control rounded"
						placeholder="Start Date" required="required" />
				</div>
				<div class="col-xl-1 col-md-12">
					<label class=" mt-2">截止日期</label>
				</div>
				<div class="col-xl-2 col-md-12 text-center">
					<sp:input path="endDate" type="date" class="form-control rounded"
						placeholder="End Date" required="required" />
				</div>
			</div>
			<div class="row g-3 mt-3">
				<div class="col-xl-2 col-md-12">
					<sp:select path="entityId" class="form-select" required="required">
						<sp:option value="" disabled="disabled"
							style="color: grey; font-style: italic;" label="請選擇身分別">
						</sp:option>
						<sp:option value="1" label="幼稚園"></sp:option>
						<sp:option value="2" label="小學"></sp:option>
						<sp:option value="3" label="國中"></sp:option>
						<sp:option value="4" label="高中"></sp:option>
						<sp:option value="5" label="大學"></sp:option>
						<sp:option value="6" label="研究所"></sp:option>
					</sp:select>
				</div>

				<div class="col-xl-3 col-md-12">
					<sp:input class="form-control rounded" path="scholarshipAmount"
						type="number" placeholder="請輸入獎學金額度" required="required" />
				</div>

				<div class="col-xl-2 col-md-12">
					<sp:input class="form-control rounded" path="contact" type="text"
						placeholder="${sessionInstitution.contact}(預設)" />
				</div>

				<div class="col-xl-3 col-md-12">
					<sp:input class="form-control rounded" path="contactNumber"
						type="text" placeholder="${sessionInstitution.contactNumber}(預設)" />
				</div>

				<div class="col-xl-1 col-md-12">
					<button type="submit" type="submit"
						class="btn btn-outline-secondary">新增</button>
				</div>
			</div>
		</sp:form>


	</div>

	<table class="table table-light">
		<thead>
			<tr>
				<th scope="col">編號</th>
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
					<td>${scholarship.scholarshipId }</td>
					<td>${scholarship.institution.institutionName }</td>

					<td><a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
					</td>
					<td><fmt:formatNumber value="${scholarship.scholarshipAmount}"
							type="currency" pattern="#,##0.##" /></td>
					<td>${scholarship.contact }</td>
					<td>${scholarship.contactNumber }</td>
					<td ${scholarship.isExpired?'class=expried':'' }>${scholarship.stringEndDate}</td>
					<td><a type="button" class="btn btn-warning"
						href="${pageContext.request.contextPath}/mvc/scholarship/backend/copy/${ scholarship.scholarshipId }">複製</a>
					</td>
					<td><a type="button"
						class="btn ${scholarship.isUpdated ? 'btn-success' : 'btn-secondary'}"
						href="javascript:void(1);"
						onClick="updateScholarship(${ scholarship.scholarshipId },${scholarship.isExpired })"
						style="display: inline-block;"> ${scholarship.isUpdated ? '已上架' : '未上架'}
					</a>${scholarship.isExpired?'<img src="https://localhost:8443/Scholarship/images/expired.png" >':'' }</td>
					<td><a type="button" class="btn btn-danger"
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