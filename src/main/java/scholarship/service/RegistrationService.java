package scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scholarship.bean.Institution;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.UserDao;

@Service
public class RegistrationService  {
	
	private final InstitutionDao institutionDao;
	private final UserDao userDao;

    @Autowired
    public RegistrationService(InstitutionDao institutionDao, UserDao userDao) {
		this.institutionDao = institutionDao;
		this.userDao = userDao;
	}
    // 把兩個 add 包裝成一個 註冊 service 方法
    @Transactional
    public void register(String username, String password, String institutionName, String contact, String contactNumber) {
    	
    	Institution institution = new Institution();
    	institution.setInstitutionName(institutionName);
    	institution.setContact(contact);
    	institution.setContactNumber(contactNumber);
    	
    	institutionDao.addInstitution(institution);
    	
        User user = new User();
        
        // 互相設定關聯 TBC
        user.setInstitution(institution);
       // institution.setUser(user);
 
        user.setUsername(username);
        user.setPassword(password);
		
        userDao.addUser(user);
    	
    }
	


}