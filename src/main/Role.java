package main;
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
	private int numEmployees;

    /**
     *
     * @return Returns the number of employees for this role
     */
	public int getNumEmployees(){
		return numEmployees;
	}

	public void setNumEmployees(int numEmployees){
		this.numEmployees = numEmployees;
	}

	/**
	 * 
	 * @return  This  defines the getters and setters for the employee list.
	 */
	

	public LinkedHashSet<String> getEmp_Set() {
		return emp_set;
	}

	public void setEmp_List(ArrayList<String> emp_list) {
		this.emp_set = emp_set;
	}


}
