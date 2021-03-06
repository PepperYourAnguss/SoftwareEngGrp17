
package business_logic;

import java.util.ArrayList;
import java.util.Date;

/*
 * @author Andreas Handberg
 */

public class Management {
	
	private User loggedInUser;
	private ArrayList<Project> listOfProjects = new ArrayList<Project>();
	private ArrayList<User> users = new ArrayList<User>();
	
	public Management() {
		users.add(new Admin());
	}
	
	public boolean userIsLoggedIn() {
		return loggedInUser != null;
	}
	
	public boolean adminIsLoggedIn() {
		return userIsLoggedIn() && loggedInUser.getTypeOfUser() == userType.Admin;
	}
	
	public boolean employeeIsLoggedIn() {
		return userIsLoggedIn() && loggedInUser.getTypeOfUser() == userType.Employee;
	}
	
	public Project createProject(String name, String ID, Date startDate, Date endDate, int estimatedTime) throws Exception { // creates a project and adds it to the management object

		if(adminIsLoggedIn()) {
			 assert getLoggedInUser().hasAdminPermissions();
			Project project = new Project(name,ID,startDate,endDate,estimatedTime);
			this.addProject(project);
			 assert project.getProjectName().equals(name) && project.getProjectID().equals(ID)
			 && project.getProjectStartDate().equals(startDate) && project.getProjectEndDate().equals(endDate)
			 && project.getEstimatedTimeUsed() == estimatedTime; //post
			 assert listOfProjects.contains(project);
			return project;

		}
		else {
			throw new OperationNotAllowedException("Insufficient permissions");
		}
	}
	
	public boolean userLogin(String ID, String password) throws FailedLoginException {
		if(userIsLoggedIn()) { 
			throw new FailedLoginException("another user is already logged in");
		}
		else {
			assert !userIsLoggedIn(); // pre
			User user = getUserByID(ID); 
			if(user != null && password.equals(user.getPassword())) { 
				assert getUserByID(ID).getPassword().equals(password); //pre
				setLoggedInUser(user); 
				assert userIsLoggedIn() && getLoggedInUserID().equals(ID); //post
				return true; 
			}
			else {
				throw new FailedLoginException("incorrect ID or password");
			}
		}
		
	}
	
	public void logUserOut() {
		setLoggedInUser(null);
	}
	
	public User addUser(User emp) throws OperationNotAllowedException {
		ArrayList<User> usersAtPre = new ArrayList<>(users);
		if(getUserByID(emp.getUserID()) == null) { 
			assert !getUsers().contains(emp); //pre
			if(adminIsLoggedIn()) { 
				assert getLoggedInUser().hasAdminPermissions(); //pre
				users.add(emp);			
				usersAtPre.add(emp);
				assert users.equals(usersAtPre); //post
			}
			else {
				throw new OperationNotAllowedException("Only an admin can add users");
			}
		}
		else {
			throw new OperationNotAllowedException("another user with that ID already exists");
		}
		return emp; 
	}
	
	
	public void removeUser(User user) throws OperationNotAllowedException {
		if(adminIsLoggedIn()) {
			users.remove(user);			
		}
		else {
			throw new OperationNotAllowedException("Only an admin can remove users");
		}
	}
	
	public Project addProject(Project pro) throws OperationNotAllowedException {
		if(getProjectByID(pro.getProjectID()) == null) {
			listOfProjects.add(pro);
			pro.management = this;
			return pro;
		}
		else {
			throw new OperationNotAllowedException("another project with that ID already exists");
		}	
	}
	
	public Project removeProject(Project pro) {
		listOfProjects.remove(pro);
		pro.management = null;
		return pro;
	}
	
	public enum userType{
		Admin,Employee; //all the types of users
	}
	
	public User getUserByID(String ID) { // returns the user if it exists. Otherwise returns null
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserID().equals(ID)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public Project getProjectByID(String ID) { // returns the project if it exists. Otherwise returns null
		for(int i = 0; i < listOfProjects.size(); i++) {
			if(listOfProjects.get(i).getProjectID().equals(ID)) {
				return listOfProjects.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Admin> getAdmins(){ // returns all users that are admins. does not return users that are employees
		ArrayList<Admin> admins = new ArrayList<Admin>();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getTypeOfUser() == userType.Admin) {
				admins.add((Admin) users.get(i));
			}
		}
		return admins;
	}
	
	public ArrayList<Employee> getEmployees(){ // returns all users that are employees. does not return users that are admins
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getTypeOfUser() == userType.Employee) {
				employees.add((Employee) users.get(i));
			}
		}
		return employees;
	}
		
	public userType getLoggedInUserType() {
		return userIsLoggedIn() ? getLoggedInUser().getTypeOfUser() : null;
	}

	public String getLoggedInUserID() {
		return userIsLoggedIn() ? getLoggedInUser().getUserID() : null;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Project> getListOfProjects() {
		return listOfProjects;
	}
	
	public User getLoggedInUser() {
		return loggedInUser;
	}
	public Employee getEmployeeByID(String ID) {
		Employee foundEmployee;
		for(int i = 0; i < getEmployees().size(); i++) {
			foundEmployee = getEmployees().get(i);	
			if(foundEmployee.getUserID().equals(ID))
				return foundEmployee;
		}
		
		return null;
	}

	private void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}


	public Employee requestAssistance(Activity activity) throws Exception { //@author Carsten Andersen/Mark Uttrup Ewing
		boolean help = false;
		Employee tempEmployee;
		for(int i = 0; i < getEmployees().size(); i++) {
			tempEmployee = getEmployees().get(i);
			if(!tempEmployee.isBusy() && !tempEmployee.checkAbsent()) {
				if(activity.getListOfEmployees().contains(tempEmployee)) {
				continue;
				}
				else {	
				help = true;
				activity.addEmployeeToActivity(tempEmployee);
				return tempEmployee;
				}
			}
		}
		throw new OperationNotAllowedException("There are no available employees");
	}
		
}