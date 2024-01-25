package scholarship.model.dao;

import java.util.List;
import java.util.Optional;

import scholarship.bean.User;

public interface UserDao {
	
		/**
		 * 1. 註冊帳號密碼 (新增使用者)
		 * @param user
		 */
		int addUser(User user);



		/**
		 * 2. 根據userId修改密碼
		 * @param 使用者 ID, 新密碼
		 * @return 是否刪除成功
		 */

		Boolean updateUserPasswordById(Integer userId, String newPassword);

		/**
		 * 3. 查詢所有使用者
		 * @return 所有使用者列表
		 */
		List<User> findAllUsers();
		
		/**
		 * 4. 根據username修改密碼
		 * @param username
		 * @return 是否查詢成功
		 */
		Optional<User> findUserByUsername(String username);
		
		/**
		 * 5. 根據userId查找使用者
		 * @param userId
		 * @return Optional user
		 */
		
		Optional<User> findUserById(Integer userId);


}
