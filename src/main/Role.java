package main;
import sun.jvm.hotspot.tools.PMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;


/**
 * 
 * @author user This is the Role class with two variables type of Map and ArrayList.
 * The Map variable defines the key-value pair of particular role Type -> (associated with) the particular projectType.
 * The list defines the number of employees. 
 *
 */
public class Role {

	HashMap<String, ProjectType> pMap = new HashMap<String,ProjectType>();
        private double numEmployees;

	/**
	 * 
	 * @return This  defines the getters and setters for the Map type variable . 
	 */
	public HashMap<String, ProjectType> getPmap() {
		return pMap;
	}

	public void setPmap(HashMap<String, ProjectType> pMap) {
		this.pMap = pMap;
	}
	

	
	LinkedHashSet<String> emp_set = new LinkedHashSet<String>();

    /**
     *
     * @return Returns the number of employees for this role
     */
	public double getNumEmployees(){
		return numEmployees;
	}

	public void setNumEmployees(double numEmployees){
		this.numEmployees = numEmployees;
	}

	/**
	 * 
	 * @return  This  defines the getters and setters for the employee list.
	 */
	

	public LinkedHashSet<String> getEmp_Set() {
		return emp_set;
	}

	public void setEmp_Set(LinkedHashSet<String> emp_list) {
		this.emp_set = emp_set;
	}


	public Role clone(){
		Role clone = new Role();

		clone.setNumEmployees(this.getNumEmployees());
		clone.setEmp_Set(this.getEmp_Set());

		for (String projectKey : pMap.keySet()){
			clone.getPmap().put(projectKey, pMap.get(projectKey).clone());
		}

		return clone;
	}

}
