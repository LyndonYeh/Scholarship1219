package scholarship.model.dao; 

import java.util.*;
import scholarship.bean.*;

public interface ScholarshipDao {

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
	 * @return 所有獎學金列表(已上架)
	 */
	List<Scholarship> findAllscholarshipisUpdated();

	/**
	 * 7. 根據entityid查詢獎學金
	 * @param entity
	 * @return
	 */

	List<Scholarship> findScholarshipByEntityId(Integer entityId);

	/**
	 * 8. 根據entityid/scholarshipAmount查詢獎學金 
	 * @param entity
	 * @param scholarshipAmount
	 * @return
	 */

	List<Scholarship> findScholarshipByEntityIdAndAmount(Integer entityid, Integer scholarshipAmount);

	/**
	 * 9. 根據scholarshipAmount查詢獎學金
	 * @param scholarshipAmount
	 * @return
	 */

	List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount);

	/**
	 * 10. 根據scholarshipId查詢獎學金 
	 * @param scholarshipAmount
	 * @return
	 */

	Optional<Scholarship> findScholarshipById(Integer scholarshipId);

	/**
	 * 11.根據條件(id)刪除獎學金至資源回收桶
	 * @param scholarship
	 */
	void addScholarshipToGarbageCollection(Scholarship scholarship);

	/**
	 * 12.根據scholarshipId將資料移出至資源回收桶(復原)
	 * @param scholarshipId
	 * @return 是否刪除成功
	 */
	Boolean removeScholarshipByIdFromGarbageCollection(Integer scholarshipId);

	/**
	 * 13.根據institutionId查詢獎學金紀錄(垃圾桶)
	 * @param institutionId
	 * @return 所有獎學金資訊
	 */
	List<Scholarship> findScholarshipByInstitutionIdFromGarbageCollection(String institutionId);
	
	/**
	 * 14. 根據scholarshipId查詢一筆獎學金(垃圾桶)
	 * @param scholarshipId
	 * @return 所有獎學金資訊
	 */
	Optional<Scholarship> findScholarshipByIdFromGarbageCollection(Integer scholarshipId);

}