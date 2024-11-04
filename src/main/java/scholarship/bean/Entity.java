package scholarship.bean;

/**
 * 4. 身分別
+----------------------------------------+
| entityId  	| entityName  			 |     
+----------------------------------------+
|    1          |   幼稚園       			 | 
|    2          |    國小          		 | 
|    3          |    國中          		 | 
|    4          |    高中          		 | 
|    5          |    大學		   		 |
|    6          |   幼稚園       			 | 
+----------------------------------------+
 */

public class Entity {
	
	private Integer entityId;
	private String entityName;
	
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	
}
