package main;

import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.internal.Graph;

/**
 * This is the execute class with with a variable type of Map. It links -
 * particular role. Another variable is the JSONObject that contains the
 * complete JSONFile.
 *
 * There are two methods namely populateForecastStatic() and
 * populateForcastDyanamic(Date start_date,Date end_date) with two parameters
 * start_date and end_date type.
 *
 * @author user
 *
 */
//TODO: Handle the case in which project type is empty, ""
public class Execute {

    private HashMap<String, Role> roleMap = new HashMap<String, Role>();

    private JSONArray forecastData;

    LinkedHashSet<Date> dateSet = new LinkedHashSet<Date>();

    private Date endDate = new Date();

    private int NUMWEEKS = 2;

    public void setForecastData(JSONArray forecastData) {
        this.forecastData = forecastData;
    }

    /**
     * Gets this roleMap
     *
     * @return the roleMap attribute for this instance of the class
     */
    public HashMap<String, Role> getRoleMap() {
        return roleMap;
    }

    /**
     * Sets the initial data for the roleMap, and then returns it
     *
     * @param startDate The start date to look in the data for
     * @param contentArray JSON file that was converted from the CSV; the raw
     * data as a JSON
     * @return A hashmap that contains the roles with their tags as keys 
     */
    public HashMap<String, Role> initialize(Date startDate, JSONArray contentArray) {
        setForecastData(contentArray);

        //Increments month by one
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(startDate);
        currentDate.add(Calendar.WEEK_OF_YEAR, NUMWEEKS); //changing to a year 
        endDate = currentDate.getTime();

        try {
            populateForecastStatic();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        populateForcastDyanamic(startDate, endDate);

        return getRoleMap();
    }

    /**
     * Sets the roleMap of this class
     *
     * @param roleMap The new role map which we want to use as the default role
     * map
     */
    public void setRoleMap(HashMap<String, Role> roleMap) {
        this.roleMap = roleMap;
    }

    /**
     * Populates the roleMap with a tag (as the key) and a Role instance, which
     * in turn is populated with the Employee name of the tuple we are at as
     * well as Project codes and the project types. The only thing that is not
     * populated after this step is the wMap in the Project class which contains
     * a week-hours method
     *
     * @throws IOException -
     * @throws ParseException -
     */
    public void populateForecastStatic() throws IOException, ParseException {

        for (int tuple = 0; tuple < forecastData.size(); tuple++) {

            JSONObject jobject = (JSONObject) forecastData.get(tuple);

            String tag = (String) jobject.get("Tags");
            String projectCode = (String) jobject.get("Project Label");
            String personLabel = (String) jobject.get("Person");

            ProjectType projecttypeobj = new ProjectType();

            Role roleobj = new Role();

            //If the role map contains the role already, then work on that one instead of a new one
            if (roleMap.containsKey(tag)) {
                roleobj = roleMap.get(tag);
            }

            roleobj.getPmap().put(projectCode, projecttypeobj);

            roleMap.put(tag, roleobj);
        }
    }

    /**
     * This method goes through the JSON and generates the Week - Hours map
     *
     * @param start_date - The start date for which the data should be evaluated
     * for
     * @param end_date - The end date for which the data should be evaluated for
     */
    public void populateForcastDyanamic(Date start_date, Date end_date) {

        //This clears the employee list so that the number of employees is set properly
        //This also clears the project types to reaccumulate hours
        for (String key : roleMap.keySet()) {
            Role role = roleMap.get(key);
            role.getEmp_Set().clear();
            for (String keyProject : role.getPmap().keySet()) {
                role.getPmap().get(keyProject).getWMap().clear();
            }
        }

        for (Object obj : forecastData) {
            JSONObject jsonObject = (JSONObject) obj;

            //This gets the proper objects from the JSON
            String roleString = (String) jsonObject.get("Tags");
            String projectTypeString = (String) jsonObject.get("Project Label"); //changed project code to project label 

            //This gets the appropriate roles and project types
            Role role = roleMap.get(roleString);
            ProjectType projectType = role.getPmap().get(projectTypeString);

            //This builds up the list of employees
            String personLabel = (String) jsonObject.get("Person");

            if (personLabel.contains("Unassigned")) {
                System.out.println("Unassigned person found!");

            } else {
                role.getEmp_Set().add(personLabel);
            }

            //This for loop goes through the dates in the range
            //For each date in the range, it will add to the proper type map
            for (Date tmp = start_date; tmp.before(end_date); tmp = incementBy7(tmp)) {
                String forecastDateString = convertDatetoForecastString(tmp);

                String hoursString = (String) jsonObject.get(forecastDateString);

                //This checks if the json array actually has the string key
                if (hoursString == null) {
                    projectType.addtoMap(tmp, 0d);

                } else {
                    Double hoursDouble = Double.parseDouble(hoursString);

                    projectType.addtoMap(tmp, hoursDouble);

                    dateSet.add(tmp);

                }
            }
        }

        //Iterates through the roles, then sets the employee number int to the size of the list
        for (String roleKey : roleMap.keySet()) {
            Role role = roleMap.get(roleKey);
            role.setNumEmployees(role.getEmp_Set().size());
        }

        processRoleHierarchy();

    }

    /**
     * The role hierarchy is as follows: Likely contains highly likely,
     * Internal, Signed and at Risk so each project contains the hours of all
     * the preceeding project types in this list
     */
    public void processRoleHierarchy() {
        System.out.println("List of rolenames is" + roleMap.keySet());
        for (String roleName : roleMap.keySet()) {
            Role role = roleMap.get(roleName);
            System.out.println("Role name is " + roleName);

            HashMap<String, ProjectType> ProjectMap = role.getPmap();

            HashMap<Date, Double> weekMapLikely = null;
            ProjectType projectTypeLikely = ProjectMap.get("Likely");
            if (projectTypeLikely != null) {
                weekMapLikely = projectTypeLikely.getWMap();
                if (roleName.equals("BED")) {
                    //    System.out.println("Hashmap for " + roleName + " with project label: Likely  is");
                    //     printHashMap(projectTypeLikely.getWMap());
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("*");
            }

            HashMap<Date, Double> weekMapHighlyLikely = null;
            ProjectType projectTypeHighlyLikely = ProjectMap.get("High Likely");
            if (projectTypeHighlyLikely != null) {
                weekMapHighlyLikely = projectTypeHighlyLikely.getWMap();
                if (roleName.equals("BED")) {
                    //    System.out.println("Hashmap for " + roleName + " with project label: projectTypeHighlyLikely  is");
                    //    printHashMap(projectTypeHighlyLikely.getWMap());
                }
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("*");
            }

            HashMap<Date, Double> weekMapSigned = null;
            ProjectType projectTypeSigned = ProjectMap.get("Signed");
            if (projectTypeSigned != null) {
                weekMapSigned = projectTypeSigned.getWMap();
                if (roleName.equals("BED")) {
                    //    System.out.println("Hashmap for " + roleName + " with project label: projectTypeSigned  is");
                    //    printHashMap(projectTypeSigned.getWMap());
                }
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("*");
            }

            /*HashMap<Date, Double> weekMapAtRisk = null;
                           ProjectType projectTypeAtRisk = ProjectMap.get("AT-RISK");
                           if (projectTypeAtRisk != null){ weekMapAtRisk = projectTypeAtRisk.getWMap(); }*/
            HashMap<Date, Double> weekMapAtRisk = null;
            ProjectType projectTypeAtRisk = ProjectMap.get("At Risk");
            if (projectTypeAtRisk != null) {
                weekMapAtRisk = projectTypeAtRisk.getWMap();
                if (roleName.equals("BED")) {
                    //    System.out.println("Hashmap for " + roleName + " with project label: projectTypeAtRisk  is: ");
                    //            printHashMap(projectTypeAtRisk.getWMap());
                }

            }

            HashMap<Date, Double> weekMapInternal = null;
            ProjectType projectTypeInternal = ProjectMap.get("Internal Projects");
            if (projectTypeInternal != null) {
                weekMapInternal = projectTypeInternal.getWMap();
                if (roleName.equals("BED")) {
                    //    System.out.println("Hashmap for " + roleName + " with project label: projectTypeAtRisk  is: ");
                    //            printHashMap(projectTypeAtRisk.getWMap());
                }

            }

            for (Date date : dateSet) {

                double signedHours = 0;
                double highlyLikelyHours = 0;
                double likelyHours = 0;
                double internalHours = 0;

                //Checks if "AT-RISK" projects exists
                //For ones that inherit "AT-RISK"//weekMapAtRisk
                if (weekMapAtRisk != null && weekMapAtRisk.containsKey(date)) {
                    double hours = weekMapAtRisk.get(date);

                    signedHours += hours;
                    internalHours += hours;
                    highlyLikelyHours += hours;
                    likelyHours += hours;
                }

                //Checks if "Signed" projects exists
                //For ones that inherit "Signed"
                if (weekMapSigned != null && weekMapSigned.containsKey(date)) {
                    double hours = weekMapSigned.get(date);
                    highlyLikelyHours += hours;
                    likelyHours += hours;
                    internalHours += hours;
                    ProjectMap.get("Signed").addtoMap(date, signedHours);

                }

                if (weekMapInternal != null && weekMapInternal.containsKey(date)) {
                    double hours = weekMapInternal.get(date);
                    highlyLikelyHours += hours;
                    likelyHours += hours;
                    ProjectMap.get("Internal Projects").addtoMap(date, internalHours);
                }

                //Checks if "Highly Likely" projects exists
                //For ones that inherit "Highly Likely"
                if (weekMapHighlyLikely != null && weekMapHighlyLikely.containsKey(date)) {
                    double hours = weekMapHighlyLikely.get(date);
                    likelyHours += hours;
                    ProjectMap.get("High Likely").addtoMap(date, highlyLikelyHours);
                }

                //Checks if "Likely" projects exists
                if (weekMapLikely != null && weekMapLikely.containsKey(date)) {
                    ProjectMap.get("Likely").addtoMap(date, likelyHours);
                }

            }
        }
    }

    /**
     * Prints the content of a hashmap which contains Date and Double pairs
     * @param weekMap The hashmap to be printed out 
     */
    public static void printHashMap(HashMap<Date, Double> weekMap) {
        for (Date s : weekMap.keySet()) {
            System.out.println("Date is" + s + " and hours are " + weekMap.get(s));
        }
    }

    /**
     * Gets the end date (which is a global private variable in this class)
     *
     * @return The end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Incrmements a specific date by 7 days
     *
     * @param cDate The initial date
     * @return The new date
     */
    public static Date incementBy7(Date cDate) {

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(cDate);
        currentDate.add(Calendar.DATE, 7);
        Date resultDate = currentDate.getTime();
        return resultDate;
    }

    /**
     * This method converts a date into string that forecast uses for the same
     * date
     *
     * @param date The date which we want to convert to string
     * @return The date as a forecast type string
     */
    public static String convertDatetoForecastString(Date date) {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String forecastDate = sdf.format(date);
		return forecastDate;*/

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;

        String formattedDate = month + "/" + day + "/" + year % 100;
        //System.out.println("Date is " + formattedDate);
        return formattedDate;
    }

}
