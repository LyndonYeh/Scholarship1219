package scholarship.model.dao;

import java.util.List;
import java.util.Optional;

import scholarship.bean.User;

public interface UserDao {
	
	
	/*
	 * 2. 使用者
	 * google 登入 
+--------+----------+--------------------------+-----------+
| userId | institutionId | userName | password | garbageId | authType | authId
+--------+----------+--------------------------+-----------+
|  101   | 25575888		 | jojo123  | pass123  |    901    | google | 123456
|  102   | 25575889		 | Nono456  | pass456  |    902    | 
|  103   | 25575881 	 | Kiki789  | pass789  |    903    |
+--------+----------+--------------------------+-----------+
使用者-Client:
1. 註冊 (帳號、密碼)
2. 修改帳號
3. 修改密碼
4. 查詢所有使用者
	 */
	
	// 使用者-Client:

		/**
		 * 
		 * 1. 註冊帳號密碼
		 * 1.1 註冊 Google 使用者
		 * 
		 * @param user
		 */
		int addUser(User user);
		int addGoogleUser(User user);

		/**
		 * 2. 修改使用者名稱
		 * 
		 * @param userId 使用者ID Password 獎學金ID 新使用者名稱 NewUserName
		 * @return 是否修改成功
		 */

		Boolean updateUsernameById(Integer userId, String Password, String NewUserName);

		/**
		 * 3. 修改密碼
		 * 
		 * @param 使用者 ID, 舊密碼, 新密碼
		 * @return 是否刪除成功
		 */
		Boolean updateUserPasswordById(Integer userId, String oldPassword, String newPassword);
		Boolean updateUserPasswordById(Integer userId, String newPassword);

		/**
		 * 4. 查詢所有使用者
		 * 
		 * @return 所有使用者列表
		 */
		
	
		List<User> findAllUsers();
		
		/*
		 * 5. 根據使用者名稱查找使用者(登入用-單筆)
		 * 
		 */
		Optional<User> findUserByUsername(String username);
		
		/*
		 * 6. 根據使用者ID查找使用者(單筆)
		 */
		
		Optional<User> findUserById(Integer userId);




}
