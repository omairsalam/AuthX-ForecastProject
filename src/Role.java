import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author user This is the Role class with two variables type of Map and ArrayList.
 * The Map variable defines the key-value pair of particular role Type -> (associated with) the particular projectType.
 * The list defines the number of employees. 
 *
 */
public class Role {

	HashMap<String, ProjectType> pmap = new HashMap<String,ProjectType>();

	/**
	 * 
	 * @return This  defines the getters and setters for the Map type variable . 
	 */
	public HashMap<String, ProjectType> getPmap() {
		return pmap;
	}

	public void setPmap(HashMap<String, ProjectType> pmap) {
		this.pmap = pmap;
	}
	

	
	ArrayList<String> emp_list = new ArrayList<String>();
	/**
	 * 
	 * @return  This  defines the getters and setters for the employee list.
	 */
	

	public ArrayList<String> getEmp_list() {
		return emp_list;
	}

	public void setEmp_list(ArrayList<String> emp_list) {
		this.emp_list = emp_list;
	}


}
