package scholarship.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

/**
 * 3. 獎學金上傳紀錄
 * +-------------------------- +------------+----------------------+----------------------------------+------------------------------------+-----------------+-----------------------------------------+-------------------------+-------------------------+-----------------------+---------------------------+----------------------+-----------------+------------------------------+
 * | scholoarshipId | userId | institutionId |  scholarshipName  | scholarshipAmount   |  entityId  |          updatedTime         |    StartDate     |     EndDate      |   isExpired    |        webUrl        |   isUpdated  |   contact  | contactNumber   |
 * +-------------------------- +------------+----------------------+----------------------------------+------------------------------------+-----------------+-----------------------------------------+-------------------------+-------------------------+-----------------------+---------------------------+----------------------+-----------------+------------------------------+
 * |          101          |      1     | 25570111  | 行天宮助寒獎學金 |            50000             |       1       |    2023/12/13 15:00    | 2023/05/03  |  2023/05/04 |          0          |  example.com   |         0          |   李先生   |   0988777666   |
 *+-------------------------- +------------+----------------------+----------------------------------+------------------------------------+-----------------+-----------------------------------------+-------------------------+-------------------------+-----------------------+---------------------------+----------------------+-----------------+-------------------------------+
 */

public class Scholarship {

	private Integer scholarshipId;
	private Integer userId;
	private String institutionId;
	private String scholarshipName;
	private Integer scholarshipAmount;
	private String updatedTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;
	private Boolean isExpired;
	private String webUrl;
	private Boolean isUpdated;
	private Integer entityId;
    private String contactNumber;
    private String contact;
	
  //設定關聯
	private Institution institution;
	private Entity entity;
	

	public Scholarship() {

	}

	public Scholarship(Integer scholarshipId, Integer userId, String scholarshipName, Integer entityId,
			Integer scholarshipAmount, String updatedTime, String startDate, Date endDate, String webUrl) {
		this.scholarshipId = scholarshipId;
		this.userId = userId;
		this.scholarshipName = scholarshipName;
		this.entityId = entityId;
		this.scholarshipAmount = scholarshipAmount;
		this.updatedTime = updatedTime; 
		this.startDate = startDate;
		this.endDate = endDate;
		this.webUrl = webUrl;
	}
	
	
	public Scholarship(Integer scholarshipId, Integer userId, String institutionId, String scholarshipName,
			Integer scholarshipAmount, String updatedTime, String startDate, Date endDate, Boolean isExpired,
			String webUrl, Boolean isUpdated, Integer entityId, String contactNumber, String contact,
			Institution institution, Entity entity) {
		super();
		this.scholarshipId = scholarshipId;
		this.userId = userId;
		this.institutionId = institutionId;
		this.scholarshipName = scholarshipName;
		this.scholarshipAmount = scholarshipAmount;
		this.updatedTime = updatedTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isExpired = isExpired;
		this.webUrl = webUrl;
		this.isUpdated = isUpdated;
		this.entityId = entityId;
		this.contactNumber = contactNumber;
		this.contact = contact;
		this.institution = institution;
		this.entity = entity;
	}


	public Integer getScholarshipId() {
		return scholarshipId;
	}
	
	public void setScholarshipId(Integer scholarshipId) {
		this.scholarshipId = scholarshipId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitutionId() {
		return institutionId;
	}
	
	public String getScholarshipName() {
		return scholarshipName;
	}

	public void setScholarshipName(String scholarshipName) {
		this.scholarshipName = scholarshipName;
	}

	public Integer getScholarshipAmount() {
		return scholarshipAmount;
	}
	
	public void setScholarshipAmount(Integer scholarshipAmount) {
		this.scholarshipAmount = scholarshipAmount;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}
	
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	// 獲取特定格式時間
	public String getStringEndDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	        return format.format(this.endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}
	
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public String getWebUrl() {
		return webUrl;
	}
	
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
	public Boolean getIsExpired() {
		return isExpired;
	}
	
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	public Institution getInstitution() {
		return institution;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Integer getEntityId() {
		return entityId;
	}
	
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	public Entity getEntity() {
		return entity;
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

	public String toString() {
		return new Gson().toJson(this);
	}
	
}
