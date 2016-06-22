package main;

import org.jfree.chart.ChartFrame;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.jfree.chart.JFreeChart;

/**
 * This class organizes all the background processing from getting the JSON File from Webscrapper to populating the graphs required
 * for display on the screen. 
 * @author alam
 */
public class Driver {

    static Execute execute = new Execute();
    static GraphPlotter graphPlotter = new GraphPlotter();
    static WebScrapper webScrapper;


    //Rescales the specified map for the number of employees
    /**
     * Rescales the specified graph for the role for the given employee number
     * @param roleName the name of the role to rescale
     * @param employeeNumber the number of employees to scale the map for
     */
    public static void rescaleMap(String roleName, int employeeNumber){
        HashMap<String, Role> roleMapClone = graphPlotter.rescale(execute.getRoleMap(), roleName, employeeNumber);
        ArrayList<JFreeChart> chartList = graphPlotter.getAllFrames(roleMapClone);
        //guiHandler.setGraphs(chartList);
    }


    /**
     * Uses the webscrapper to get a json file from forecastapp.com, calculates an initial roleMap based on default start and end dates,
     * and gets a list of charts that can be displayed on a JPanel
     * @param username The username for forecastapp.com
     * @param password The password for forecastapp.com
     * @param downloadPath The download directory to store the CSV file and the JSON file 
     * @return A list of charts that can be plotted on any JPanel 
     * @throws InterruptedException -
     * @throws ParseException -
     * @throws JSONException -
     * @throws IOException -
     * @throws java.text.ParseException -
     */
    public static ArrayList<JFreeChart> login(String username, String password, String downloadPath) throws InterruptedException, ParseException, JSONException, IOException, java.text.ParseException {


        webScrapper = new WebScrapper(downloadPath, username, password);


        JSONArray content = webScrapper.downloadJSONArray();

        Date startDate = webScrapper.getStartDate();
        HashMap<String, Role> roleMap = execute.initialize(startDate, content);

        GraphPlotter p1 = new GraphPlotter();
        
        ArrayList<JFreeChart> charts = p1.getAllFrames(roleMap);
        return charts;

    }
    /**
     * Redraws the graphs based on a new rolemap which is based on new start and end dates 
     * @param startDate The new start date for the role map
     * @param endDate The new end date for the rolemap 
     * @return An Array List of charts that can be used by MainFrame.java for plotting 
     * @throws InterruptedException -
     * @throws ParseException -
     * @throws JSONException -
     * @throws IOException -
     * @throws java.text.ParseException -
     */
        public static ArrayList<JFreeChart> recalculateMaps(Date startDate, Date endDate) throws InterruptedException, ParseException, JSONException, IOException, java.text.ParseException {

        execute.populateForcastDyanamic(startDate, endDate);
        HashMap<String, Role> roleMap = execute.getRoleMap();

        
        ArrayList<JFreeChart> charts = graphPlotter.getAllFrames(roleMap);
        return charts;

    }

}
