package business_logic;

import java.util.ArrayList;

import business_logic.Management.userType;

/*
 * @author Andreas Handberg
 */

public class Employee extends User {

	public ArrayList<Project> leaderOfProjects;
	private ArrayList<Activity> memberOfActivities;
	
	public Employee(String employeeID, String employeePass) {
		super(employeeID, employeePass, userType.Employee);
		leaderOfProjects = new ArrayList<Project>();
		memberOfActivities = new ArrayList<Activity>();	
	}
	
	public void addActivityToEmployee(Activity act) throws Exception {
		if(!this.getMemberOfActivities().contains(act)) {
			memberOfActivities.add(act);
		}
		else {
			throw new OperationNotAllowedException("This employee is already part of this activity");
		}
	
	}	
	
	public void removeActivityFromEmployee(Activity act) {
		if(memberOfActivities.contains(act)) {
			memberOfActivities.remove(act);
		}
	}

	public ArrayList<Project> getLeaderOfProjects() {
		return leaderOfProjects;
	}

	public ArrayList<Activity> getMemberOfActivities() {
		return memberOfActivities;
	}
	
	public boolean isBusy() {
		return memberOfActivities.size() >= 20;
	}
}
