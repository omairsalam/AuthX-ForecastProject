import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

	
	//private JSONObject forecastData;
	
	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * 
	 */
	
	public void populateForecastStatic() throws IOException, ParseException{
		
		JSONParser jparser = new JSONParser();
		FileReader file = new FileReader("/Users/user/Documents/JSONFile.json"); // Change the file path
		
		Object object = jparser.parse(file); 
		
		JSONArray jarray = (JSONArray)object;
		
		for(int tuple=0;tuple<jarray.size();tuple++){
			
			JSONObject jobject =(JSONObject) jarray.get(tuple);
			
			String tag= (String)jobject.get("Tags");
			String projectCode=(String)jobject.get("Project Code");
			
			Role roleobj = new Role();
			ProjectType projecttypeobj = new ProjectType();
			
			roleobj.getPmap().put(projectCode,projecttypeobj);
			roleobj.getEmp_list().add((String)(jobject.get("Person")));
			roleMap.put(tag, roleobj);
			
			System.out.print(tag + " "+roleMap.get(tag).getEmp_list() + " ");
			
		}
		
	}
	
	/**
	 * 
	 * @param start_date
	 * @param end_date
	 */
	
	public void populateForcastDyanamic(Date start_date,Date end_date){
		
	}

}
