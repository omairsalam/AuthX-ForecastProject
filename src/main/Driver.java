package main;

import org.jfree.chart.ChartFrame;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 6/8/16.
 */
public class Driver {


    /**
     * Sets the roleMap of this class
     * @throws java.text.ParseException 
     */
    public static void main(String[] args) throws InterruptedException, ParseException, JSONException, IOException, java.text.ParseException {

        String downloadPath = args[0];
        String username = args[1];
        String password = args[2];

        WebScrapper webScrapper = new WebScrapper(downloadPath, username, password);

        JSONArray content = webScrapper.downloadJSONArray();

        Execute execute = new Execute();

        Date startDate = webScrapper.getStartDate();
        HashMap<String, Role> roleMap = execute.initialize(startDate, content);

        ArrayList<ChartFrame> chartFrames = GraphPlotter.getAllFrames(roleMap);

        GUIHandler.makeGraphs(chartFrames);
    }

    //TODO: Modify end date
    //TODO: modify employee number
}
