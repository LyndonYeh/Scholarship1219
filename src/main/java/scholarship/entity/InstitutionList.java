package scholarship.entity;

import java.util.List;

/**
 * 1. 獎助機構
+-----------+-------------+-------+--------+------------------+
| organizationId | organizationName| contact | contact number |  
+-----------+-------------+-------+--------+-------------------+
|    25570111    |   行天宮助寒獎學金  | 陳小姐    |  0912345678      |
+-------+--------+---------------------------------------------+
 */
public class InstitutionList {

	private String organiationName;
	private Integer organizationId;
	private String contactNumber;

	public InstitutionList() {

	}

	public InstitutionList(Integer scholoarshipId, Integer userId, List<String> scholarshipItems, int scholarshipMaxAmount, String unit, String contact, String contactNumber) {
//		
	}

}
