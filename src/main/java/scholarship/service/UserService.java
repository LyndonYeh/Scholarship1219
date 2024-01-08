package scholarship.service;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	private EmailService emailService;

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
		session.invalidate();
		model.addAttribute("loginMessage", message);
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
	public void sendRegisterVerificationCode(String username, HttpSession session) {
		
		session.setAttribute("userEmail", username);
		

		String verificationCode = RandomNumberGenerator.generateRandomCode();
		String toEmail = (String) session.getAttribute("userEmail");
		session.setAttribute("verificationCode",verificationCode);

		try {
			EmailService.sendVerificationCode(toEmail, verificationCode);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}
	}
	
	public void sendForgetVerificationCode(String username, HttpSession session) {
		User user = null;
		String confirmedUsername = user.getUsername();
		session.setAttribute("userEmail", username);

		String verificationCode = RandomNumberGenerator.generateRandomCode();
		String toEmail = (String) session.getAttribute("userEmail");

		try {
			EmailService.sendVerificationCode(toEmail, verificationCode);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}
	}

	public void showEditUser(User user,  HttpSession session, Model model) {
		
		
		User sessionData = user;
		Optional<Institution> sessionInstitution = institutionDao
				.findInstitutionByInstitutionId(sessionData.getInstitutionId());


		model.addAttribute("user", user);
		model.addAttribute("session", sessionData);
		model.addAttribute("sessionInstitution", sessionInstitution);
	}

	public void editUser(Integer userId, User user, String contact, String contactNumber, HttpSession session, Model model) {
        User sessionData = (User) session.getAttribute("user");

        if (sessionData != null) {
            String sessionInstitutionId = sessionData.getInstitutionId();

            institutionDao.updateContactById(sessionInstitutionId, contact);
            institutionDao.updateContactNumberById(sessionInstitutionId, contactNumber);

            model.addAttribute("user", user);
            model.addAttribute("session", sessionData);
        } else {
            model.addAttribute("error", "Session data not found");
        }
    }

}

/*
 * @PostMapping("/login") public String login(@RequestParam("username") String
 * username, @RequestParam("password") String password, HttpSession session,
 * Model model) {
 * 
 * // 根據 username 查找 user 物件 Optional<User> userOpt =
 * userMySQL.findUserByUsername(username);
 * 
 * if (userOpt.isPresent()) { User user = userOpt.get(); // String
 * hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()); // 從
 * userDao 帶入 // 比對 password if (BCrypt.checkpw(password, user.getPassword())) {
 * 
 * session.setAttribute("user", user); // 將 user 物件放入到 session 變數中 return
 * "redirect:/mvc/scholarship/backend"; // OK, 導向後台首頁 } else {
 * session.invalidate(); // session 過期失效 model.addAttribute("loginMessage",
 * "密碼錯誤"); return "login"; } } else { session.invalidate(); // session 過期失效
 * model.addAttribute("loginMessage", "無此使用者"); return "login"; } }
 */
