import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @author user This is the ProjectType class with a variable type of Map. It links number-of-weeks -> associated hours.
 * The class defines two methods namely addtoMap(Date date,Double hours) which accumulate the no.of hrs of that particular week.
 * Another method is scaledMap(Double no_of_emps) which scaled the graph as per input parameters.
 *
 */

public class ProjectType {

	HashMap<Date, Double> hmap = new HashMap<Date,Double>();
	
	
	/**
	 * This  defines the getters and setters for the Map type variable.
	 * @return
	 */
	public HashMap<Date, Double> getHmap() {
		return hmap;
	}



	public void setHmap(HashMap<Date, Double> hmap) {
		this.hmap = hmap;
	}


/**
 * This method accumulate the no.of hrs of that particular week selected by the user.
 * @param date
 * @param hours
 */
	public void addtoMap(Date date,Double hours){
		
	}
	
	/**
	 * This method scaled the graph as per input parameter. The number of employees given are scaled with their working hours.
	 * The method returns the  key-Value pair of(Date,Double) type
	 * @param n
	 * @return
	 */
	public HashMap<Date,Double> scaleMap(Double n){
		return hmap;
		
	}
}
