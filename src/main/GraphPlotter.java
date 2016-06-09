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
	
	private HashMap<String, Role> roleMap = new HashMap<String,Role>(); 
		
	/**
	 * Converts the roleMap into a format readable by the plotting method
	 * @param roleMap The hashmap that contains all the details for the execute, role and project class 
	 * @return Returns a hasmap which represents the tag (or role name) as a key and the time series collection i.e. works-hours as a value 
	 */
	public HashMap<String, TimeSeriesCollection> getFormattedData(HashMap<String, Role> roleMap){
		
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
	
	
	/**
	 * Takes a hashmap of essentially a tag or a role name and its respective time series, which is the data of day-hours for every project and returns an array
	 * list of all the charts corresponding to these roles
	 * @param graphSet The Hash map that contains work-hours wMap for each project for each role 
	 * @return An array list of charts for each role 
	 */
	public ArrayList<JFreeChart> createFrameList(HashMap<String, TimeSeriesCollection> graphSet){
		
		ArrayList<JFreeChart> myChartList = new ArrayList<JFreeChart>();
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
	 * @param myDataSet The roleMap that contains all the roles and the respective projects etc
	 * @return A list of all the charts (i.e. one chart for each role) 
	 */
	public ArrayList<JFreeChart> getAllFrames(HashMap<String, Role> myDataSet){
		roleMap = myDataSet; 
		HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(roleMap);
        return createFrameList(graphSets);
	}
	
	/**
	 * Takes a tag and the number of employees and rescales a particular role 
	 * @param tag The key for the role 
	 * @param numEmployees The new number of employees we want to use
	 * @return A new hashmap with the scaled data 
	 */
	public HashMap<String, Role> rescale(String tag, int numEmployees){
		rescaleRole(roleMap.get(tag), numEmployees); 
		return roleMap; 
	}
	
	/**
	 * Takes a particular role and rescales its week-hours map
	 * @param currentRole The role that we want to rescale 
	 * @param numEmployees The number of employees which we want to use as a rescale factor 
	 */
	public void rescaleRole(Role currentRole, int numEmployees){
		if (currentRole == null){
			System.err.println("There was no role for this tag!");
		}
		HashMap<String, ProjectType> projects = currentRole.getPmap();
		for (String pCode: projects.keySet()){
			HashMap<Date,Double> wMap = projects.get(pCode).getWMap();
			for(Date key:wMap.keySet()){
				wMap.put(key,(wMap.get(key)-(5*8*numEmployees))/8);
			}
		}
		
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
        

        //HashMap<String, TimeSeriesCollection> graphSets = getFormattedData(execute.getRoleMap());
        //createFrameList(graphSets);
        
		
			
	}
}
