package scholarship.model.sqlimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void addInstitution(Institution institution) {
        String sql = "INSERT INTO institution (institutionId, institutionName, contact, contactNumber) " +
                "VALUES (:institutionId, :institutionName, :contact, :contactNumber)";

        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institution.getInstitutionId());
        params.put("institutionName", institution.getInstitutionName());
        params.put("contact", institution.getContact());
        params.put("contactNumber", institution.getContactNumber());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Boolean updateContact(String institutionId, String newContact) {
        String sql = "UPDATE institution SET contact = :newContact WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("newContact", newContact);
        params.put("institutionId", institutionId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public Boolean updateContactNumberById(String institutionId, String newContactNumber) {
        String sql = "UPDATE institution SET contactNumber = :newContactNumber WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("newContactNumber", newContactNumber);
        params.put("institutionId", institutionId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public Boolean checkIfInstitutionExist(String institutionId) {
        String sql = "SELECT COUNT(*) FROM institution WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institutionId);
// 輸入 insId, count(*) 總筆數 如果有對到就是 1, 返回 true
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class) > 0;
    }

    @Override
    public List<Institution> findAllInstitutions() {
        String sql = "SELECT * FROM institution";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Institution.class));
    }
}
