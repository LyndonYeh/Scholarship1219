package scholarship.bean;

import mvc.bean.spform.BaseData;

public class Entity {
	private Integer id;
	private String name;

	public Entity() {
		
	}
	public Entity(Integer id, String name) {
		
		this.id=id;
		this.name=name;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getDisplay() {
		return name + "(" + id + ")";
	}
	
}