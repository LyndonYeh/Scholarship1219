package scholarship.service;

import org.apache.catalina.startup.UserDatabase;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scholarship.bean.Institution;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.UserDao;
import scholarship.model.sqlimpl.InstitutionMySQL;
import scholarship.model.sqlimpl.UserMySQL;
import scholarship.util.RandomNumberGenerator;

@Service
public class RegistrationService  {
	@Autowired
	private InstitutionDao institutionDao;
	
	@Autowired
	private UserDao userDao;
	
	private EmailService emailService;

    @Autowired
    public RegistrationService(InstitutionMySQL institutionMySQL, UserMySQL userMySQL) {
		this.institutionDao = institutionDao;
		this.userDao = userDao;
	}
    // 把兩個 add 包裝成一個 註冊 service 方法
    @Transactional
    public void register(String username, String password, String institutionName, String institutionId, String contact, String contactNumber, HttpSession session) throws Exception {
    	
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
	


}