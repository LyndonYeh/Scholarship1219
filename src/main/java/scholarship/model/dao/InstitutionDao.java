package scholarship.model.dao;

import java.util.List;
import java.util.Optional;

import scholarship.bean.Institution;
import scholarship.bean.User;

public interface InstitutionDao {

    /**
     * 1. 註冊獎助機構
     * @param institution
     */
    void addInstitution(Institution institution);
    
    /**
     * 2. 更改項目聯絡人
     *
     * @param institutionId     機構ID
     * @param newContact        新聯絡人
     * @return 是否更新成功
     */
    Boolean updateContactById(String institutionId, String newContact);

    /**
     * 3. 更改項目聯絡連絡電話
     *
     * @param institutionId     機構ID
     * @param newContactNumber  新聯絡電話
     * @return 是否更新成功
     */
    Boolean updateContactNumberById(String institutionId, String newContactNumber);

    /**
     *4. 根據id查詢機構是否已存在
     *
     * @param institutionId 機構ID
     * @return 是否存在
     */
    Boolean checkIfInstitutionExist(String institutionId);
    
    /**
     * 5. 根據機構名稱查詢機構是否已存在
     */
    
    Optional<Institution> findInstitutionByInstitutionName(String institutionName);

    /**
     *6. 查詢所有機構
     *
     * @return 所有機構列表
     */
    List<Institution> findAllInstitutions();
    
    /**
     * 7. 根據機構名稱查詢機構是否已存在
     */
    
   Optional <Institution> findInstitutionByInstitutionId(String institutionId);
    
    
    
}
