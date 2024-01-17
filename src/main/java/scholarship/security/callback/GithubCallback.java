package scholarship.security.callback;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.protocol.HttpService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import scholarship.bean.User;
import scholarship.model.dao.UserDao;
import scholarship.service.UserService;
import scholarship.util.OAuth2Util;

@WebServlet(value = "/secure/callback/oauth2")
public class GithubCallback extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain;charset=UTF-8");

		String code = req.getParameter("code");
		resp.getWriter().println("code: " + code);

		// 已有授權碼(code)之後，可以跟 Github 來得到 token (訪問令牌)
		// 有了 token 就可以得到客戶的公開資訊例如: userInfo

		// 1. 根據 code 得到 token
		String token = OAuth2Util.getGitHubAccessToken(code);
		resp.getWriter().println("token: " + token);

		// 2. 透過 token 裡面的 access_token 來取的用戶資訊
		String accessToken = OAuth2Util.parseAccessToken(token);
		resp.getWriter().println("accessToken: " + accessToken);

		// 3. 得到用戶在 Github 上的公開資料
		String userInfo = OAuth2Util.getUserInfoFromGitHub(accessToken);

		// 4. 利用 JSONObject 來分析資料
		JSONObject userInfoObject = new JSONObject(userInfo);
		String username = userInfoObject.getString("login");
		
		HttpSession session = req.getSession();
		session.setAttribute("githubUsername", username);
		resp.getWriter().println(session.getAttribute("githubUsername"));
		
		resp.sendRedirect("http://localhost:8080/Scholarship/mvc/scholarship/frontend");
		
		

	}
}
