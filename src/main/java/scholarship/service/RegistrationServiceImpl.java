package scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scholarship.bean.Institution;
import scholarship.bean.User;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.dao.UserDao;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InstitutionDao institutionDao;

    @Override
    public void register(String username, String password, String institutionName, String institutionId, String contact, String contactNumber) {
        User user = addUser(username, password);
        Institution institution = addInstitution(institutionName, institutionId, contact, contactNumber);

        // Register User and Institution
        userDao.addUser(user);
        institutionDao.addInstitution(institution);
    }

    private User addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    private Institution addInstitution(String institutionName, String institutionId, String contact, String contactNumber) {
        Institution institution = new Institution();
        institution.setInstitutionName(institutionName);
        institution.setInstitutionId(institutionId);
        institution.setContact(contact);
        institution.setContactNumber(contactNumber);
        return institution;
    }
}