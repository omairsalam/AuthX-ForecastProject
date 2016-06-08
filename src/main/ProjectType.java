package main;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author user This is the ProjectType class with a variable type of Map. It links number-of-weeks -> associated hours.
 * The class defines two methods namely addtoMap(Date date,Double hours) which accumulate the no.of hrs of that particular week.
 * Another method is scaledMap(Double no_of_emps) which scaled the graph as per input parameters.
 *
 */

public class ProjectType {

	HashMap<Date, Double> wMap = new HashMap<Date,Double>();


	/**
	 * This  defines the getters and setters for the Map type variable.
	 * @return
	 */
	public HashMap<Date, Double> getWMap() {
		return wMap;
	}
	public void setWMap(HashMap<Date, Double> wMap) {
		this.wMap = wMap;
	}

/**
 * This method accumulate the no.of hrs of that particular week selected by the user.
 * @param date
 * @param hours
 */
	public void addtoMap(Date date,Double hours){
		if (wMap.containsKey(date)){
			double oldHours = wMap.get(date);
			hours += oldHours;
		}
		wMap.put(date, hours);
	}

	/**
	 * This method scaled the graph as per input parameter. The number of employees given are scaled with their working hours.
	 * The method returns the  key-Value pair of(Date,Double) type
	 * @param n
	 * @return
	 */
	public HashMap<Date,Double> scaleMap(Double n){

		/**
		 * This makes the deep clone of the HashMap named as wmap that link the particular week -> hours
		 */
		HashMap<Date,Double> scaledMap = (HashMap<Date, Double>) this.wMap.clone();

		for(Date key:scaledMap.keySet()){
			scaledMap.put(key,(scaledMap.get(key)-(5*8*n))/8);
		}
		return scaledMap;

		}


}
