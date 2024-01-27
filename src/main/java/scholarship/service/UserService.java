package scholarship.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scholarship.bean.Institution;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.UserDao;
import scholarship.util.JwtGenerator;
import scholarship.util.RandomNumberGenerator;

@Service
public class UserService {

	@Autowired
	private final UserDao userDao;

	@Autowired
	private InstitutionDao institutionDao;

	@Autowired
	public UserService(UserDao userDao, InstitutionDao institutionDao) {
		this.userDao = userDao;
		this.institutionDao = institutionDao;
	}

	/*
	 * 登入 user
	 */
	public String loginUser(String username, String password, HttpSession session, Model model) {
		Optional<User> userOpt = userDao.findUserByUsername(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();

			if (BCrypt.checkpw(password, user.getPassword())) {
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(60 * 30);
				return "redirect:/mvc/scholarship/backend";
			} else {
				handleLoginFailure(session, model, "密碼錯誤");
			}
		} else {
			handleLoginFailure(session, model, "無此使用者");
		}
		return "login";
	}

	/*
	 * 處理登入錯誤
	 */
	private void handleLoginFailure(HttpSession session, Model model, String message) {
		session.invalidate();
		model.addAttribute("loginMessage", message);
	}

	/*
	 * 登出
	 */
	public void logoutUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}

	/*
	 * 驗證重設密碼信箱
	 */
	public Boolean validateResetPasswordMail(String username, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList());
		Boolean isMailValidate = usernames.contains(username);

		// 如果驗證成功, 啟動 EmailService 的靜態 mail sender 方法
		if (isMailValidate) {
			session.setAttribute("userEmail", username);
			String toEmail = username;
			String verificationCode = RandomNumberGenerator.generateRandomCode();
			String jwt = JwtGenerator.generateJwt(username);
			session.setAttribute("jwt", jwt);
			session.setAttribute("verificationCode", verificationCode);
			try {
				EmailService.sendVerificationCode(toEmail, verificationCode);
			} catch (MessagingException e) {
				redirectAttributes.addFlashAttribute("forgetErrorMessage", "信箱錯誤");
			}
		} else {
			redirectAttributes.addFlashAttribute("forgetErrorMessage", "信箱錯誤");
		}
		return isMailValidate;

	}

	/*
	 * 驗證重設密碼6位數信箱驗證碼
	 */

	public Boolean validateResetVerifyCode(String verifyCode, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String verificationCode = (String) session.getAttribute("verificationCode");
		Boolean isCodeValidate = verifyCode.equals(verificationCode);
		if (!isCodeValidate) {
			redirectAttributes.addFlashAttribute("forgetErrorMessage", "驗證碼錯誤");
		}
		return isCodeValidate;
	}

	/*
	 * 重設密碼
	 */
	public String resetPassword(String jwt, String newpassword, HttpSession session, Model model) {
		String sessionUsername = (String) session.getAttribute("userEmail");
		Optional<User> userOpt = userDao.findUserByUsername(sessionUsername);
		User user = userOpt.get();
		userDao.updateUserPasswordById(user.getUserId(), BCrypt.hashpw(newpassword, BCrypt.gensalt()));
		session.invalidate();
		return "login";
	}

	/*
	 * 驗證註冊信箱
	 */
	public Boolean validateRegisterMail(String username, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList());
		Boolean isMailValidate = !usernames.contains(username);

		if (isMailValidate) {
			String toEmail = username;
			String verificationCode = RandomNumberGenerator.generateRandomCode();
			session.setAttribute("username", username);
			session.setAttribute("verificationCode", verificationCode);
			try {
				EmailService.sendVerificationCode(toEmail, verificationCode);
			} catch (MessagingException e) {
				redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
			}
		} else {
			redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
		}
		return isMailValidate;
	}

	/*
	 * 註冊使用者
	 */
	@Transactional
	public void registerUser(String username, String password, String institutionName, String institutionId,
			String contact, String contactNumber) throws Exception {

		Institution institution = new Institution();
		institution.setInstitutionId(institutionId);
		institution.setInstitutionName(institutionName);
		institution.setContact(contact);
		institution.setContactNumber(contactNumber);

		institutionDao.addInstitution(institution);

		User user = new User();

		user.setInstitution(institution);
		user.setUsername(username);
		user.setPassword(password);
		userDao.addUser(user);

	}

	/*
	 * 驗證註冊驗證碼
	 */
	public Boolean validateRegisterVerifyCode(String verificationCode, HttpSession session,
			RedirectAttributes redirectAttributes) {

		String sessionVerifiedCode = (String) session.getAttribute("verificationCode");
		Boolean isCodeValidate = verificationCode.equals(sessionVerifiedCode);

		if (!isCodeValidate) {
			redirectAttributes.addFlashAttribute("CodeErrorMessage", "驗證碼錯誤");
		}
		return isCodeValidate;
	}

	/*
	 * 驗證註冊機構, 檢查是否有重複機構Id或機構名稱
	 */
	public Boolean validateRegisterInfo(String institutionName, String institutionId,
			RedirectAttributes redirectAttributes) {
		List<Institution> institutions = institutionDao.findAllInstitutions();
		List<String> institutionNames = institutions.stream().map(Institution::getInstitutionName)
				.collect(Collectors.toList());
		List<String> institutionIds = institutions.stream().map(Institution::getInstitutionId)
				.collect(Collectors.toList());

		Boolean isRegisterValidate = !institutionNames.contains(institutionName)
				&& !institutionIds.contains(institutionId);

		if (institutionNames.contains(institutionName)) {
			redirectAttributes.addFlashAttribute("InstitutionNameErrorMessage", "機構名稱已存在");
		} else if (institutionIds.contains(institutionId)) {
			redirectAttributes.addFlashAttribute("InstitutionIdErrorMessage", "機構統編已存在");
		}

		return isRegisterValidate;
	}

	
	/*
	 * 修改使用者表單頁
	 */
	public void showEditUser(User user, HttpSession session, Model model) {
		User sessionData = user;
		// user 被設定成 controller 的 session 抓取值: User user
		// userDao.findUserById(userId).get();
		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());
		// 透過 user session 找到 session institution

		model.addAttribute("session", sessionData);
		model.addAttribute("sessionInstitution", sessionInstitution);
		// 渲染到 jsp 可用的 attribute
	}

	/*
	 * 修改使用者
	 */
	public String editUser(String contact, String contactNumber, String password, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {
		User sessionData = (User) session.getAttribute("user");
		String sessionInstitutionId = sessionData.getInstitutionId();
		if (BCrypt.checkpw(password, sessionData.getPassword())) {
			institutionDao.updateContactById(sessionInstitutionId, contact);
			institutionDao.updateContactNumberById(sessionInstitutionId, contactNumber);
			model.addAttribute("session", sessionData);
			session.setAttribute("user", sessionData);
		} else {
			redirectAttributes.addFlashAttribute("editErrorMessage", "密碼錯誤");
		}
		return "/backend/edit";

	}

}
