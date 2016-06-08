package main;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class LinePlot {
		
	/**
	 * Converts the roleMap into a format readable by the plotting method
	 */
	public void createPointLists(HashMap<String, Role> roleMap){

		//Iterate through Roles in the role map
		for (String roleName : roleMap.keySet()){
			System.out.println("Role: " + roleName);

			Role role = roleMap.get(roleName);
			HashMap<String, ProjectType> projectTypeMap = role.getPmap();

			//Iterate through ProjectTypes in this role
			for (String projectCode : projectTypeMap.keySet()){
				System.out.println("    Project Code: " + projectCode);

				//Scaling the map for the number of employees
				double employeeNumber = role.getEmp_Set().size();
				HashMap<Date, Double> weekMapEmployees = projectTypeMap.get(projectCode).scaleMap(employeeNumber);

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
				}
			}
		}
	}


	public static void main(String[] args) {
			
		
		// Create a time series chart
		 TimeSeries pop = new TimeSeries("Highly Likely", Day.class);
		 pop.add(new Day(10, 9, 2004), 100);
		 pop.add(new Day(10, 2, 2004), 150);
		 pop.add(new Day(10, 3, 2004), 250);
		 pop.add(new Day(10, 4, 2004), 275);
		 pop.add(new Day(10, 5, 2004), 325);
		 pop.add(new Day(10, 6, 2004), 425);
		 TimeSeriesCollection dataset = new TimeSeriesCollection();
		 dataset.addSeries(pop);
		 
		 TimeSeries pop2 = new TimeSeries("Likely", Day.class);
		 pop2.add(new Day(20, 1, 2004), 200);
		 pop2.add(new Day(20, 2, 2004), 250);
		 pop2.add(new Day(20, 3, 2004), 450);
		 pop2.add(new Day(20, 4, 2004), 475);
		 pop2.add(new Day(20, 5, 2004), 125);
		 dataset.addSeries(pop2);
		 
		 
		 JFreeChart chart = ChartFactory.createTimeSeriesChart(
		 "Front-End Developer",
		 "Week",
		 "# Of Employees Required",
		 dataset,
		 true,
		 true,
		 false);

		 try {
		 ChartUtilities.saveChartAsPNG(new File("/Users/alam/Documents/chart1.png"), chart, 500, 300);
		// create and display a frame...
	        ChartFrame frame = new ChartFrame("Internal Admin Graphs - Front End Developer", chart);
	        frame.pack();
	        frame.setVisible(true);

		 } catch (IOException e) {
		 System.err.println("Problem occurred creating chart.");
		 }
		 
		 
	}
}
