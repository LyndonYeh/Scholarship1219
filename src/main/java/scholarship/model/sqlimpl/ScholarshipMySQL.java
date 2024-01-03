package scholarship.model.sqlimpl;

import java.sql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.InstitutionDao;
import scholarship.model.sqlimpl.InstitutionMySQL;


@Repository
public class ScholarshipMySQL implements ScholarshipDao {


	
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private InstitutionDao institutionDao;

    // 原來的 private AtomicInteger scholarshipIdGenerator  註解掉 
    //private AtomicInteger scholarshipIdGenerator = new AtomicInteger(100);
    
    // 新增 getNextScholarshipId()
    // 直接在其他要加的 dao 方法 把 scholarshipId 設定成這個 getNextScholarshipId()
    // 下面先用 addScholarship 示範
    private int getNextScholarshipId() {
        // SQL找scholarshipid最大值
        String LatestIdSql = "SELECT MAX(scholarshipid) FROM scholarshipv1.scholarshiprecord";
        // scholarshipid 最大值
        Integer latestId = namedParameterJdbcTemplate.queryForObject(LatestIdSql, new MapSqlParameterSource(), Integer.class);
        // return 最大值 + 1 如果 null 最大值預設 100, 但應該不會 
        return (latestId != null) ? latestId + 1 : 100;
    }
 
 
    
    @Override
    public void addScholarship(Scholarship scholarship) {
        String sql = "INSERT INTO scholarshipv1.scholarshiprecord (scholarshipId, userId, institutionId, scholarshipName, scholarshipAmount, entityId, updatedTime, startDate, endDate, isExpired, webUrl, isUpdated) " +
                "VALUES (:scholarshipId, :userId, :institutionId, :scholarshipName, :scholarshipAmount, :entity, :updatedTime, :startDate, :endDate, :isExpired, :webUrl, :isUpdated)";
       
        int scholarshipId = getNextScholarshipId();
        
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);
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
        String sql = "UPDATE scholarshipv1.cholarshiprecord SET isUpdated = :isUpdated WHERE scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("isUpdated", isUpdated);
        params.put("scholarshipId", scholarshipId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    @Override
    public boolean removeScholarshipById(Integer scholarshipId) {
        String sql = "DELETE FROM  scholarshipv1.scholarshiprecord WHERE scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);

        int rowsDeleted = namedParameterJdbcTemplate.update(sql, params);
        return rowsDeleted > 0;
    }

    @Override
    public List<Scholarship> findScholarshipByInstitutionId(String institutionId) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE institutionId = :institutionId";
        Map<String, Object> params = new HashMap<>();
        params.put("institutionId", institutionId);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findAllscholarship() {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord";
        List<Scholarship> scholarships = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);
        //scholarships.forEach(scholarhship -> enrichScholarshipWithDetails(scholarhship));
        return scholarships;
    }

    @Override
    public List<Scholarship> findScholarshipByEntity(String entity) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE entity = :entity";
        Map<String, Object> params = new HashMap<>();
        params.put("entity", entity);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findScholarshipByEntityAndAmount(String entity, Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE  entity = :entity AND scholarshipAmount = :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        
        params.put("entity", entity);
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    @Override
    public List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE scholarshipAmount = :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    }
    @Override
    public Optional<Scholarship> findScholarshipById(Integer scholarshipId) {
    	String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);

        Scholarship scholarship = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Scholarship.class));
        return Optional.ofNullable(scholarship);
    }
    
	private void enrichScholarshipWithDetails(Scholarship scholarship) {
		// 注入 Institution
		//InstitutionDao IDao= new InstitutionMySQL();
		
		//IDao.findInstitutionByInstitutionId(scholarship.getInstitutionId()).ifPresent(scholarship::setInstitution);
		Optional<Institution> addInstitution=institutionDao.findInstitutionByInstitutionId(scholarship.getInstitutionId());
		scholarship.setInstitution(addInstitution.get());
		
		
	}

}
