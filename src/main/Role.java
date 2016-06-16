package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 *  This is the Role class with two variables type of Map and
 * ArrayList. The Map variable defines the key-value pair of particular role
 * Type -> (associated with) the particular projectType. The list defines the
 * number of employees.
 * @author user 
 *
 */
public class Role {

    HashMap<String, ProjectType> pMap = new HashMap<String, ProjectType>();
    private double numEmployees;
    LinkedHashSet<String> emp_set = new LinkedHashSet<String>();

    /**
     * Get the project map of project code to project objects
     *
     * @return The project hashmap
     */
    public HashMap<String, ProjectType> getPmap() {
        return pMap;
    }

    /**
     * Set the project hashmap
     *
     * @param pMap The hashmap which we want to set as THIS hashmap
     */
    public void setPmap(HashMap<String, ProjectType> pMap) {
        this.pMap = pMap;
    }

    /**
     * Finds the number of employees for a particular role object
     * @return The number of employees
     */
    public double getNumEmployees() {
        return numEmployees;
    }

    /**
     * Sets the number of employees to a specific double value
     * @param numEmployees The number which we want to set as the new number of employees
     */
    public void setNumEmployees(double numEmployees) {
        this.numEmployees = numEmployees;
    }

    /**
     * Gets the list of employee names for a particular role 
     * @return The employee name set 
     */
    public LinkedHashSet<String> getEmp_Set() {
        return emp_set;
    }

    /**
     * Sets the list of employees for a particular role
     * @param emp_list The employee name set which we want to set as THIS employee set 
     */
    public void setEmp_Set(LinkedHashSet<String> emp_list) {
        this.emp_set = emp_set;
    }

    /**
     * Makes a deep copy of a Role
     * @return The deep copy of the role 
     */
    public Role clone() {
        Role clone = new Role();

        clone.setNumEmployees(this.getNumEmployees());
        clone.setEmp_Set(this.getEmp_Set());

        for (String projectKey : pMap.keySet()) {
            clone.getPmap().put(projectKey, pMap.get(projectKey).clone());
        }

        return clone;
    }

}
