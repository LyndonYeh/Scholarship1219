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
import org.springframework.http.MediaType;
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
import com.google.gson.Gson;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
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


	/*
	 * 登入頁
	 */
	@GetMapping(value = { "/login", "/", "/login/" })
	public String loginPage() {
		return "/login";
	}
	
	
	@GetMapping("/loginGoogle")
	public String loginGooglePage() {
		
		
		return "/login";
	}
	
	
	/*
	 * 驗證登入
	 */
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, Model model) {
		userService.loginUser(username, password, session, model);
		return "redirect:/mvc/scholarship/backend";
	}
	
	
	/*
	 * 登出
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();

		return "redirect:/mvc/scholarship/frontend";
	}

	
	/*
	 * 忘記密碼頁面
	 */
	@RequestMapping("/frontend/forgetpassword")
	public String forget(Model model) {
		return "/frontend/forgetpassword";
	}

	
	/*
	 * 忘記密碼傳送驗證碼頁面
	 */
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

	
	/*
	 * 忘記密碼驗證驗證碼
	 */
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

	
	/*
	 * 重設密碼頁面
	 */
	@GetMapping("/backend/reset/{strUUID}")
	// 設定 jwt token 時效
	public String showReset(@PathVariable("strUUID") String strUUID, Model model, HttpSession session) {
		String sessionStrUUID = (String) session.getAttribute("strUUID");
		model.addAttribute("strUUID", sessionStrUUID);
		return "/backend/reset";
	}

	
	/*
	 * 重設密碼
	 */
	@PostMapping("/backend/reset/{strUUID}")
	public String resetPassword(@PathVariable("strUUID") String strUUID,
			@RequestParam("newPassword") String newPassword, Model model, HttpSession session) {
		userService.resetPassword(strUUID, newPassword, session, model);
		return "redirect:/mvc/scholarship/login";
	}
	
	/*
	 * 註冊信箱頁面
	 */
	@GetMapping("/frontend/mailconfirm")
	public String mailConfirm(Model model, HttpSession session) {
		return "/frontend/mailconfirm";
	}
	
	/*
	 * 比對註冊信箱驗證碼
	 */
	@PostMapping("/frontend/sendRegisterVerificationCode")
	public String sendRegisterVerificationCode(@RequestParam String username, HttpSession session)
			throws MessagingException {
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList());
if(!usernames.contains(username)) {
	String toEmail = username;
	String verificationCode = RandomNumberGenerator.generateRandomCode();
	session.setAttribute("username", username);
	session.setAttribute("verificationCode", verificationCode);
	try {
		EmailService.sendVerificationCode(toEmail, verificationCode);
	} catch (MessagingException e) {
		return "error";
	} 
}else {
		}
		return "/frontend/register";
	}

	
	/*
	 * 註冊頁面
	 */
	@GetMapping(value = { "/register", "/register/" })
	public String registerPage() {
		return "/frontend/register";
	}

	
	/*
	 * 使用者註冊
	 */
	@PostMapping("/register")
	public String register(@RequestParam("password") String password,
			@RequestParam("institutionName") String institutionName,
			@RequestParam("institutionId") String institutionId, @RequestParam("contact") String contact,
			@RequestParam("contactNumber") String contactNumber,
			@RequestParam("verificationCode") String verificationCode, Model model, HttpSession session) {
		String sessionVerifiedCode = (String) session.getAttribute("verificationCode");
		String username = (String) session.getAttribute("username");
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

	
	

	
	/**
	 * 透過後台進入帶有該會員資料的修改頁面
	 */
	@GetMapping("/backend/edit/{userId}")
	public String showEditPage(@PathVariable("userId") Integer userId, Model model, HttpSession session) {
		User user = userDao.findUserById(userId).get();
		userService.showEditUser(user, session, model);
		return "/backend/edit";
	}
	
	
	/**
	 * 修改會員資料
	 */
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
	public String indexFront(@ModelAttribute Scholarship scholarship, Model model) {
		addBasicModel2(model);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "frontend/scholarmain";
	}

	
	/*
	 * 前台查找已上架獎學金 根據 身分別 根據 金額下限
	 */

	@GetMapping(value ={"/frontend/","/frontend/{entity}"})
	public String findScholarship(
	        @PathVariable(required = false) String entity,
	        @Valid Scholarship scholarship,
	        BindingResult result,
	        Model model) {

	    model.addAttribute("entId", entity);
	    Integer entId=Integer.valueOf(entity);
	    //model.addAttribute("amount", amount);

	    // Retrieve scholarships based on the conditions
	    List<Scholarship> scholarships;
	    if (entId!=0) {
	    	scholarships = scholarshipDao.findScholarshipByEntityId(entId);
	    	addBasicModel3(model,entId);
	    	return "frontend/scholarmain";
	    }scholarships = scholarshipDao.findAllscholarship();
	    	addBasicModel2(model);
	     	return "frontend/scholarmain";
	}


	@PostMapping("/frontend")
	public String findScholarship( @Valid Scholarship scholarship,BindingResult result, Model model) {

		int entId = scholarship.getEntityId();
		//Integer amount = scholarship.getScholarshipAmount();
		model.addAttribute("entId",entId);
		//model.addAttribute("amount", amount);

		if (entId!=0) {
			List<Scholarship> scholarships = scholarshipDao.findScholarshipByEntityId(3);
			model.addAttribute("scholarships", scholarships);
			return "frontend/scholarmain";
		} 
		addBasicModel2(model);
			return "frontend/scholarmain";
		}

/*
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

			model.addAttribute("submitBtnName", "建立");
			model.addAttribute("_method", "POST");
			model.addAttribute("scholarship", scholarship); // 給 form 表單用的 (ModelAttribute)

			return "backendmain";
		}

		addBasicModel(model, session);
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
	@GetMapping("/backend/changeLunch/{scholarshipId}")
	public String changeLunch(@PathVariable("scholarshipId") Integer scholarshipId, Model model, HttpSession session) {
		
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
	 * @param model
	 * @param session
	 * 首頁基礎資料 !!!!  後台 根據Institution顯示資料
<<<<<<< HEAD

=======
>>>>>>> b274efe5159234c295907b72519dd44b4b376108
	 */
	private void addBasicModel(Model model, HttpSession session) {
		User sessionData = (User) session.getAttribute("user");
		
		
		
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByInstitutionId(sessionData.getInstitutionId());
		List<User> users = userDao.findAllUsers();
		
		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());

		model.addAttribute("sessionInstitution", sessionInstitution.get());

		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}
	
	
	/**
	 * 首頁基礎資料 前台
	 */
	private void addBasicModel2(Model model) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarshipisUpdated();
		List<User> users = userDao.findAllUsers();
		
		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	
	private void addBasicModel3(Model model,Integer entId) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByEntityId(entId);
		List<User> users = userDao.findAllUsers();
		
		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}
	
	
	private void addBasicModel4(Model model) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarshipisUpdated();
		List<User> users = userDao.findAllUsers();
		
		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	
	/**data_table 測試 data_table 用註解
	 * 搜尋
	 * 根據欄位排序 : #	獎助機構	獎學金名稱	獎學金額度	聯絡人	聯絡電話
	 * Search bar
	 * 分頁顯示筆數功能
	 * 選擇顯示筆數功能
	 * 左下顯示 showing 筆數功能
	 * 根據欄位搜尋 **
	 * 檔案輸出
	 */
	
}
