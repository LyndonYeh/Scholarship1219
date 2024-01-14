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
import scholarship.model.dao.EntityDao;
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
	@Autowired
	private EntityDao entityDao;

	/**
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

	/**
	 * 驗證登入
	 */
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, Model model) {
		userService.loginUser(username, password, session, model);
		return "redirect:/mvc/scholarship/backend";
	}

	/**
	 * 登出
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();

		return "redirect:/mvc/scholarship/frontend";
	}

	/**
	 * 忘記密碼頁面
	 */
	@RequestMapping("/frontend/forgetpassword")
	public String forget(Model model) {
		return "/frontend/forgetpassword";
	}

	/**
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
			return "redirect:/mvc/scholarship/frontend/forgetpasswordverify";
		} else {
			redirectAttributes.addFlashAttribute("forgetErrorMessage", "信箱錯誤");
			return "redirect:/mvc/scholarship/frontend/forgetpassword";
		}
	}

	@RequestMapping("/frontend/forgetpasswordverify")
	public String verifyCode(Model model) {
		return "/frontend/forgetpasswordverify";
	}

	/**
	 * 忘記密碼驗證驗證碼
	 */
	@PostMapping("/frontend/forgetpasswordverify")
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
		return "redirect:/mvc/scholarship/frontend/forgetpasswordverify";

	}

	/**
	 * 重設密碼頁面
	 */
	@GetMapping("/backend/reset/{strUUID}")
	// 設定 jwt token 時效
	public String showReset(@PathVariable("strUUID") String strUUID, Model model, HttpSession session) {
		String sessionStrUUID = (String) session.getAttribute("strUUID");
		model.addAttribute("strUUID", sessionStrUUID);
		return "/backend/reset";
	}

	/**
	 * 重設密碼
	 */
	@PostMapping("/backend/reset/{strUUID}")
	public String resetPassword(@PathVariable("strUUID") String strUUID,
			@RequestParam("newPassword") String newPassword, Model model, HttpSession session) {
		userService.resetPassword(strUUID, newPassword, session, model);
		return "redirect:/mvc/scholarship/login";
	}

	/**
	 * 註冊信箱頁面
	 */
	@GetMapping("/frontend/register")
	public String checkedRegisteredmailPage(Model model, HttpSession session) {
		return "/frontend/register";
	}

	/**
	 * 比對註冊信箱驗證碼
	 */
	@PostMapping("/frontend/register")
	public String sendRegisterVerificationCode(@RequestParam String username, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) throws MessagingException {
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList());
		if (!usernames.contains(username)) {
			String toEmail = username;
			String verificationCode = RandomNumberGenerator.generateRandomCode();
			session.setAttribute("username", username);
			session.setAttribute("verificationCode", verificationCode);
			try {
				EmailService.sendVerificationCode(toEmail, verificationCode);
				return "redirect:/mvc/scholarship/frontend/registerconfirm";
			} catch (MessagingException e) {
				MessagingException Erro = e;
				System.out.println(e);
				return "error";
			}
		} else {
			redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
		}
		redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
		return "register";
	}

	/**
	 * 註冊頁面
	 */
	@GetMapping("/frontend/registerconfirm")
	public String registerPage() {
		return "/frontend/registerconfirm";
	}

	/**
	 * 使用者註冊
	 */
	@PostMapping("/frontend/registerconfirm")
	public String register(@RequestParam("password") String password,
			@RequestParam("institutionName") String institutionName,
			@RequestParam("institutionId") String institutionId, @RequestParam("contact") String contact,
			@RequestParam("contactNumber") String contactNumber, @RequestParam("username") String username,
			@RequestParam("verificationCode") String verificationCode, Model model, HttpSession session) {
		String sessionVerifiedCode = (String) session.getAttribute("verificationCode");
		System.out.println(sessionVerifiedCode);
		System.out.println(verificationCode);
		if (verificationCode.equals(sessionVerifiedCode)) {
			try {
				userService.registerUser(username, password, institutionName, institutionId, contact, contactNumber);
				return "redirect:/mvc/scholarship/login";
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
	public String indexFront(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		if (session.getAttribute("user") instanceof User) {
			User sessionData = (User) session.getAttribute("user");
			model.addAttribute("username", sessionData.getUsername());
			// 如果有 user 在 session, 存入 attribute 登入狀態的給前台渲染 username\
		} else {
			addBasicModelFrontEnd(model);
			model.addAttribute("submitBtnName", "新增");
			model.addAttribute("_method", "POST");
			return "frontend/scholarmain";
		}
		addBasicModelFrontEnd(model);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "frontend/scholarmain";
	}

	@PostMapping("/frontend")
	public String findScholarship(@Valid Scholarship scholarship, BindingResult result, Model model) {

		int entId = scholarship.getEntityId();
		Integer amount = scholarship.getScholarshipAmount();

		if (entId != 0 && amount != null) {
			addBasicModelEntityAndAmount(model, entId, amount);
			return "frontend/scholarmain";
		} else if (entId != 0) {
			addBasicModelEntity(model, entId);
			return "frontend/scholarmain";
		} else if (amount != null) {
			addBasicModelAmount(model, amount);
			return "frontend/scholarmain";
		} else {
			return "frontend/scholarmain";
		}

	}

	/**
	 * 後台首頁
	 */
	@GetMapping("/backend")
	public String indexBackend(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		addBasicModelBackEnd(model, session);

		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");

		return "backend/backendmain";
	}
	/**
	 * 垃圾回收頁
	 */
	@GetMapping("/backend/garbageCollection")
	public String garbageCollection(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		User sessionData = (User) session.getAttribute("user");
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByInstitutionIdFromGarbageCollection(sessionData.getInstitutionId());

		model.addAttribute("_method", "POST");
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("username", sessionData.getUsername());

		return "backend/garbageCollection";
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

		addBasicModelBackEnd(model, session);
		User sessionData = (User) session.getAttribute("user");
		scholarship.setInstitutionId(sessionData.getInstitutionId());
		scholarship.setUserId(sessionData.getUserId());
		while(scholarship.getContact().isEmpty()) {
			String Contact=institutionDao.findInstitutionByInstitutionId(sessionData.getInstitutionId()).get().getContact();
			scholarship.setContact(Contact);
		}
		while(scholarship.getContactNumber().isEmpty()) {
			String contactNumber =institutionDao.findInstitutionByInstitutionId(scholarship.getInstitutionId()).get().getContactNumber();
			scholarship.setContactNumber(contactNumber);
		}
		scholarshipDao.addScholarship(scholarship);
		// System.out.println("add User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}

	/**
	 * 複製獎學金資料
	 */
	@GetMapping("/backend/copy/{scholarshipId}")
	public String getUser(@PathVariable("scholarshipId") Integer scholarshipId, Model model, HttpSession session) {

		addBasicModelBackEnd(model, session);

		Scholarship scholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		model.addAttribute("scholarship", scholarship);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "/backend/backendmain";
	}
	/**
	 * 修改上下架狀態
	 */
	@GetMapping("/backend/changeLunch/{scholarshipId}")
	public String changeLunch(@PathVariable("scholarshipId") Integer scholarshipId, HttpSession session) {


		Scholarship changeScholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		Boolean isLunch=changeScholarship.getIsUpdated();
		isLunch=!isLunch;
		scholarshipDao.updateLauchStatusbyId(scholarshipId, isLunch);

		
		return "redirect:/mvc/scholarship/backend";
	}

	/**
	 * 刪除獎學金資料
	 */
	@DeleteMapping("/backend/delete/{scholarshipId}") // Delete method 刪除
	// @ResponseBody
	public String deleteScholarship(@PathVariable("scholarshipId") Integer scholarshipId) {
		//先複製到垃圾桶
		Scholarship dScholarship=scholarshipDao.findScholarshipById(scholarshipId).get();
		scholarshipDao.addScholarshipToGarbageCollection(dScholarship);
		
		boolean rowcount = scholarshipDao.removeScholarshipById(scholarshipId);
		System.out.println("delete User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}
	/**
	 * 復原獎學金資料
	 */
	@DeleteMapping("/backend/garbageCollection/{scholarshipId}") // Delete method 刪除
	// @ResponseBody
	public String recoveryScholarship(@PathVariable("scholarshipId") Integer scholarshipId) {
		//先複製回後台
		Scholarship rScholarship=scholarshipDao.findScholarshipByIdFromGarbageCollection(scholarshipId).get();
		scholarshipDao.addScholarship(rScholarship);
		
		boolean rowcount = scholarshipDao.removeScholarshipByIdFromGarbageCollection(scholarshipId);
		System.out.println("delete User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend/garbageCollection"; 
	}

	/**
	 * 
	 * 首頁基礎資料 !!!!根據Institution顯示資料
	 * 
	 * @param model
	 * @param session 首頁基礎資料 !!!! 後台 根據Institution顯示資料
	 * 
	 */
	private void addBasicModelBackEnd(Model model, HttpSession session) {
		User sessionData = (User) session.getAttribute("user");

		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByInstitutionId(sessionData.getInstitutionId());
		List<User> users = userDao.findAllUsers();

		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());

		model.addAttribute("username", sessionData.getUsername());
		model.addAttribute("userId", sessionData.getUserId());
		model.addAttribute("sessionInstitution", sessionInstitution.get());

		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	/**
	 * 首頁基礎資料 前台
	 */

	private void addBasicModelFrontEnd(Model model) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarshipisUpdated();
		List<User> users = userDao.findAllUsers();

		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	private void addBasicModelEntity(Model model, Integer entId) {
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByEntityId(entId);
		model.addAttribute("scholarships", scholarships);

	}

	private void addBasicModelAmount(Model model, Integer amount) {
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByAmount(amount);
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp

	}

	private void addBasicModelEntityAndAmount(Model model, Integer entId, Integer amount) {
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByEntityIdAndAmount(entId, amount);
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp

	}

	/**
	 * data_table 測試 data_table 用註解 搜尋 根據欄位排序 : # 獎助機構 獎學金名稱 獎學金額度 聯絡人 聯絡電話 Search
	 * bar 分頁顯示筆數功能 選擇顯示筆數功能 左下顯示 showing 筆數功能 根據欄位搜尋 ** 檔案輸出
	 */

}
