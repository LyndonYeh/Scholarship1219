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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import scholarship.bean.Institution;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.UserDao;
import scholarship.model.sqlimpl.UserMySQL;
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

	public String loginUser(String username, String password, HttpSession session, Model model) {
		Optional<User> userOpt = userDao.findUserByUsername(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();

			if (BCrypt.checkpw(password, user.getPassword())) {
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(60*30);
				return "redirect:/mvc/scholarship/backend";
			} else {
				handleLoginFailure(session, model, "密碼錯誤");
			}
		} else {
			handleLoginFailure(session, model, "無此使用者");
		}
		return "login";
	}
	
	public String loginGoogleUser(String username, String password, HttpSession session, Model model) {
		Optional<User> userOpt = userDao.findUserByUsername(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();

			if (BCrypt.checkpw(password, user.getPassword())) {
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(60*30);
				return "redirect:/mvc/scholarship/backend";
			} else {
				handleLoginFailure(session, model, "密碼錯誤");
			}
		} else {
			handleLoginFailure(session, model, "無此使用者");
		}
		return "login";
	}

	private void handleLoginFailure(HttpSession session, Model model, String message) {
		// 設定共用 session
		session.invalidate();
		model.addAttribute("loginMessage", message);
	}

	public String resetPassword(String strUUID, String newpassword, HttpSession session, Model model) {
		String sessionUsername = (String) session.getAttribute("userEmail");
		Optional<User> userOpt = userDao.findUserByUsername(sessionUsername);
		User user = userOpt.get();
		userDao.updateUserPasswordById(user.getUserId(), BCrypt.hashpw(newpassword, BCrypt.gensalt()));
		return "login";
	}

	@Transactional
	public void registerUser(String username, String password, String institutionName, String institutionId,
			String contact, String contactNumber, HttpSession session) throws Exception {

		Optional<User> existingUserOpt = userDao.findUserByUsername(username);
		if (existingUserOpt.isPresent()) {
			throw new Exception("電子郵件已被註冊");
		}

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

	// 先拿到 jsp username 比對 db username, 設定 user
	public void sendRegisterVerificationCode(String username, HttpSession session, RedirectAttributes redirectAttributes) {
	
		List<User> users = userDao.findAllUsers();
		List<String> usernames = users.stream().map(User::getUsername) // 把 username 抽出來
				.collect(Collectors.toList()); 
		
		if (!usernames.contains(username)) {
			session.setAttribute("userEmail", username);
			String storedVerificationCode = (String) session.getAttribute("verificationCode");
			String verificationCode = RandomNumberGenerator.generateRandomCode();
			String toEmail = (String) session.getAttribute("userEmail");
			session.setAttribute("verificationCode", verificationCode);			
			try {
				EmailService.sendVerificationCode(toEmail, verificationCode);
			} catch (MessagingException e) {
				redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
			}
			redirectAttributes.addFlashAttribute("registerErrorMessage", "信箱錯誤");
		}
	}
	
//	public Boolean  verifyRegisterVerificationCode(String verificationCode, HttpSession session, RedirectAttributes redirectAttributes) {
//
//			String to = (String) session.getAttribute("verificationCode");;
//			
//			
//			try {
//				EmailService.sendVerificationCode(toEmail, verificationCode);
//			} catch (MessagingException e) {
//				redirectAttributes.addFlashAttribute("registerVerifyMessage", "驗證碼錯誤");
//				// Handle the exception as needed
//			}
//			redirectAttributes.addFlashAttribute("registerVerifyMessage", "驗證碼錯誤");
//	}

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

	public String editUser(String contact, String contactNumber, String password, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {
//		String password
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

	private void handleEditFailure(HttpSession session, Model model, String message) {
		model.addAttribute("editErroMessage", message);
	}
}
