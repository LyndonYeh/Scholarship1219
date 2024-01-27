package scholarship.controller;

import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.UserDao;
import scholarship.service.UserService;

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

	/**
	 * 登入頁
	 */
	@GetMapping(value = { "/login", "/", "/login/" })
	public String loginPage() {
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
		userService.logoutUser(request);
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

		Boolean isResetMailValidate = userService.validateResetPasswordMail(username, model, session,
				redirectAttributes);

		if (isResetMailValidate) {
			return "redirect:/mvc/scholarship/frontend/forgetpasswordverify";
		} else {
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
		Boolean isResetCodeValidate = userService.validateResetVerifyCode(verifyCode, model, session,
				redirectAttributes);

		if (isResetCodeValidate) {
			return "redirect:/mvc/scholarship/backend/reset/" + session.getAttribute("jwt");
		} else {
			return "redirect:/mvc/scholarship/frontend/forgetpasswordverify";
		}
	}

	/**
	 * 重設密碼頁面
	 */
	@GetMapping("/backend/reset/{jwt}")
	public String showReset(@PathVariable("jwt") String jwt, Model model, HttpSession session) {
		if (session.getAttribute("jwt") != null) {
			String sessionJwt = (String) session.getAttribute("jwt");
			model.addAttribute("jwt", sessionJwt);
			return "/backend/reset";
		} else {
			return "redirect:/mvc/scholarship/login";
		}
	}

	/**
	 * 重設密碼
	 */
	@PostMapping("/backend/reset/{jwt}")
	public String resetPassword(@PathVariable("jwt") String jwt, @RequestParam("newPassword") String newPassword,
			Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			userService.resetPassword(jwt, newPassword, session, model);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("resetErrorMessage", "連結已失效");
			return "redirect:/mvc/scholarship/frontend";
		}
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
	 * 驗證註冊信箱
	 */
	@PostMapping("/frontend/register")
	public String sendRegisterVerificationCode(@RequestParam String username, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) throws MessagingException {

		Boolean isRegisterMailValidate = userService.validateRegisterMail(username, redirectAttributes, session,
				redirectAttributes);

		if (isRegisterMailValidate) {
			return "redirect:/mvc/scholarship/frontend/registerconfirm";
		} else {
			return "redirect:/mvc/scholarship/frontend/register";
		}
	}

	/**
	 * 顯示註冊頁面
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
			@RequestParam("verificationCode") String verificationCode, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {

		// 註冊信箱驗證碼驗證
		Boolean isRegisterCodeValidate = userService.validateRegisterVerifyCode(verificationCode, session,
				redirectAttributes);
		// 註冊機構驗證
		Boolean isRegisterInfoValidate = userService.validateRegisterInfo(institutionName, institutionId,
				redirectAttributes);

		if (isRegisterCodeValidate && isRegisterInfoValidate) {
			try {
				// 若通過驗證, 註冊使用者
				userService.registerUser(username, password, institutionName, institutionId, contact, contactNumber);
				return "redirect:/mvc/scholarship/login";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/mvc/scholarship/frontend/registerconfirm";

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
		userService.editUser(contact, contactNumber, password, session, model, redirectAttributes);
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

			// 如果有 user 在 session, 存入 attribute 登入狀態的給前台渲染 username
		} else if (session.getAttribute("username") != null) {
			// 如果 github 的 username 有在 session , 存入 attribute 登入狀態的給前台渲染 username
			String sessionGithubUser = (String) session.getAttribute("username");
			model.addAttribute("username", sessionGithubUser);
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
	
	/**
	 * 篩選獎學金資料
	 */

	@PostMapping("/frontend")
	public String findScholarship(@Valid Scholarship scholarship, BindingResult result, Model model) {

		int entId = scholarship.getEntityId();
		Integer amount = scholarship.getScholarshipAmount();

		if (entId != 0 && amount != null) {
			addBasicModelEntityAndAmount(model, entId, amount);
			return "/frontend/scholarmain";
		} else if (entId != 0) {
			addBasicModelEntity(model, entId);
			return "/frontend/scholarmain";
		} else if (amount != null) {
			addBasicModelAmount(model, amount);
			return "/frontend/scholarmain";
		} else {
			return "redirect:/mvc/scholarship/frontend/";
		}

	}

	/**
	 * 後台首頁
	 */
	@GetMapping("/backend")
	public String indexBackend(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		addBasicModelBackEnd(model, session);
		return "backend/backendmain";
	}

	/**
	 * 資源回收頁
	 */
	@GetMapping("/backend/garbageCollection")
	public String garbageCollection(@ModelAttribute Scholarship scholarship, Model model, HttpSession session) {
		User sessionData = (User) session.getAttribute("user");
		List<Scholarship> scholarships = scholarshipDao
				.findScholarshipByInstitutionIdFromGarbageCollection(sessionData.getInstitutionId());

		model.addAttribute("_method", "POST");
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("username", sessionData.getUsername());
		model.addAttribute("userId", sessionData.getUserId());

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

			model.addAttribute("_method", "POST");
			model.addAttribute("scholarship", scholarship); // 給 form 表單用的 (ModelAttribute)

			return "backendmain";
		}

		addBasicModelBackEnd(model, session);
		User sessionData = (User) session.getAttribute("user");
		scholarship.setInstitutionId(sessionData.getInstitutionId());
		scholarship.setUserId(sessionData.getUserId());
		while (scholarship.getContact().isEmpty()) {
			String Contact = institutionDao.findInstitutionByInstitutionId(sessionData.getInstitutionId()).get()
					.getContact();
			scholarship.setContact(Contact);
		}
		while (scholarship.getContactNumber().isEmpty()) {
			String contactNumber = institutionDao.findInstitutionByInstitutionId(scholarship.getInstitutionId()).get()
					.getContactNumber();
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
		
		User sessionData = (User) session.getAttribute("user");
		
		Institution  sessionInstitution = institutionDao.findInstitutionByInstitutionId(sessionData.getInstitutionId()).get();
		
		String sessionInstitutionId =sessionInstitution.getInstitutionId();
		
		Scholarship passScholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		
		if(sessionInstitutionId.equals(passScholarship.getInstitutionId())){
			Scholarship scholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
			model.addAttribute("scholarship", scholarship);
			return "/backend/backendmain";
		};
		return "redirect:/mvc/scholarship/backend";	
	}

	/**
	 * 修改上下架狀態
	 */
	@GetMapping("/backend/changeLunch/{scholarshipId}")
	public String changeLunch(@PathVariable("scholarshipId") Integer scholarshipId, HttpSession session) {

		Scholarship changeScholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		Boolean isLunch = changeScholarship.getIsUpdated();
		isLunch = !isLunch;
		scholarshipDao.updateLauchStatusbyId(scholarshipId, isLunch);

		return "redirect:/mvc/scholarship/backend";
	}

	/**
	 * 刪除獎學金資料
	 */
	@DeleteMapping("/backend/delete/{scholarshipId}") // Delete method 刪除
	// @ResponseBody
	public String deleteScholarship(@PathVariable("scholarshipId") Integer scholarshipId) {
		// 先複製到垃圾桶
		Scholarship dScholarship = scholarshipDao.findScholarshipById(scholarshipId).get();
		scholarshipDao.addScholarshipToGarbageCollection(dScholarship);

		// 刪除獎學金紀錄
		boolean rowcount = scholarshipDao.removeScholarshipById(scholarshipId);
		System.out.println("delete User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend"; // 重導到 user 首頁
	}

	/**
	 * 復原獎學金資料
	 */
	@DeleteMapping("/backend/garbageCollection/{scholarshipId}") // Delete method 刪除
	public String recoveryScholarship(@PathVariable("scholarshipId") Integer scholarshipId) {
		// 先複製回後台
		Scholarship rScholarship = scholarshipDao.findScholarshipByIdFromGarbageCollection(scholarshipId).get();
		scholarshipDao.addScholarship(rScholarship);

		boolean rowcount = scholarshipDao.removeScholarshipByIdFromGarbageCollection(scholarshipId);
		System.out.println("delete User rowcount = " + rowcount);
		return "redirect:/mvc/scholarship/backend/garbageCollection";
	}

	/**
	 * 後台首頁基礎資料
	 * 
	 * @param model
	 * @param session 後台根據Institution顯示資料
	 */
	private void addBasicModelBackEnd(Model model, HttpSession session) {

		User sessionData = (User) session.getAttribute("user");

		List<Institution> institutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findScholarshipByInstitutionId(sessionData.getInstitutionId());
		List<User> users = userDao.findAllUsers();

		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());

		model.addAttribute("username", sessionData.getUsername());
		model.addAttribute("userId", sessionData.getUserId());
		model.addAttribute("sessionInstitution", sessionInstitution.get());


		model.addAttribute("institutions", institutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}

	/**
	 * 前台首頁基礎資料
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

}