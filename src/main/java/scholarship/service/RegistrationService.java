package scholarship.service;

import scholarship.bean.Institution;
import scholarship.bean.User;

public interface RegistrationService {
    void register(String username, String password, String institutionName, String institutionId, String contact, String contactNumber);
}
