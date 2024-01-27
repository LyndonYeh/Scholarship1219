package scholarship.model.dao; 

import java.util.*;
import scholarship.bean.*;

public interface ScholarshipDao {

	/*
	 * 獎學金上傳紀錄 Scholarship
	 */
	
	
	/**
	 * 1. 新增一筆獎學金
	 * @param scholarship
	 */

	void addScholarship(Scholarship scholarship);

	/**
	 * 2.根據 scholoarshipId 更改上架狀態
	 * @param scholoarshipId
	 * @return 是否更改成功
	 */

	Boolean updateLauchStatusbyId(Integer scholoarshipId, Boolean isUpdated);

	/**
	 * 3. 刪除一筆獎學金
	 * @param scholoarshipId
	 * @return 是否刪除成功
	 */

	Boolean removeScholarshipById(Integer scholoarshipId);

	/**
	 * 4. 查詢獎學金紀錄(包含上架與否與過期與否 僅限項目所有方)
	 * @param institutionId
	 * @return 所有獎學金資訊
	 */

	List<Scholarship> findScholarshipByInstitutionId(String institutionId);

	/**
	 * 5. 查詢所有獎學金列表
	 * @return 所有獎學金列表
	 */

	List<Scholarship> findAllscholarship();

	/**
	 * 6.查詢所有已上架獎學金列表
	 * @return 所有已上架獎學金列表
	 */
	List<Scholarship> findAllscholarshipisUpdated();

	/**
	 * 7-1. 根據entityid查詢獎學金
	 * @param entity
	 * @return 獎學金
	 */

	List<Scholarship> findScholarshipByEntityId(Integer entityId);

	/**
	 * 7-2. 根據entityid/scholarshipAmount查詢獎學金 
	 * @param entity
	 * @param scholarshipAmount
	 * @return 獎學金
	 */

	List<Scholarship> findScholarshipByEntityIdAndAmount(Integer entityid, Integer scholarshipAmount);

	/**
	 * 7-3. 根據scholarshipAmount查詢獎學金
	 * @param scholarshipAmount
	 * @return 獎學金
	 */

	List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount);

	/**
	 * 7-4. 根據scholarshipId查詢獎學金 
	 * @param scholarshipAmount
	 * @return 獎學金
	 */

	Optional<Scholarship> findScholarshipById(Integer scholarshipId);

	/**
	 * 8.根據scholarshipId刪除獎學金至資源回收桶
	 * @param scholarship
	 * 
	 */
	void addScholarshipToGarbageCollection(Scholarship scholarship);

	
	/*
	 * 資源回收桶 GgarbageCollection
	 */
	
	
	/**
	 * 1. 根據scholarshipId將資料移出至資源回收桶(復原)
	 * @param scholarshipId
	 * @return 是否刪除成功
	 */
	Boolean removeScholarshipByIdFromGarbageCollection(Integer scholarshipId);

	/**
	 * 2. 根據 institutionId 從資源回收桶查詢一筆獎學金紀錄
	 * @param institutionId
	 * @return 獎學金資訊
	 */
	List<Scholarship> findScholarshipByInstitutionIdFromGarbageCollection(String institutionId);
	
	/**
	 * 3. 根據 scholarshipId 從資源回收桶查詢一筆獎學金紀錄
	 * @param scholarshipId
	 * @return 獎學金資訊
	 */
	Optional<Scholarship> findScholarshipByIdFromGarbageCollection(Integer scholarshipId);

}