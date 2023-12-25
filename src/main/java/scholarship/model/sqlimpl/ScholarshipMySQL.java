package scholarship.model.sqlimpl;

import java.sql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import scholarship.bean.Scholarship;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.InstitutionDao;


@Repository
public class ScholarshipMySQL implements ScholarshipDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private AtomicInteger scholarshipIdGenerator = new AtomicInteger(100);
    

    @Override
    public void addScholarship(Scholarship scholarship) {
        String sql = "INSERT INTO scholarshipv1.scholarshiprecord (scholarshipId, userId, institutionId, scholarshipName, scholarshipAmount, entityId, updatedTime, startDate, endDate, isExpired, webUrl, isUpdated) " +
                "VALUES (:scholarshipId, :userId, :institutionId, :scholarshipName, :scholarshipAmount, :entity, :updatedTime, :startDate, :endDate, :isExpired, :webUrl, :isUpdated)";

        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipIdGenerator.getAndIncrement());
        params.put("userId", scholarship.getUserId());
        params.put("institutionId", scholarship.getInstitutionId());
        params.put("scholarshipName", scholarship.getScholarshipName());
        params.put("scholarshipAmount", scholarship.getScholarshipAmount());
        params.put("entity", scholarship.getEntity());
        
        params.put("updatedTime", Date.valueOf(scholarship.getUpdatedTime()));
//        params.put("updatedTime", Timestamp.valueOf(scholarship.getUpdatedTime()));
        params.put("startDate", Date.valueOf(scholarship.getStartDate()));
        params.put("endDate", Date.valueOf(scholarship.getEndDate()));
        params.put("isExpired", scholarship.getIsExpired());
        params.put("webUrl", scholarship.getWebUrl());
        params.put("isUpdated", scholarship.getIsUpdated());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public boolean updateLauchStatusbyId(Integer scholarshipId, boolean isUpdated) {
        String sql = "UPDATE scholarshipv1.cholarshiprecord SET isUpdated = :isUpdated WHERE  scholarshipv1.scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("isUpdated", isUpdated);
        params.put("scholarshipId", scholarshipId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public boolean removeScholarshipById(Integer scholarshipId) {
        String sql = "DELETE FROM  scholarshipv1.scholarshiprecord WHERE  scholarshipv1.scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);

        int rowsDeleted = namedParameterJdbcTemplate.update(sql, params);
        return rowsDeleted > 0;
    }

    @Override
    public List<Scholarship> findScholarshipByInstitutionId(String institutionId) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE  scholarshipv1.institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institutionId);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findAllscholarship() {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findScholarshipByEntity(String entity) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE  scholarshipv1.entity = :entity";
        Map<String, Object> params = new HashMap<>();
        params.put("entity", entity);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findScholarshipByEntityAndAmount(String entity, Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE  scholarshipv1.entity = :entity AND  scholarshipv1.scholarshipAmount = :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        params.put("entity", entity);
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE  scholarshipv1.scholarshipAmount = :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }
    
	private void enrichScholarshipWithDetails(Scholarship scholarship) {
		// 注入 Institution
		InstitutionDao IDao= new InstitutionMySQL();
		
		IDao.findInstitutionByInstitutionId(scholarship.getInstitutionId()).ifPresent(scholarship::setInstitution);
		
	}
}
