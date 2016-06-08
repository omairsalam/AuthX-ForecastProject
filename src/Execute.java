import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
/**
 * This is the execute class with with a variable type of Map. It links  -> particular role.
 * Another variable is the JSONObject that contains the complete JSONFile.
 * 
 *  There are two methods namely populateForecastStatic() and populateForcastDyanamic(Date start_date,Date end_date)
 *  with two parameters start_date and end_date type. 
 * @author user
 *
 */
public class Execute {
	
	private HashMap<String, Role> roleMap = new HashMap<String,Role>();
	/**
	 * This defines getters and setters for roleMap variable of key-Value pair.
	 * @return
	 */
	public HashMap<String, Role> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(HashMap<String, Role> roleMap) {
		this.roleMap = roleMap;
	}

	
	private JSONObject forecastData;
	
	/**
	 * 
	 */
	
	public void populateForecastStatic(){
		
	}
	
	/**
	 * 
	 * @param start_date
	 * @param end_date
	 */
	
	public void populateForcastDyanamic(Date start_date,Date end_date){
		
	}

}
