package scholarship.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

	@Autowired
	private InstitutionDao institutionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ScholarshipDao scholarshipDao;
	
	// private Logger logger = LoggerFactory.getLogger(getClass());

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
		s.append("帳號: dave.wenyu@gmail.com / 密碼 : password1 '")
		.append("帳號: lyndonyeh@gmail.com / 密碼: password2 '")
		.append("帳號: alicelu@gmail.com /碼密碼: password3 ");
		return s.toString(); 
	}
	
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

	
	@GetMapping(value = { "/register", "/register/" })
	public String registerPage() {
		return "/frontend/register";
	}


	@PostMapping("/register")
	public String register(
	        @RequestParam("username") String username,
	        @RequestParam("password") String password,
	        @RequestParam("institutionName") String institutionName,
	        @RequestParam("institutionId") String institutionId,
	        @RequestParam("contact") String contact,
	        @RequestParam("contactNumber") String contactNumber,
	        Model model) {
		
		Random random = new Random();

        // Generate a random integer between 1 and 100 (inclusive)
        int randomNumber = random.nextInt(1000000) ;

	    try {
	        User user = new User();
	        user.setUsername(username);
	        user.setPassword(password);

	        Institution institution = new Institution();
	        institution.setInstitutionName(institutionName);
	        institution.setInstitutionId(institutionId);
	        institution.setContact(contact);
	        institution.setContactNumber(contactNumber);

	        // Save User and Institution to the database
	        userDao.addUser(user);
	        institutionDao.addInstitution(institution);

	        // Redirect to a success page
	        return "redirect:/successPage";
	    } catch (Exception e) {
	        // Handle the exception, log it, and redirect to an error page
	        e.printStackTrace();
	        model.addAttribute("error", "Error occurred during registration.");
	        return "errorPage";
	    }
	}

	
	/*
	@GetMapping("/{username}/edit")
	public String editPage() {
		return "/frontend/register";
	}
	*/
	



	/* 
	 * 登入後重新導向的 後台測試頁
	 * 若後台 controller 串接好此路徑可刪除
	 */
	@GetMapping("/backendtest")
	@ResponseBody
	public String backendtest(HttpServletRequest req, HttpServletResponse resp) {
		return "backendTest 後台測試頁 登入成功 !";

	}

	/*
	 * 首頁基礎資料
	 */
	
	private void addBasicModel(Model model) {
		List<Institution> instiutions = institutionDao.findAllInstitutions();
		List<Scholarship> scholarships = scholarshipDao.findAllscholarship();
		List<User> users = userDao.findAllUsers();
		
		model.addAttribute("institutions", instiutions); // 將機構資料傳給 jsp
		model.addAttribute("scholarships", scholarships); // 將獎學金資料傳給 jsp
		model.addAttribute("users", users); // 取得目前最新 users 資料
	}



	
	/*
	 * 透過後台進入帶有該會員資料的修改頁面
	 */
	@GetMapping("/backend/edit")
	public String edit(@ModelAttribute User user, Model model) {
		
		return "backend/edit";
	}

	
	@GetMapping("/frontend")
	public String indexFront(@ModelAttribute Scholarship scholarship, Model model) {
		addBasicModel(model);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "frontend/scholarmain";
	}
	
	
	@GetMapping("/backend")
	public String indexBackend(@ModelAttribute Scholarship scholarship, Model model) {
		addBasicModel(model);
		model.addAttribute("submitBtnName", "新增");
		model.addAttribute("_method", "POST");
		return "backend/backendmain";
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
