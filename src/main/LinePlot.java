package main;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;

import java.io.File;
import java.io.IOException;

public class LinePlot {
		



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
