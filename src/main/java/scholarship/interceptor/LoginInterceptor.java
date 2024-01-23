package scholarship.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import scholarship.bean.User;

// 設定 Interceptor 攔截路徑
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String URI = request.getRequestURI();

		// 有 session 的資料
		if (session.getAttribute("user") != null) {

			User user = (User) session.getAttribute("user");
			String urlUserId = String.valueOf(user.getUserId());

			// 修改資料頁 session userId 等於網址userId : 給過
			if (URI.contains("/mvc/scholarship/backend/edit/" + urlUserId)) {
				return true;
			}

			// 等於後台首頁 : 給過
			if (URI.equals("/Scholarship/mvc/scholarship/backend")) {
				return true;
			}

			// 後台包含 copy : 給過
			if (URI.contains("/mvc/scholarship/backend/copy/")) {
				return true;
			}

			if (URI.contains("/mvc/scholarship/backend/garbageCollection")) {
				return true;
			}
			
			// 後台包含 change : 給過
			if (URI.contains("/mvc/scholarship/backend/changeLunch/")) {
				return true;
			}
			
			// 後台包含 reset : 給過
			if (URI.contains("/mvc/scholarship/backend/reset/")) {
				return true;
			}

			// 後台包含 delete : 給過
			if (URI.contains("/mvc/scholarship/backend/delete/")) {
				return true;
			}

			// 後台包含 logout: 給過
			if (URI.contains("/mvc/scholarship/logout")) {
				return true;
			}

		} else {
			
			// 處理 frontend 網址授權	
			// 因為 github attribute 為 username 而非 user, 所以幫他設登出的排除條款
			if (URI.contains("/mvc/scholarship/logout")) {
				return true;
			}		
			
			if (URI.contains("/mvc/scholarship/frontend/")) {
				return true;
			}

			if (URI.contains("/Scholarship/mvc/scholarship/login")) {
				return true;
			}

			// 處理 reset 網址授權 (因為前端 reset 為未登入狀態)
			if (URI.contains("/mvc/scholarship/backend/reset/")) {
				return true;
			}

			if (URI.contains("/Scholarship/secure/callback")) {
				return true;
			}
		}
		System.out.println("Session" + session);
		System.out.println("Session User" + session.getAttribute("user"));
		System.out.println(" request.getRequestURI();" + URI);
		response.sendRedirect(request.getContextPath() + "/mvc/scholarship/login");
		return false;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
