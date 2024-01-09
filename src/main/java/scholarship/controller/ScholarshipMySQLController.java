package scholarship.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.faces.annotation.RequestCookieMap;
import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.cj.Session;

import scholarship.*;
import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.UserDao;
import scholarship.model.sqlimpl.UserMySQL;
import scholarship.service.EmailService;
import scholarship.service.UserService;
import scholarship.util.RandomNumberGenerator;

import java.lang.StringBuilder;
import java.net.HttpURLConnection;

@Controller
@RequestMapping("/scholarship")
public class ScholarshipMySQLController {

	private final UserService userService;

	@Autowired
	public ScholarshipMySQLController(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private InstitutionDao institutionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ScholarshipDao scholarshipDao;

	// private Logger logger = LoggerFactory.getLogger(getClass());

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
		s.append("帳號: dave.wenyu@gmail.com / 密碼 : password1 '").append("帳號: lyndonyeh@gmail.com / 密碼: password2 '")
				.append("帳號: alicelu@gmail.com /碼密碼: password3 ");
		return s.toString();
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, Model model) {
		return userService.loginUser(username, password, session, model);
	}

	@RequestMapping("/frontend/forgetpassword")
	public String forget(Model model) {
		return "/frontend/forgetpassword";
	}

	@PostMapping("/frontend/forgetpassword")
	public String forgetVerificationCode(@RequestParam("username") String username, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws MessagingException {
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList());
		if (usernames.contains(username)) {
			session.setAttribute("userEmail", username);
			String toEmail = username;

			String verificationCode = RandomNumberGenerator.generateRandomCode();
			session.setAttribute("verificationCode", verificationCode);
			try {
				EmailService.sendVerificationCode(toEmail, verificationCode);
			} catch (MessagingException e) {
				redirectAttributes.addFlashAttribute("forgetErrorMessage", "信箱錯誤");
			}
			return "redirect:/mvc/scholarship/frontend/forgetpassword";
		} else {
			redirectAttributes.addFlashAttribute("forgetErrorMessage", "信箱錯誤");
			return "redirect:/mvc/scholarship/frontend/forgetpassword";
		}
	}

	@PostMapping("/frontend/verify")
	public String verifyCode(@RequestParam("verifyCode") String verifyCode, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		UUID uuid = UUID.randomUUID();
		String strUUID = uuid.toString();
		String verificationCode = (String) session.getAttribute("verificationCode");
		if (verifyCode.equals(verificationCode)) {
			session.setAttribute("strUUID", strUUID);
			return "redirect:/mvc/scholarship/backend/reset/" + strUUID;
		}
		redirectAttributes.addFlashAttribute("forgetErrorMessage", "驗證碼錯誤");
		return "redirect:/mvc/scholarship/frontend/forgetpassword";

	}

	@GetMapping("/backend/reset/{strUUID}")
	public String showReset(@PathVariable("strUUID") String strUUID, Model model, HttpSession session) {
		String sessionStrUUID = (String) session.getAttribute("strUUID");
		model.addAttribute("strUUID",sessionStrUUID);
		return "/backend/reset";
	}

	@PostMapping("/backend/reset/{strUUID}")
	public String resetPassword(@PathVariable("strUUID") String strUUID,
	                             @RequestParam("newPassword") String newPassword,
	                             Model model, HttpSession session ) {
	    userService.resetPassword( strUUID, newPassword, session, model);
	    return "redirect:/mvc/scholarship/login";
	}

	@GetMapping(value = { "/register", "/register/" })
	public String registerPage() {
		return "/frontend/register";
	}

	@PostMapping("/register")
	public String register(@RequestParam("password") String password,
			@RequestParam("institutionName") String institutionName,
			@RequestParam("institutionId") String institutionId, @RequestParam("contact") String contact,
			@RequestParam("contactNumber") String contactNumber,
			@RequestParam("verificationCode") String verificationCode, Model model, HttpSession session) {
		String sessionVerifiedCode = (String) session.getAttribute("verificationCode");
		String username =(String)session.getAttribute("username");
		if (verificationCode.equals(sessionVerifiedCode)) {
			try {
				userService.registerUser(username, password, institutionName, institutionId, contact, contactNumber,
						session);
				return "redirect:login";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("error", "Error occurred during registration.");
				return "error";
			}

		}
		return "error";
	}

	@PostMapping("/sendRegisterVerificationCode")
	public String sendRegisterVerificationCode(@RequestParam String username, HttpSession session)
			throws MessagingException {

		String toEmail = username;
		String verificationCode = RandomNumberGenerator.generateRandomCode();
		session.setAttribute("username", username);
		session.setAttribute("verificationCode", verificationCode);
		try {
			EmailService.sendVerificationCode(toEmail, verificationCode);
		} catch (MessagingException e) {
			return "error";
		}
		return "/frontend/register";
	}

	/**
	 * 透過後台進入帶有該會員資料的修改頁面
	 */
	@GetMapping("/backend/edit/{userId}")
	public String showEditPage(@PathVariable("userId") Integer userId, Model model, HttpSession session) {
		User user = userDao.findUserById(userId).get();
		userService.showEditUser(user, session, model);
		return "/backend/edit";
	}

	@PostMapping("/backend/edit/{userId}")
	public String editConfirmed(@ModelAttribute User user, @RequestParam String contact,
			@RequestParam String contactNumber, @RequestParam String password, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		// @RequestParam String password
		userService.editUser(contact, contactNumber, password, session, model, redirectAttributes);
		// , password
		return "redirect:/mvc/scholarship/backend/edit/" + user.getUserId();
	}

	/**
	 * 前台首頁
	 */

	@GetMapping("/frontend")
	public String indexFront(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		addBasicModel(model, session);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "frontend/scholarmain";
	}

	/**
	 * 後台首頁
	 */

	@GetMapping("/backend")
	public String indexBackend(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		addBasicModel(model, session);

		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		// System.out.println(scholarshipDao.findAllscholarship());

		return "backend/backendmain";
	}

	/**
	 * 新增獎學金資料
	 */
	@PostMapping("/backend") // 新增 scholarship
	public String addScholarship(@Valid Scholarship scholarship, BindingResult result, Model model,
			HttpSession session) { // @Valid 驗證, BindingResult 驗證結果

		// 判斷驗證是否通過?
		if (result.hasErrors()) { // 有錯誤發生
			// 自動會將 errors 的資料放在 model 中

			addBasicModel(model, session);
			model.addAttribute("submitBtnName", "建立");
			model.addAttribute("_method", "POST");
			model.addAttribute("scholarship", scholarship); // 給 form 表單用的 (ModelAttribute)

			return "backendmain";
		}

		User sessionData = (User) session.getAttribute("user");
		scholarship.setInstitutionId(sessionData.getInstitutionId());
		scholarship.setUserId(sessionData.getUserId());
		scholarshipDao.addScholarship(scholarship);
		// System.out.println("add User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}

	/**
	 * 複製獎學金資料
	 */
	@GetMapping("/backend/copy/{scholarshipId}")
	public String getUser(@PathVariable("scholarshipId") Integer scholarshipId, Model model, HttpSession session) {

		addBasicModel(model, session);

		Scholarship scholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		model.addAttribute("scholarship", scholarship);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "/backend/backendmain";
	}

	/**
	 * 刪除獎學金資料
	 */
	@DeleteMapping("/backend/delete/{scholarshipId}") // Delete method 刪除
	// @ResponseBody
	public String deleteScholarship(@PathVariable("scholarshipId") Integer scholarshipId) {
		boolean rowcount = scholarshipDao.removeScholarshipById(scholarshipId);
		System.out.println("delete User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}

	/**
	 * 首頁基礎資料 !!!!根據Institution顯示資料
	 */

	private void addBasicModel(Model model, HttpSession session) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarship();
		List<User> users = userDao.findAllUsers();
		User sessionData = (User) session.getAttribute("user");
		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());

		model.addAttribute("sessionInstitution", sessionInstitution.get());

		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello(HttpServletRequest req, HttpServletResponse resp) {
		// logger.info("hello, spirng 來了!!!");
		return "Hello, Spring12345 !!";
	}
}
