package scholarship.bean;
import com.google.gson.Gson;

/*
	2. 使用者
	+-------------+-----------------------+-------------------------------------+------------------+
	| userId | institutionId   |          userName         | password | 
	+------------+------------------------+-------------------------------------+------------------+
	|    1 	  | 25575888	 | jojo123@gmail.com  | pass123  |
	|    2      | 25575889	 | jojo124@gmail.com  | pass456  |
	|    3      | 25575881 	 | jojo125@gmail.com  | pass789  |
	+------------+------------------------+-------------------------------------+------------------+
*/
	
public class User {
	private Integer userId; 
	private String institutionId; 
	private String username; 
	private String password; 
	
	//設定關聯
	private Institution institution;
	
	
	public User(Integer userId, String username, String password, String institutionId) {
		this.userId = userId;
		this.username = username;
		this.institutionId = institutionId;
		this.password = password;
	}
	
	
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	
	public User() {
		
	}

	public Institution getInstitution() {
		return institution;
	}

	
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}


	public Integer getUserId() {
		return userId;
	}
	
	
	public String getInstitutionId() {
		return institutionId;
	}

	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String toString() {
		return new Gson().toJson(this);
	}
	
	
}
