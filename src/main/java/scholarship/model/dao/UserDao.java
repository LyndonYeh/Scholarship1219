package scholarship.model.dao;

import java.util.List;
import java.util.Optional;

import scholarship.bean.User;

public interface UserDao {
	
		/**
		 * 1. 註冊帳號密碼
		 * @param user
		 */
		int addUser(User user);



		/**
		 * 2. 修改密碼
		 * @param 使用者 ID, 新密碼
		 * @return 是否刪除成功
		 */
		
		Boolean updateUserPasswordById(Integer userId, String newPassword);

		/**
		 * 3. 查詢所有使用者
		 * @return 所有使用者列表
		 */
		List<User> findAllUsers();
		
		/*
		 * 4. 根據使用者名稱查找使用者(登入用-單筆)
		 * 
		 */
		Optional<User> findUserByUsername(String username);
		
		/*
		 * 5. 根據使用者ID查找使用者(單筆)
		 */
		
		Optional<User> findUserById(Integer userId);


}
