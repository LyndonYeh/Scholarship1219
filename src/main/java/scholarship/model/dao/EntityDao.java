package scholarship.model.dao;

import java.util.Optional;
import scholarship.bean.Entity;

public interface EntityDao {
	
	/**
     * 1. 根據 EntityId 查找 Entity
     * @param EntityId
     */
	
	Optional <Entity> findEntityByEntityId(Integer entityId);

}
