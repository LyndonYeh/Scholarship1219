package scholarship.model.sqlimpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import scholarship.bean.User;
import scholarship.model.dao.UserDao;

@Repository
public class UserMySQL implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User (username, password) VALUES (:username, :password)";
        
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

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

    //  namedJdbc 登入用
    
    @Override
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT userId, institutionId, username, password FROM scholarshipv1.user WHERE username = :username";

        Map<String, Object> paramMap = Collections.singletonMap("username", username);

        try {
            User user = namedParameterJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(User.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    
    /* jdbcTemplate 登入用 還是有保留
	@Override
	public Optional<User> findUserByUsername(String username) {
		String sql = "select userId, username, password, level from user where username = ?";
		try {
			User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	*/
    
    
	@Override
	public Optional<User> findUserById(Integer userId) {
		return Optional.empty();
	}
}
