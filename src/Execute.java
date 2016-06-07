import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


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

	private JSONArray forecastData;


    public void setForecastData(JSONArray forecastData){
        this.forecastData = forecastData;
    }


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
	
	/**
	 * 
	 */
	
	public void populateForecastStatic(){
		
	}
	
	/**
	 * This method goes through the JSON and generates the Week -> Hours map
	 * @param start_date
	 * @param end_date
	 */
	
	public void populateForcastDyanamic(Date start_date, Date end_date){

        //This clears the employee list so that the number of employees is set properly
        //This also clears the project types to reaccumulate hours
		for (String key : roleMap.keySet()){
            Role role = roleMap.get(key);
			role.getEmp_list().clear();
            for (String keyProject : role.getPmap().keySet()){
                role.getPmap().get(keyProject).getWMap().clear();
            }
		}

		for (Object obj : forecastData){
			JSONObject jsonObject = (JSONObject)obj;

            //This gets the proper objects from the JSON
			String roleString = (String) jsonObject.get("Tags");
            String projectTypeString = (String) jsonObject.get("Project Code");

            //This gets the appropriate roles and project types
            //TODO: Handle the case in which project type is empty, ""
            Role role = roleMap.get(roleString);
            ProjectType projectType = role.getPmap().get(projectTypeString);

            //This builds up the list of employees
            String personString = (String) jsonObject.get("Person");
            role.getEmp_list().add(personString);

            System.out.println("Role Name: " + roleString);
            System.out.println("ProjectType Name: " + projectTypeString);
            System.out.println("Person: " + personString);

            //This for loop goes through the dates in the range
            //For each date in the range, it will add to the proper type map
			for (Date tmp = start_date; tmp.before(end_date); tmp = incementBy7(tmp)){
                String forecastDateString = convertDatetoForecastString(tmp);

                String hoursString = (String) jsonObject.get(forecastDateString);

                //This checks if the json array actually has the string key
                if (hoursString == null){
                    projectType.addtoMap(tmp, 0d);
                }else{
                    double hoursDouble = Double.parseDouble(hoursString);

                    System.out.println("Week Key: " + forecastDateString);
                    System.out.println("Hours: " + hoursDouble);

                    projectType.addtoMap(tmp, hoursDouble);
                }

			}

            System.out.println("----------------------NEW TUPLE----------------------");

		}

	}

	public static Date incementBy7(Date cDate){

		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(cDate);
		currentDate.add(Calendar.DATE, 7);
		Date resultDate = currentDate.getTime();
		return resultDate;
	}

	/**
	 * This method converts a date into string that forecast uses for the same date
	 * @param date
	 */
	public static String convertDatetoForecastString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String forecastDate = sdf.format(date);
		return forecastDate;

	}

}
