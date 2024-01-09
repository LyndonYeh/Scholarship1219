package scholarship.model.dao; // Replace with your actual package name

import java.util.*;

import scholarship.bean.*;

public interface ScholarshipDao {

	/*
	 * 獎學金上傳紀錄
+-----------+-------+--------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| scholoarshipId   |    userId       |    institutionId      |   scholarshipName       |  scholarshipAmount    	|     entity            |       updatedTime   	 	|   StartDate         |   EndDate   		|   isExpired   |      webUrl         		  |   isUpdated  |
+-----------+-------------+-------+--------+----------------------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------
| 301          	   |      101        |      25570111          |      測試獎學金1	            |              50000              	|   high school  | 2023/12/13 05:00 p.m |  2023/05/03  | 2023/05/10  |    true       |   www.book.com.tw    |	  true    	  |
+-----------+-------------+-------+--------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| 302          	   |      101        |      25570111          |     	 測試獎學金2	        |               50000       	    |     graduate   | 2023/12/13 05:00 p.m |  2023/06/30  | 2023/08/30  |    true       |   www.book.com.tw    |	  true       	 |
+-----------+-------------+-------+--------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
	獎學金上傳紀錄
1. 新增一筆獎學金
2. 修改獎學金資訊
	//(1)根據 scholoarshipId 修改獎學金期間
	(2)根據 itemId/ scholoarshipId 更改上架狀態
	//(3)根據 itemId 修改聯絡資訊 包含網址
3. 刪除一筆獎學金
4. 查詢獎學金紀錄(包含上架與否與過期與否 僅限項目所有方)
5. 查詢所有獎學金列表 
6. 根據條件查詢獎學金
7. 根據條件(id)刪除獎學金
	*/
	
	
	
	/**
	 * 1. 新增一筆獎學金
	 * 
	 * @param Scholarship
	 */

	void addScholarship(Scholarship Scholarship);


	/**
	 * (2)根據 scholoarshipId 更改上架狀態
	 * 
	 * @param scholoarshipId
	 * @return 是否更改成功
	 */

	Boolean updateLauchStatusbyId(Integer scholoarshipId, Boolean isUpdated);

	/**
	 * 3. 刪除一筆獎學金
	 * 
	 * @param scholoarshipId
	 * @return 是否刪除成功
	 */

	Boolean removeScholarshipById(Integer scholoarshipId);

    /**4. 查詢獎學金紀錄(包含上架與否與過期與否 僅限項目所有方)
     * 
     * @param institutionId
     * @return 所有獎學金資訊
     */
	
	List<Scholarship> findScholarshipByInstitutionId(String  institutionId);
  /**  5. 查詢所有獎學金列表 
   * 
   * @return 所有獎學金列表
   */

	List<Scholarship> findAllscholarship();
	
	/**
	 *  6. 根據條件查詢獎學金
	 * @param entity
	 * @return
	 */
	
	List<Scholarship> findScholarshipByEntity(String entity);


	/**
	 *  7.. 根據條件查詢獎學金
	 * @param entity
	 * @param cholarshipAmount
	 * @return
	 */
	
	List<Scholarship> findScholarshipByEntityAndAmount(String entity,  Integer scholarshipAmount);
	
	/**
	 *  8. 根據條件查詢獎學金
	 * @param cholarshipAmount
	 * @return
	 */
	
	List<Scholarship> findScholarshipByAmount( Integer scholarshipAmount);
	
	
	/**
	 *  9. 根據ID查詢獎學金
	 * @param cholarshipAmount
	 * @return
	 */
	
	Optional<Scholarship> findScholarshipById( Integer scholarshipId);
	
}