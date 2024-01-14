package scholarship.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.Gson;

/**
 * 1. 獎助機構
+--------------------------------------------------------------+
| institutionId     |     institutionName     | contact    | contact number  |  
+--------------------------------------------------------------+
|    25570111    |   行天宮助寒獎學金  | 陳小姐    |  0912345678     |
+--------------------------------------------------------------+
 */


public class Institution {

    private String institutionId;
    private String institutionName;
    private String contactNumber;
    private String contact;
	
	// 設定關聯
	private User user;
	private List<Scholarship> scholarships;
	private List<User> users;

	public Institution() {
	
	}
	public Institution(String institutionName, String institutionId, String contactNumber, String contact) {
		this.institutionName = institutionName;
		this.institutionId = institutionId;
		this.contactNumber = contactNumber;
		this.contact = contact;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getInstitutionName() {
		return institutionName;
	}


	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	
	public String getInstitutionId() {
		return institutionId;
	}


	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}

	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	
}
