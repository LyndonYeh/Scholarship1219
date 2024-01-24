package scholarship.model.sqlimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import scholarship.bean.Entity;
import scholarship.model.dao.EntityDao;


@Repository
public class EntityMySQL implements EntityDao {
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Override
	public Optional<Entity> findEntityByEntityId(Integer entityId) {
		String sql = "SELECT * FROM scholarshipv1.entity where entityId = :entityId";
		Map<String, Object> params = new HashMap<>();
		params.put("entityId", entityId);
		Entity entity = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Entity.class));
		return Optional.of(entity);
	}

}
