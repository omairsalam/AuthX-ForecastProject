

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import main.Execute;


/**
 * Created by user on 6/7/16.
 */
public class ExecuteTest {

    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException {

        JSONArray contentArray = new JSONArray();

        try {
            JSONParser parser = new JSONParser();
            contentArray = (JSONArray) parser.parse(new FileReader("/Users/user/Documents/JSONFile.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Execute execute = new Execute();
        execute.setForecastData(contentArray);

        execute.populateForecastStatic();

        /*
        ProjectType pTypeHighlyLikely = new ProjectType();
        ProjectType pTypeLikely = new ProjectType();
        ProjectType pTypeSigned = new ProjectType();
        Role role = new Role();


        role.getPmap().put("Highly Likely", pTypeHighlyLikely);
        role.getPmap().put("Likely", pTypeLikely);
        role.getPmap().put("Signed", pTypeSigned);

        execute.getRoleMap().put("Front End", role);
        */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //setting the format that we want

        String startString = "2016-05-30";  // Specify start date as string
        Calendar endCalenderDate = Calendar.getInstance();
        endCalenderDate.setTime(sdf.parse(startString)); //setup end as start first so we can use increment using the Calender class API
        endCalenderDate.add(Calendar.DATE, 120); //end date is 3 months beyond start date

        Date startDate = sdf.parse(startString);
        Date endDate = endCalenderDate.getTime();

        execute.populateForcastDyanamic(startDate, endDate);
    }



}
