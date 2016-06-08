package main;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.internal.Graph;

/**
 * This is the execute class with with a variable type of Map. It links  -> particular role.
 * Another variable is the JSONObject that contains the complete JSONFile.
 *
 *  There are two methods namely populateForecastStatic() and populateForcastDyanamic(Date start_date,Date end_date)
 *  with two parameters start_date and end_date type.
 * @author user
 *
 */

//TODO: Handle the case in which project type is empty, ""
public class Execute {

	private HashMap<String, Role> roleMap = new HashMap<String,Role>();

	private JSONArray forecastData;

    public void setForecastData(JSONArray forecastData){
        this.forecastData = forecastData;
    }

    /**
     * Gets this roleMap
     * @return the roleMap attribute for this instance of the class 
     */

	public HashMap<String, Role> getRoleMap() {
		return roleMap;
	}
	
	/**
	 * Sets the roleMap of this class 
	 * @param roleMap The new role map which we want to use as the default role map
	 */
	public void setRoleMap(HashMap<String, Role> roleMap) {
		this.roleMap = roleMap;
	}

	/**
	 * Populates the roleMap with a tag (as the key) and a Role instance, which in turn is populated with the Employee name of the tuple we are at
	 * as well as Project codes and the project types. The only thing that is not populated after this step is the wMap in the Project class which
	 * contains a week-hours method 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void populateForecastStatic() throws IOException, ParseException{

		for(int tuple=0;tuple<forecastData.size();tuple++){

			JSONObject jobject =(JSONObject) forecastData.get(tuple);

			String tag= (String)jobject.get("Tags");
			String projectCode=(String)jobject.get("Project Code");

            ProjectType projecttypeobj = new ProjectType();

			Role roleobj = new Role();

			//If the role map contains the role already, then work on that one instead of a new one
			if (roleMap.containsKey(tag)){ roleobj = roleMap.get(tag); }

			roleobj.getPmap().put(projectCode, projecttypeobj);
			roleobj.getEmp_Set().add((String)(jobject.get("Person")));

			roleMap.put(tag, roleobj);
		}
	}

	/**
	 * This method goes through the JSON and generates the Week -> Hours map
	 * @param start_date - The start date for which the data should be evaluated for
	 * @param end_date - The end date for which the data should be evaluated for
	 */

	public void populateForcastDyanamic(Date start_date, Date end_date){

        //This clears the employee list so that the number of employees is set properly
        //This also clears the project types to reaccumulate hours
		for (String key : roleMap.keySet()){
            Role role = roleMap.get(key);
			role.getEmp_Set().clear();
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
            Role role = roleMap.get(roleString);
            ProjectType projectType = role.getPmap().get(projectTypeString);

            //This builds up the list of employees
            String personString = (String) jsonObject.get("Person");
            role.getEmp_Set().add(personString);

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

                    projectType.addtoMap(tmp, hoursDouble);
                }
			}
		}
	}

	/**
	 * Converts the roleMap into a format readable by the plotting method
	 */
	public void createPointLists(HashMap<String, Role> roleMap){

		//Iterate through Roles in the role map
		for (String roleName : roleMap.keySet()){
			System.out.println("Role: " + roleName);

			Role role = roleMap.get(roleName);
			HashMap<String, ProjectType> projectTypeMap = role.getPmap();

			//Iterate through ProjectTypes in this role
			for (String projectCode : projectTypeMap.keySet()){
				System.out.println("    Project Code: " + projectCode);

				//Scaling the map for the number of employees
				double employeeNumber = role.getEmp_Set().size();
				HashMap<Date, Double> weekMapEmployees = projectTypeMap.get(projectCode).scaleMap(employeeNumber);

				//Goes through the weeks -> hours and builds up the points into a list
				for (Date weekDate : weekMapEmployees.keySet()){
					double employeeHours = weekMapEmployees.get(weekDate);

					Calendar cal = Calendar.getInstance();
					cal.setTime(weekDate);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH);
					int day = cal.get(Calendar.DAY_OF_MONTH);

					System.out.print("        Date: " + day + " " + month + " " + year);
					System.out.println(" Employees: " + employeeHours);

					//Omair: Do the plotting here
					//X = Day, month, years
					//Y = employeeHours
				}
			}
		}
	}

	/**
	 * Incrmements a specific date by 7 days 
	 * @param cDate The initial date 
	 * @return The new date 
	 */
	public static Date incementBy7(Date cDate){

		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(cDate);
		currentDate.add(Calendar.DATE, 7);
		Date resultDate = currentDate.getTime();
		return resultDate;
	}

	/**
	 * This method converts a date into string that forecast uses for the same date
	 * @param date The date which we want to convert to string 
	 */
	public static String convertDatetoForecastString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String forecastDate = sdf.format(date);
		return forecastDate;

	}

}
