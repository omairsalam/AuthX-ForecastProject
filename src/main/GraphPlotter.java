package main;
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

public class GraphPlotter {
		
	/**
	 * Converts the roleMap into a format readable by the plotting method
	 * @param roleMap The hashmap that contains all the details for the execute, role and project class 
	 */
	public static HashMap<String, TimeSeriesCollection> getFormattedData(HashMap<String, Role> roleMap){
		
		//Create a collection of graph sets which store the graphs for each role type (i.e. FED's etc)
		HashMap<String, TimeSeriesCollection> graphSets = new HashMap<String, TimeSeriesCollection>();

		//Iterate through Roles in the role map
		for (String tag : roleMap.keySet()){
			System.out.println("Tag: " + tag);
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			
			//Add a particular dataset for one graph to this graph set 
			graphSets.put(tag, dataset);

			Role role = roleMap.get(tag);
			HashMap<String, ProjectType> projectTypeMap = role.getPmap();

			//Iterate through ProjectTypes in this role
			for (String projectCode : projectTypeMap.keySet()){
				System.out.println("    Project Code: " + projectCode);

				//Scaling the map for the number of employees
				double employeeNumber = role.getEmp_Set().size();
				HashMap<Date, Double> weekMapEmployees = projectTypeMap.get(projectCode).scaleMap(employeeNumber);
				
				//Make a time series for this project 
				TimeSeries pop = new TimeSeries(projectCode, Day.class);

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
					
					//Add a point to the time series
					pop.add(new Day(day, month, year), employeeHours);
				}
				dataset.addSeries(pop);
			}
			
		}
		return graphSets;
	}
	
	
	public static ArrayList<ChartFrame> createFrameList(HashMap<String, TimeSeriesCollection> graphSet){
		
		ArrayList<ChartFrame> myFrameList = new ArrayList<ChartFrame>();
		for(String tag: graphSet.keySet()){
			System.out.println("Tag is " + tag);
		}
		
		for (String tag : graphSet.keySet()){
			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					tag,
					 "Week",
					 "# Of Employees Required",
					 graphSet.get(tag),
					 true,
					 true,
					 false);

			ChartFrame frame = new ChartFrame("Internal Admin Graphs", chart);
			myFrameList.add(frame);
			frame.pack();
			frame.setVisible(true);

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
		return myFrameList;
	}
	
	public static ArrayList<ChartFrame> getAllFrames(HashMap<String, Role> myDataSet){
		
		HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(myDataSet);
        return createFrameList(graphSets);
	}
	



	public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException, InterruptedException, JSONException {
		
		String dir = "/Users/user/Documents";
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

        HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(execute.getRoleMap());
        createFrameList(graphSets);
        
		
			
	}
}
