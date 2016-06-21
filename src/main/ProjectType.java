package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This is the ProjectType class with a variable type of Map. It
 * links number-of-weeks - associated hours. The class defines two methods
 * namely addtoMap(Date date,Double hours) which accumulate the no.of hrs of
 * that particular week. Another method is scaledMap(Double no_of_emps) which
 * scaled the graph as per input parameters.
 * @author user 
 *
 */
public class ProjectType {

    HashMap<Date, Double> wMap = new HashMap<Date, Double>();

    /**
     * Gets the week-hours map for this class
     * @return The week-hours map 
     */
    public HashMap<Date, Double> getWMap() {
        return wMap;
    }

    /**
     * Sets the week-hours map for this class
     * @param wMap The map which we want to set as this map 
     */
    public void setWMap(HashMap<Date, Double> wMap) {
        this.wMap = wMap;
    }

    /**
     * This method accumulate the no.of hrs of that particular week selected by
     * the user.
     *
     * @param date The Monday of that week
     * @param hours The hours worked that week
     */
    public void addtoMap(Date date, Double hours) {
        if (wMap.containsKey(date)) {
            Double oldHours = wMap.get(date);
            hours += oldHours;
        }
        wMap.put(date, hours);
    }

    /**
     * This method scales the graph as per input parameter. The number of
     * employees given are scaled with their working hours. The method returns
     * the key-Value pair of(Date,Double) type
     *
     * @param n The number by which we want to scale the map 
     * @return The scaled hash map 
     */
    public HashMap<Date, Double> scaleMap(Double n) {

        /**
         * This makes the deep clone of the HashMap named as wmap that link the
         * particular week -> hours
         */
        HashMap<Date, Double> scaledMap = new HashMap<Date, Double>();

        //Clones week map into scaled map
        for (Date dateKey : this.getWMap().keySet()) {
            scaledMap.put(dateKey, this.getWMap().get(dateKey));
        }

        //Scales the cloned map for number of necessary employee
        for (Date key : scaledMap.keySet()) {
            double numberNeeded = scaledMap.get(key) / 32;
            //numberNeeded*=-1;
            scaledMap.put(key, (numberNeeded - n));
        }

        return scaledMap;

    }

    /**
     * Clones a hashmap by manually copying all its contents to another hashmap
     *
     * @return A deep copy of the hashmap to be copied
     */
    public ProjectType clone() {
        ProjectType clone = new ProjectType();

        HashMap<Date, Double> wMapClone = new HashMap<Date, Double>();

        for (Date date : this.wMap.keySet()) {
            Double hours = wMap.get(date);
            wMapClone.put(date, hours);
        }

        clone.setWMap(wMapClone);
        return clone;
    }
}
