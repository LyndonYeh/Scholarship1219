
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<script>
//定義一個名為 startCountdown 的函數，接受結束日期（endDate）和倒數計時元素的 ID（countdownElementId）作為參數
    function startCountdown(endDate, countdownElementId) {
        	endDate.setDate(endDate.getDate() + 30);
        // 將結束日期設定為當前日期的30天後
        function updateCountdown() {
        	// 取得當前日期
            const now = new Date();
            const timeRemainingInSeconds = Math.floor((endDate - now) / 1000);

            // 計算天數、小時、分鐘和秒數
            const days = Math.floor(timeRemainingInSeconds / (24 * 60 * 60));
            const hours = Math.floor((timeRemainingInSeconds % (24 * 60 * 60)) / 3600);
            const minutes = Math.floor((timeRemainingInSeconds % 3600) / 60);
            const seconds = timeRemainingInSeconds % 60;

        	 // 構建倒數計時字串，以顯示剩餘時間
            const countdownString = days + "天 " +
            (hours < 10 ? "0" + hours : hours) + ":" +
            (minutes < 10 ? "0" + minutes : minutes) + ":" +
            (seconds < 10 ? "0" + seconds : seconds);

       		// 如果剩餘時間小於零，表示時間到，停止倒數計時並顯示提示訊息
            if (timeRemainingInSeconds < 0) {
                clearInterval(countdownInterval);
                countdownElement.textContent = '時間到！';
            } else {
            	// 更新倒數計時元素的內容為倒數計時字串
                countdownElement.textContent = countdownString;
            }
        }

     // 取得倒數計時元素
        const countdownElement = document.getElementById(countdownElementId);
     // 初始化倒數計時的顯示   
     updateCountdown();

 	 // 設定每秒更新一次倒數計時的間隔，並保存 setInterval 的引用以便之後清除
        const countdownInterval = setInterval(updateCountdown, 1000);
    }


</script>
<script type="text/javascript">
//定義一個名為 deleteScholarship 的函數，接受學習資助的 ID（scholarshipId）作為參數
			function deleteScholarship(scholarshipId) {
				// 構建刪除資助的 URL，使用模板字面量插入學習資助的 ID			
				const url = '${pageContext.request.contextPath}/mvc/scholarship/backend/garbageCollection/' + scholarshipId;
				// 使用 confirm 函數顯示確認對話框，確認是否要復原
				if(confirm('是否要復原 ?')) {
					fetch(url, {method: 'DELETE'})
					.then(response => {
						// 檢查回應是否成功或已重定向
						if(response.ok || response.redirected) {
							console.log(response);
							// 如果成功，將頁面重新導向到回應的 URL
							location.href = response.url;
						} else {
							// 如果失敗，輸出錯誤訊息到控制台
							console.log('delete fail');
						}
					})
					.catch(error => {
						// 捕獲任何發生的錯誤，並輸出到控制台
						console.log('delete error: ', error);
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
	href="/Scholarship/images/icon.png">
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
<meta charset="UTF-8">
<style>
body {
	padding-top: 70px;
}
</style>
<title>資源回收頁</title>

</head>
<body>
	<%@include file="../include/menu.jspf"%>
	<div id="main">

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


		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">獎助機構</th>
					<th scope="col">獎學金名稱</th>
					<th scope="col">獎學金額度</th>
					<th scope="col">聯絡人</th>
					<th scope="col">聯絡電話</th>
					<th scope="col">永久刪除倒計時</th>
					<th scope="col">復原按鈕</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${scholarships}" var="scholarship">
					<tr>
						<td>${scholarship.scholarshipId }</td>
						<td>${scholarship.institution.institutionName }</td>
						<td><a href="${scholarship.webUrl}">${scholarship.scholarshipName }</a>
						</td>
						<td>${scholarship.scholarshipAmount}</td>
						<td>${scholarship.contact }</td>
						<td>${scholarship.contactNumber }</td>
						<td>
							<div id="${scholarship.scholarshipId}" class="expried"></div> <script>
            				startCountdown(new Date('${scholarship.updatedTime}'), '${scholarship.scholarshipId}');
        					</script>
						</td>

						<td><a type="button" class="btn btn-warning"
							href="javascript:void(0);"
							onClick="deleteScholarship(${ scholarship.scholarshipId })">復原</a>
						</td>

					</tr>
				</c:forEach>


			</tbody>
		</table>
	</div>
</body>

</html>
