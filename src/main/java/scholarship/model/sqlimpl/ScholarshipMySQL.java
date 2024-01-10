package scholarship.model.sqlimpl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.InstitutionDao;


@Repository
public class ScholarshipMySQL implements ScholarshipDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private InstitutionDao institutionDao;
    

    
    private int getNextScholarshipId() {
        String LatestIdSql = "SELECT MAX(scholarshipid) FROM scholarshipv1.scholarshiprecord";
        Integer latestId = namedParameterJdbcTemplate.queryForObject(LatestIdSql, new MapSqlParameterSource(), Integer.class);
        return (latestId != null) ? latestId + 1 : 100;
    }
 
 
    
    @Override
    public void addScholarship(Scholarship scholarship) {
        String sql = "INSERT INTO scholarshipv1.scholarshiprecord (scholarshipId, userId, institutionId, scholarshipName, scholarshipAmount, entityId, updatedTime, startDate, endDate, isExpired, webUrl, isUpdated) " +
                "VALUES (:scholarshipId, :userId, :institutionId, :scholarshipName, :scholarshipAmount, :entity, :updatedTime, :startDate, :endDate, :isExpired, :webUrl, :isUpdated)";
       
        int scholarshipId = getNextScholarshipId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss"); 
        
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);
        params.put("userId", scholarship.getUserId());
        params.put("institutionId", scholarship.getInstitutionId());
        params.put("scholarshipName", scholarship.getScholarshipName());
        params.put("scholarshipAmount", scholarship.getScholarshipAmount());
        params.put("entity", scholarship.getEntity());
        
        //params.put("updatedTime", Date.valueOf(scholarship.getUpdatedTime()));
        params.put("updatedTime", format.format(System.currentTimeMillis()));
        params.put("startDate", scholarship.getStartDate());
        params.put("endDate", Date.valueOf(scholarship.getEndDate()));
        params.put("isExpired", false);
        params.put("webUrl", scholarship.getWebUrl());
        params.put("isUpdated", false);

        namedParameterJdbcTemplate.update(sql, params);
    }

    
    
    @Override
    public Boolean updateLauchStatusbyId(Integer scholarshipId, Boolean isUpdated) {
        String sql = "UPDATE scholarshipv1.cholarshiprecord SET isUpdated = :isUpdated WHERE scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("isUpdated", isUpdated);
        params.put("scholarshipId", scholarshipId);

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        return rowsUpdated > 0;
    }

    
    
    @Override
    public Boolean removeScholarshipById(Integer scholarshipId) {
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

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    
    
    @Override
    public List<Scholarship> findAllscholarship() {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord";
        List<Scholarship> scholarships = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);

        return scholarships;
    }
    
    
    
    @Override
    public List<Scholarship> findAllscholarshipisUpdated() {
    	String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord where isUpdated = true";
    	List<Scholarship> scholarships = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Scholarship.class));
    	scholarships.forEach(this::enrichScholarshipWithDetails);
    	
    	return scholarships;
    }
   
    

    @Override
    public List<Scholarship> findScholarshipByEntityId(Integer entityId) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE entityId = :entityId";
        Map<String, Object> params = new HashMap<>();
        params.put("entityId", entityId);

        return namedParameterJdbcTemplate.query(sql,params ,new BeanPropertyRowMapper<>(Scholarship.class));
    }

    
    
    @Override
    public List<Scholarship> findScholarshipByEntityIdAndAmount(Integer entityId, Integer scholarshipAmount) {

        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE entityid = :entity AND scholarshipAmount > :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        
        params.put("entityId", entityId);
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql,params, new BeanPropertyRowMapper<>(Scholarship.class));
    }

    
    
    @Override
    public List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE scholarshipAmount > :scholarshipAmount AND";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipAmount", scholarshipAmount);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Scholarship.class));
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
