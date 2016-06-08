package test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import main.ProjectType;
import main.WebScrapper;
import org.json.JSONException;
import org.json.simple.JSONArray;

import main.Execute;
import main.Role;


/**
 * Created by user on 6/7/16.
 */
public class ExecuteTest {

    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException, JSONException, InterruptedException {

        WebScrapper webScrapper = new WebScrapper("/Users/user/Downloads", "theoriginalsine@gmail.com", "forecast");

        JSONArray contentArray = webScrapper.downloadJSONArray();

        Execute execute = new Execute();
        execute.setForecastData(contentArray);

        execute.populateForecastStatic();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //setting the format that we want

        String startString = "2016-05-30";  // Specify start date as string
        Calendar endCalenderDate = Calendar.getInstance();
        endCalenderDate.setTime(sdf.parse(startString)); //setup end as start first so we can use increment using the Calender class API
        endCalenderDate.add(Calendar.DATE, 120); //end date is 3 months beyond start date

        Date startDate = sdf.parse(startString);
        Date endDate = endCalenderDate.getTime();

        execute.populateForcastDyanamic(startDate, endDate);

        printOut(execute.getRoleMap());

        execute.createPointLists(execute.getRoleMap());

    }

    public static void printOut(HashMap<String, Role> roleMap){
        for (String roleName : roleMap.keySet()){
            System.out.println("Role: " + roleName);

            Role role = roleMap.get(roleName);
            HashMap<String, ProjectType> projectTypeMap = role.getPmap();

            for (String projectCode : projectTypeMap.keySet()){
                System.out.println("    Project Code: " + projectCode);

                HashMap<Date, Double> weekMapHours = projectTypeMap.get(projectCode).getWMap();

                double employeeNumber = role.getEmp_Set().size();
                HashMap<Date, Double> weekMapEmployees = projectTypeMap.get(projectCode).scaleMap(employeeNumber);

                for (Date weekDate : weekMapHours.keySet()){
                    double hours = weekMapHours.get(weekDate);
                    double employeeHours = weekMapEmployees.get(weekDate);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String forecastDate = sdf.format(weekDate);

                    System.out.print("        Date: " + forecastDate);
                    System.out.println(" Hours: " + hours + ", Employees: " + employeeHours);
                }
            }
        }
    }
}
