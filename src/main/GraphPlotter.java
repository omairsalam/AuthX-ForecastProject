package main;


import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import junit.framework.Test;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;

public class GraphPlotter {


    private static final Color COLOR_LIKELY = new Color(237, 126, 49);
    private static final Color COLOR_HIGHLIKELY = new Color(112, 173, 71);
    private static final Color COLOR_SIGNEDATRISK = new Color(68, 114, 196);
    private static final Color COLOR_INTERNALPROJECTS = new Color(112, 48, 160);

    /**
     * Converts the roleMap into a format readable by the plotting method
     *
     * @param roleMap The hashmap that contains all the details for the execute,
     * role and project class
     * @return Returns a hasmap which represents the tag (or role name) as a key
     * and the time series collection i.e. works-hours as a value
     */
    public static HashMap<String, TimeSeriesCollection> getFormattedData(HashMap<String, Role> roleMap) {

        //Create a collection of graph sets which store the graphs for each role type (i.e. FED's etc)
        HashMap<String, TimeSeriesCollection> graphSets = new HashMap<String, TimeSeriesCollection>();

        //Iterate through Roles in the role map
        for (String tag : roleMap.keySet()) {
            System.out.println("Tag: " + tag);
            TimeSeriesCollection dataset = new TimeSeriesCollection();

            //Add a particular dataset for one graph to this graph set 
            graphSets.put(tag, dataset);

            Role role = roleMap.get(tag);
            HashMap<String, ProjectType> projectTypeMap = role.getPmap();
            
            if (tag.equals("BED")){
                System.out.println("Employees in BED are: " + role.getEmp_Set());
            }

            //Iterate through ProjectTypes in this role
            for (String projectCode : projectTypeMap.keySet()) {
                //Add a filter for the project codes that we actually need to see 
                if ( projectCode.equals("Internal Projects") || projectCode.equals("High Likely") || projectCode.equals("Likely") || projectCode.equals("Signed")) {
                    System.out.println("    Project Label: " + projectCode);

                    //Scaling the map for the number of employees
                    double employeeNumber = role.getNumEmployees();
                    
                    HashMap<Date, Double> weekMapEmployees = projectTypeMap.get(projectCode).scaleMap(employeeNumber);

                    //Make a time series for this project 
                    TimeSeries pop; 
                    if (projectCode.equals("Signed")){ //We need to change the label for signed and highly likely 
                         pop = new TimeSeries("Signed + At Risk", Day.class);
                    }else{
                         pop = new TimeSeries(projectCode, Day.class);
                    }

                    //Goes through the weeks -> hours and builds up the points into a list
                    for (Date weekDate : weekMapEmployees.keySet()) {
                        double employeeHours = weekMapEmployees.get(weekDate);

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(weekDate);
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH) + 1;
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        /*if (tag.equals("BED") && projectCode.equals("Signed")){
                            System.out.println("        Date: " + day + " " + month + " " + year + " Hours: " + employeeHours);
                         }*/
                        

                        //Omair: Do the plotting here
                        //X = Day, month, years
                        //Y = employeeHours
                        //Add a point to the time series
                        //System.out.print("Day is " + new Day(day, month, year).toString());
                        //System.out.print(" and empHours is " + employeeHours + '\n');
                        pop.add(new Day(day, month, year), employeeHours);
                    }
                    dataset.addSeries(pop);
                }
            }

        }
        //printOut(roleMap);
        return graphSets;
    }

    /**
     * Takes a hashmap of essentially a tag or a role name and its respective
     * time series, which is the data of day-hours for every project and returns
     * an array list of all the charts corresponding to these roles
     *
     * @param graphSet The Hash map that contains work-hours wMap for each
     * project for each role
     * @return An array list of charts for each role
     */
    public static ArrayList<JFreeChart> createFrameList(HashMap<String, TimeSeriesCollection> graphSet) {

        ArrayList<JFreeChart> myChartList = new ArrayList<JFreeChart>();
        for (String tag : graphSet.keySet()) {
            System.out.println("Tag is " + tag);
        }

        for (String tag : graphSet.keySet()) {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    tag,
                    "Week",
                    "# Of Employees Required",
                    graphSet.get(tag),
                    true,
                    true,
                    false);
            
            
            //Sets background color of chart to white
            //chart.setBackgroundPaint(Color.RED);
            chart.getPlot().setBackgroundPaint(new Color(225,225,225));
            chart.getPlot().setOutlineVisible(true);
            //chart.setBackgroundImageAlpha(0.2f);
            chart.setBorderPaint(Color.BLACK);
            
            //Get all charts as XY Plots
            XYPlot plot = (XYPlot) chart.getPlot();
            
            //Change the background colors and the grid line colors 
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setRangeGridlinePaint(Color.BLACK);
            plot.setBackgroundPaint(Color.WHITE);
            
            //Iterate through each line to make it thicker 
            int seriesCount = plot.getSeriesCount();

            //Iterate through the series
            for (int i = 0; i < seriesCount; i++) {

                //Increase thickness of each stroke
                plot.getRenderer().setSeriesStroke(i, new BasicStroke(2)); 

            }
            
            //Add a horizontal marker
            ValueMarker marker = new ValueMarker(0);  // position is the value on the axis
            marker.setStroke(new BasicStroke(3)); //make the line thicker 
            marker.setPaint(Color.black); //make the color black 
            plot.addRangeMarker(marker);

            //These gets the indexes of each of the project code lines
            //If the project code isn't found, it returns -1.
            int indexLikely = plot.getDataset(0).indexOf("Likely");
            int indexHighLikely = plot.getDataset(0).indexOf("High Likely");
            int indexInternalProjects = plot.getDataset(0).indexOf("Internal Projects");
            int indexSignedAtRisk = plot.getDataset(0).indexOf("Signed + At Risk");

            //These lines set the color of the project code lines based on the indexes you found above
            if (indexLikely != -1) { plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(indexLikely, COLOR_LIKELY); }
            if (indexHighLikely != -1) {
                plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(indexHighLikely, COLOR_HIGHLIKELY);
                //plot.getRendererForDataset(plot.getDataset(0)).setSeriesVisible(indexHighLikely, false); //This is the way you set the visibility of a series
            }
            if (indexSignedAtRisk != -1) { plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(indexSignedAtRisk, COLOR_SIGNEDATRISK); }
            if (indexInternalProjects != -1) { plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(indexInternalProjects, COLOR_INTERNALPROJECTS); }



            //ChartFrame frame = new ChartFrame("Internal Admin Graphs", chart);
            myChartList.add(chart);
            //frame.pack();
            //frame.setVisible(true);

            /*
			try {
				 ChartUtilities.saveChartAsPNG(new File("/Users/user/Documents/chart1.png"), chart, 500, 300);
				// create and display a frame...



				 } catch (IOException e) {
				 System.err.println("Problem occurred creating chart.");
				 }
             */
            //TODO: Implement this exporting, do not hardcode it
        }
        return myChartList;
    }

    /**
     * Gets all chart frames from a specific roleMap
     *
     * @param roleMap The roleMap that contains all the roles and the respective
     * projects etc
     * @return A list of all the charts (i.e. one chart for each role)
     */
    public static ArrayList<JFreeChart> getAllFrames(HashMap<String, Role> roleMap) {
        HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(roleMap);
        return createFrameList(graphSets);
    }

    /**
     * Takes a tag and the number of employees and rescales a particular role
     *
     * @param tag The key for the role
     * @param roleMap A hashmap that contains the role objects with their tag names as keys 
     * @param numEmployees The new number of employees we want to use
     * @return A new hashmap with the scaled data
     */
    public static HashMap<String, Role> rescale(HashMap<String, Role> roleMap, String tag, int numEmployees) {

        HashMap<String, Role> scaledRoleMap = new HashMap<String, Role>();

        roleMap.get(tag).setNumEmployees(numEmployees);

        for (String roleName : roleMap.keySet()) {
            Role role = roleMap.get(roleName);
            Role roleClone = role.clone();

            scaledRoleMap.put(roleName, roleClone);
        }

        return scaledRoleMap;
    }

    /**
     * Takes a particular role and rescales its week-hours map
     *
     * @param currentRole The role that we want to rescale
     * @param numEmployees The number of employees which we want to use as a
     * rescale factor
     */
    public static void rescaleRole(Role currentRole, int numEmployees) {
        if (currentRole == null) {
            System.err.println("There was no role for this tag!");
        }
        HashMap<String, ProjectType> projects = currentRole.getPmap();
        for (String pCode : projects.keySet()) {
            HashMap<Date, Double> wMap = projects.get(pCode).getWMap();
            for (Date key : wMap.keySet()) {
                wMap.put(key, (wMap.get(key) - (5 * 8 * numEmployees)) / 8);
            }
        }

    }
    
    /**
     * Prints out all the contents of a roleMap in the hierarchy of Role - Project - (Date, Hours)  
     * @param roleMap The hashmap that we want to print out 
     */
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

    

    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException, InterruptedException, JSONException {

        String dir = "/Users/alam/Documents";
        WebScrapper webScrapper = new WebScrapper(dir, "theoriginalsine@gmail.com", "forecast");

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

        //HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(execute.getRoleMap());
        //createFrameList(graphSets);
    }
}
