package scholarship.model.sqlimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import scholarship.bean.Institution;
import scholarship.model.dao.InstitutionDao;

@Repository
public class InstitutionMySQL implements InstitutionDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int addInstitution(Institution institution) {
        String sql = "INSERT INTO scholarshipv1.institution (institutionId, institutionName, contact, contactNumber) " +
                "VALUES (:institutionId, :institutionName, :contact, :contactNumber)";

        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institution.getInstitutionId());
        params.put("institutionName", institution.getInstitutionName());
        params.put("contact", institution.getContact());
        params.put("contactNumber", institution.getContactNumber());
        return namedParameterJdbcTemplate.update(sql, params);
    }
    

    @Override
    public Boolean updateContactById(String institutionId, String newContact) {
        String sql = "UPDATE scholarshipv1.institution SET contact = :newContact WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("newContact", newContact);
        params.put("institutionId", institutionId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public Boolean updateContactNumberById(String institutionId, String newContactNumber) {
        String sql = "UPDATE scholarshipv1.institution  SET contactNumber = :newContactNumber WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("newContactNumber", newContactNumber);
        params.put("institutionId", institutionId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public Boolean checkIfInstitutionExist(String institutionId) {
        String sql = "SELECT COUNT(*) FROM scholarshipv1.institution WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institutionId);
// 輸入 insId, count(*) 總筆數 如果有對到就是 1, 返回 true
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class) > 0;
    }

    @Override
    public List<Institution> findAllInstitutions() {
        String sql = "SELECT * FROM scholarshipv1.institution";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Institution.class));
    }

	@Override
	public Optional<Institution> findInstitutionByInstitutionName(String institutionName) {
        String sql = "SELECT * FROM scholarshipv1.institution where institutionName = :institutionName";
		Map<String, Object> params = new HashMap<>();
		params.put("institutionName", institutionName);
        Institution institution = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Institution.class));
        return Optional.ofNullable(institution);
	}
	@Override
	public Optional<Institution> findInstitutionByInstitutionId(String institutionId) {
		String sql = "SELECT * FROM scholarshipv1.institution where institutionId = :institutionId";
		Map<String, Object> params = new HashMap<>();
		params.put("institutionId", institutionId);
		Institution institution = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Institution.class));
		return Optional.of(institution);
	}


}
