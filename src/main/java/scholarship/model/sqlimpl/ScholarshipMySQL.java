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

import scholarship.bean.Entity;
import scholarship.bean.Institution;
import scholarship.bean.Scholarship;
import scholarship.model.dao.ScholarshipDao;
import scholarship.model.dao.EntityDao;
import scholarship.model.dao.InstitutionDao;


@Repository
public class ScholarshipMySQL implements ScholarshipDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private InstitutionDao institutionDao;
    
    @Autowired
    private EntityDao entityDao;
    

    
    private int getNextScholarshipId() {
        String LatestIdSql = "SELECT MAX(scholarshipid) FROM scholarshipv1.scholarshiprecord";
        Integer latestId = namedParameterJdbcTemplate.queryForObject(LatestIdSql, new MapSqlParameterSource(), Integer.class);
        return (latestId != null) ? latestId + 1 : 100;
    }
 
 
    
    @Override
    public void addScholarship(Scholarship scholarship) {
        String sql = "INSERT INTO scholarshipv1.scholarshiprecord (scholarshipId, userId, institutionId, scholarshipName, scholarshipAmount, entityId, updatedTime, startDate, endDate, isExpired, webUrl, isUpdated, contact, contactNumber) " +
                "VALUES (:scholarshipId, :userId, :institutionId, :scholarshipName, :scholarshipAmount, :entityId, :updatedTime, :startDate, :endDate, :isExpired, :webUrl, :isUpdated, :contact, :contactNumber)";
       
        int scholarshipId = getNextScholarshipId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
        
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);
        params.put("userId", scholarship.getUserId());
        params.put("institutionId", scholarship.getInstitutionId());
        params.put("scholarshipName", scholarship.getScholarshipName());
        params.put("scholarshipAmount", scholarship.getScholarshipAmount());
        params.put("entityId", scholarship.getEntityId());
        
        //params.put("updatedTime", Date.valueOf(scholarship.getUpdatedTime()));
        params.put("updatedTime", format.format(System.currentTimeMillis()));
        params.put("startDate", scholarship.getStartDate());
        params.put("endDate", Date.valueOf(scholarship.getEndDate()));
        params.put("isExpired", false);
        params.put("webUrl", scholarship.getWebUrl());
        params.put("isUpdated", false);
        params.put("contact", scholarship.getContact());
        params.put("contactNumber", scholarship.getContactNumber());

        namedParameterJdbcTemplate.update(sql, params);
    }
    //這個是垃圾桶的新增
    @Override
    public void addScholarshipToGarbageCollection(Scholarship scholarship) {
    	String sql = "INSERT INTO scholarshipv1.garbageCollection ( userId, institutionId, scholarshipName, scholarshipAmount, entityId, updatedTime, startDate, endDate, isExpired, webUrl, isUpdated, contact, contactNumber) " +
    			"VALUES ( :userId, :institutionId, :scholarshipName, :scholarshipAmount, :entityId, :updatedTime, :startDate, :endDate, :isExpired, :webUrl, :isUpdated, :contact, :contactNumber)";
    	
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
    	
    	Map<String, Object> params = new HashMap<>();
    	
    	params.put("userId", scholarship.getUserId());
    	params.put("institutionId", scholarship.getInstitutionId());
    	params.put("scholarshipName", scholarship.getScholarshipName());
    	params.put("scholarshipAmount", scholarship.getScholarshipAmount());
    	params.put("entityId", scholarship.getEntityId());
    	
    	//params.put("updatedTime", Date.valueOf(scholarship.getUpdatedTime()));
    	params.put("updatedTime", format.format(System.currentTimeMillis()));
    	params.put("startDate", scholarship.getStartDate());
    	params.put("endDate", Date.valueOf(scholarship.getEndDate()));
    	params.put("isExpired", false);
    	params.put("webUrl", scholarship.getWebUrl());
    	params.put("isUpdated", false);
    	params.put("contact", scholarship.getContact());
    	params.put("contactNumber", scholarship.getContactNumber());
    	
    	namedParameterJdbcTemplate.update(sql, params);
    }

    
    
    @Override
    public Boolean updateLauchStatusbyId(Integer scholarshipId, Boolean isUpdated) {
        String sql = "UPDATE scholarshipv1.scholarshiprecord SET isUpdated = :isUpdated WHERE scholarshipId = :scholarshipId";
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
    //這是垃圾桶的刪除(復原)
    @Override
    public Boolean removeScholarshipByIdFromGarbageCollection(Integer scholarshipId) {
    	String sql = "DELETE FROM  scholarshipv1.GarbageCollection WHERE scholarshipId = :scholarshipId";
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
        List<Scholarship> scholarships=namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);
        return scholarships;
    }
    @Override
    public List<Scholarship> findScholarshipByInstitutionIdFromGarbageCollection(String institutionId) {
    	String sql = "SELECT * FROM  scholarshipv1.garbageCollection WHERE institutionId = :institutionId";
    	Map<String, Object> params = new HashMap<>();
    	params.put("institutionId", institutionId);
    	List<Scholarship> scholarships=namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Scholarship.class));
    	scholarships.forEach(this::enrichScholarshipWithDetails);
    	return scholarships;
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
        List<Scholarship>scholarships =  namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);

        return scholarships;
    }

    
    
    @Override
    public List<Scholarship> findScholarshipByEntityIdAndAmount(Integer entityId, Integer scholarshipAmount) {

        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE entityId = :entityId AND scholarshipAmount > :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        
        params.put("entityId", entityId);
        params.put("scholarshipAmount", scholarshipAmount);
        List<Scholarship>scholarships =  namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);

        return scholarships;
    }

    
    
    @Override
    public List<Scholarship> findScholarshipByAmount(Integer scholarshipAmount) {
        String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE scholarshipAmount > :scholarshipAmount";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipAmount", scholarshipAmount);
        List<Scholarship>scholarships =  namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Scholarship.class));
        scholarships.forEach(this::enrichScholarshipWithDetails);

        return scholarships;
    }
    
    
    
    @Override
    public Optional<Scholarship> findScholarshipById(Integer scholarshipId) {
    	String sql = "SELECT * FROM  scholarshipv1.scholarshiprecord WHERE scholarshipId = :scholarshipId";
        Map<String, Object> params = new HashMap<>();
        params.put("scholarshipId", scholarshipId);

        Scholarship scholarship = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Scholarship.class));
        return Optional.ofNullable(scholarship);
    }
    
    @Override
    public Optional<Scholarship> findScholarshipByIdFromGarbageCollection(Integer scholarshipId) {
    	String sql = "SELECT * FROM  scholarshipv1.garbageCollection WHERE scholarshipId = :scholarshipId";
    	Map<String, Object> params = new HashMap<>();
    	params.put("scholarshipId", scholarshipId);
    	
    	Scholarship scholarship = namedParameterJdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Scholarship.class));
    	return Optional.ofNullable(scholarship);
    }
    
    
    
	private void enrichScholarshipWithDetails(Scholarship scholarship) {
		// 注入 Institution
		Optional<Institution> addInstitution=institutionDao.findInstitutionByInstitutionId(scholarship.getInstitutionId());
		Optional<Entity> addEntity=entityDao.findEntityByEntityId(scholarship.getEntityId());
		scholarship.setInstitution(addInstitution.get());
		scholarship.setEntity(addEntity.get());
	}
	
//	private void enrichScholarshipWithEntity(Scholarship scholarship) {
//		// 注入 Entity 到 Scholarship
//		
//		
//	}
	
	
}
