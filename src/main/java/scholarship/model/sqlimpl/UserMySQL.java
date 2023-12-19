package scholarship.model.sqlimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import scholarship.bean.User;
import scholarship.model.dao.UserDao;

@Repository
public class UserMySQL implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User(institutionId, userName, password) " +
                "VALUES (:institutionId, :username, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", user.getInstitutionId());
        params.put("userName", user.getUsername());
        params.put("password", user.getPassword());
   
        namedParameterJdbcTemplate.update(sql, params);
    }
/*
 * 
 */
    @Override
    public Boolean updateUsernameById(Integer userId, String password, String newUserName) {
        String sql = "UPDATE User SET userName = :newUserName WHERE userId = :userId AND password = :password";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);
        params.put("newUserName", newUserName);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public Boolean updateUserPasswordById(Integer userId, String oldPassword, String newPassword) {
        String sql = "UPDATE User SET password = :newPassword WHERE userId = :userId AND password = :oldPassword";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM User";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
