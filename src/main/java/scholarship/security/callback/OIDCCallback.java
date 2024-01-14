package scholarship.security.callback;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimbusds.jwt.JWTClaimsSet;

import scholarship.model.dao.UserDao;
import scholarship.service.UserService;
import scholarship.util.OIDCUtil;


/**
 * 此 Servlet 處理從 Google OAuth 2.0 服務重定向回的 OAuth 2.0 回調。
 * 它將解析授權碼，獲取ID令牌，然後驗證令牌的有效性。
 * 如果令牌有效，它將從ID令牌中提取用戶信息，並將令牌發送到報告服務。
 */


@WebServlet("/secure/callback/oidc")
public class OIDCCallback extends HttpServlet {

// Google 登入
//	private UserService userService;
//
//	@Autowired
//	public OIDCCallback(UserService userService) {
//		this.userService = userService;
//	}
//	
//	@Autowired
//	UserDao userDao; 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
	
		
		// 取得授權碼
		String code = req.getParameter("code");
		resp.getWriter().println("code: " + code + "<p>");
		try {
			// 得到 idToken
			String idToken = OIDCUtil.getIDToken(code);
			resp.getWriter().println("idToken: " + idToken + "<p>");
			
			// 取得 email
			JWTClaimsSet claimsSet = OIDCUtil.getClaimsSetAndVerifyIdToken(idToken);
			String email = claimsSet.getStringClaim("email");
			resp.getWriter().println("email: " + email + "<p>");
			
//			HttpSession session = req.getSession();
//	        userService.loginGoogleUser(email, session);
			
		} catch (Exception e) {
			resp.getWriter().println("Exception: " + e);
		}
		
	}
}