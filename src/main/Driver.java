package main;

import org.jfree.chart.ChartFrame;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.internal.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 6/8/16.
 */
public class Driver {

    static GUIHandler guiHandler = new GUIHandler();
    static Execute execute = new Execute();
    static GraphPlotter graphPlotter = new GraphPlotter();
    static WebScrapper webScrapper;


    /**
     * Executes when the program starts
     */
    public static void main(String[] args){
        guiHandler.displayLogin();
    }

    //Rescales the specified map for the number of employees
    /**
     * Rescales the specified graph for the role for the given employee number
     * @param roleName the name of the role to rescale
     * @param employeeNumber the number of employees to scale the map for
     */
    public static void rescaleMap(String roleName, int employeeNumber){
        HashMap<String, Role> roleMapClone = graphPlotter.rescale(roleName, employeeNumber);
        ArrayList<ChartFrame> chartList = graphPlotter.getAllFrames(roleMapClone);
        guiHandler.setGraphs(chartList);
    }


    /**
     * Sets the roleMap of this class
     * @throws java.text.ParseException 
     */
    public static void login(String username, String password, String downloadPath) throws InterruptedException, ParseException, JSONException, IOException, java.text.ParseException {

        //String downloadPath = args[0];
        //String username = args[1];
        //String password = args[2];

        downloadPath = "/Users/user/Desktop";
        username = "theoriginalsine@gmail.com";
        password = "forecast";

        webScrapper = new WebScrapper(downloadPath, username, password);

        JSONArray content = webScrapper.downloadJSONArray();

        Date startDate = webScrapper.getStartDate();
        HashMap<String, Role> roleMap = execute.initialize(startDate, content);

        ArrayList<ChartFrame> chartFrames = GraphPlotter.getAllFrames(roleMap);

        GUIHandler.setGraphs(chartFrames);
    }

    //TODO: Modify end date
    //TODO: modify employee number
}
