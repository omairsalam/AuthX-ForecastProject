package main;

import org.jfree.chart.ChartFrame;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 * Created by user on 6/8/16.
 */
public class GUIHandler {

    /**
     * Sets all the graphs in the GUI
     * @param chartFrames the chart frames to set
     */
    public static void setGraphs(ArrayList<ChartFrame> chartFrames){
        //TODO: Put the graphs into the GUI
    }
    /**
     * Sets a specific graphs in the GUI
     * @param roleName the name of the role corresponding to the chart frame to set
     * @param chartFrame the chartFrame to set
     */
    public void setGraph(String roleName, ChartFrame chartFrame){
        //TODO: Ability to set a specific graph
    }
    /**
     * Displays the login screen
     */
    public void displayLogin(){
        //TODO: Display the login screen
    }
    /**
     * Triggers when the employee number changes
     */
    public void onNumEmployeeChanged(){
        //TODO: Call the driver method to rescale a specific frame when the employee number is changed
        String roleName = "";
        int employeeNumber = 0;
        Driver.rescaleMap(roleName, employeeNumber);
    }
    /**
     * Triggers when the login button is clicked
     */
    public void onLoginButtonClicked() throws InterruptedException, JSONException, ParseException, org.json.simple.parser.ParseException, IOException {
        String userName = "";
        String password = "";
        String downloadPath = "";

        Driver.login(userName, password, downloadPath);
    }
}
