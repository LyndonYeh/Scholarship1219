package scholarship.bean;

import java.io.File;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

//import scholarship.model.ScholarshipInMemory;
import scholarship.model.dao.ScholarshipDao;

/**
3. 獎學金上傳紀錄
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| scholoarshipId   |    userId       |    institutionId      |   scholarshipName       |  scholarshipAmount    |    updatedTime        |  StartDate    |    EndDate  	|   isExpired   |      web url        |   isUpdated   |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| 301          	   |      101        |      25570111         |  行天宮助寒獎學金	 	   |	 50000             |  2023/12/13 05:00 p.m |  2023/05/03   |  2023/05/10    |    true     	|   www.book.com.tw   |	  true        |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

 * 
 * 
 * 
 */

public class Scholarship {
	
	private Integer scholarshipId;
	private Integer userId;
	private String institutionId;
	private String  scholarshipName;
	private Integer scholarshipAmount;
	private  String  updatedTime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private String startDate ;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private String endDate;
	private boolean isExpired ;
	private String  webUrl;
	private boolean  isUpdated;
	private String entity;
	private Institution institution;
	
	public Scholarship() {
	
	}
	
	public Scholarship(Integer scholarshipId, Integer userId, String scholarshipName,String entity, Integer scholarshipAmount,
String updatedTime, String startDate, String endDate, String webUrl) {
		this.scholarshipId = scholarshipId;
//		this.userId = userId;
		this.scholarshipName = scholarshipName;
//		this.entity= entity;
		this.scholarshipAmount = scholarshipAmount;
		this.updatedTime = updatedTime; //now time
		this.startDate = startDate;
		this.endDate = endDate;
		this.webUrl = webUrl;
	}
	
	
	
	public String getInstitutionId() {
		return institutionId;
	}
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this. endDate = endDate;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public boolean isUpdated() {
		return isUpdated;
	}
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	public Integer getScholarshipId() {
		return scholarshipId;
	}
	public boolean isExpired() {
		return isExpired;
	}
	
	public String toString() {
		return new Gson().toJson(this);
	}



	public Object getUserId() {
		// TODO Auto-generated method stub
		return null;
	}



	public String getUpdatedTime() {
		return updatedTime;
	}



	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}



	public Institution getInstitution() {
		return institution;
	}



	public void setInstitution(Institution institution) {
		this.institution = institution;
	}



	public void setScholarshipId(Integer scholarshipId) {
		this.scholarshipId = scholarshipId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}



	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}



	public Object getIsExpired() {
		// TODO Auto-generated method stub
		return null;
	}



	public Object getIsUpdated() {
		// TODO Auto-generated method stub
		return null;
	}





	}

	
	
	

