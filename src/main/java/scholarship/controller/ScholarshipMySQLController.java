package scholarship.controller;

import java.util.List;
import java.util.Optional;

import javax.faces.annotation.RequestCookieMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.UserDao;
import java.lang.StringBuilder;

@Controller
@RequestMapping("/scholarship")
public class ScholarshipMySQLController {

	// private Logger logger = LoggerFactory.getLogger(getClass());
	/*
	 * 測試用hello為測試用，測試能否從網址連到controller
	 */

	@GetMapping("/hello")
	@ResponseBody
	public String hello(HttpServletRequest req, HttpServletResponse resp) {
		// logger.info("hello, spirng 來了!!!");
		return "Hello, Spring12345 !!";
	}

	/*
	 * 透過網址連到登入頁
	 */

	@GetMapping(value = { "/login", "/", "/login/" })
	public String loginPage() {
		return "/login";
	}
	


	@GetMapping("/pass")
	@ResponseBody
	public String pass() {
		StringBuilder s = new StringBuilder();
		s.append("帳號: dave.wenyu@gmail.com / 明碼 : password1 暗碼: '$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta'")
		.append("帳號: lyndonyeh@gmail.com / 明碼: password2 + 暗碼: '$2a$05$0ppuj4QdEyWAnlNf7IbWFObR9.NH1rSIgTaTJ1WyNvAyd9iWtR7uW'")
		.append("帳號: alicelu@gmail.com /明碼: password3 暗碼:'$2a$05$wbUIDjEtLQ5J8mzwIiDE1Op5nz4N6XNclJxFEtehQg2Bodhj22G2K");
		return s.toString(); 
	}

	//'$2a$05$0ppuj4QdEyWAnlNf7IbWFObR9.NH1rSIgTaTJ1WyNvAyd9iWtR7uW'

	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, Model model) {

		// 根據 username 查找 user 物件
		Optional<User> userOpt = userDao.findUserByUsername(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			// 比對 password
			if (BCrypt.checkpw(password, user.getPassword())) {
				session.setAttribute("user", user); // 將 user 物件放入到 session 變數中
				return "redirect:/mvc/scholarship/backendtest"; // OK, 導向後台首頁
			} else {
				session.invalidate(); // session 過期失效
				model.addAttribute("loginMessage", "密碼錯誤");
				return "login";
			}
		} else {
			session.invalidate(); // session 過期失效
			model.addAttribute("loginMessage", "無此使用者");
			return "login";
		}
	}

	// 登入後重新導向的 後台測試頁, 若 後台 controller 串接好此路徑可刪除
	@GetMapping("/backendtest")
	@ResponseBody
	public String backendtest(HttpServletRequest req, HttpServletResponse resp) {
		return "backendTest 後台測試頁 登入成功 !";

	}

	@GetMapping("/backend")
	public String index(@ModelAttribute Scholarship scholarship, Model model) {
		addBasicModel(model);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "backend/backendmain";
	}

	/*
	 * 首頁基礎資料
	 */

	@Autowired
	private InstitutionDao instiutionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ScholarshipDao scholarshipDao;

	private void addBasicModel(Model model) {
		List<Institution> instiutions = instiutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarship();
		List<User> users = userDao.findAllUsers();

		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}


	

	@PostMapping("/backend") // 新增 scholarship
	public String addScholarship(@Valid Scholarship scholarship, BindingResult result, Model model) { // @Valid 驗證, BindingResult 驗證結果


		// 判斷驗證是否通過?
		if (result.hasErrors()) { // 有錯誤發生
			// 自動會將 errors 的資料放在 model 中

			addBasicModel(model);
			model.addAttribute("submitBtnName", "建立");
			model.addAttribute("_method", "POST");
			model.addAttribute("scholarship", scholarship); // 給 form 表單用的 (ModelAttribute)

			return "backendmain";
		}

		scholarshipDao.addScholarship(scholarship);
		// System.out.println("add User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}

}
