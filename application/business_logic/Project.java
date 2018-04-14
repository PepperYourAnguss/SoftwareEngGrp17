package business_logic;

import java.util.ArrayList;
import java.util.Date;

import business_logic.Management.userType;

public class Project {
	ArrayList<Activity> Activities;
	String projectname = "";
	String projectID = "";
	String adminID = "";
	String adminPass = "";
	private String projectLeaderID = "";
	Date startDate = null;
	Date endDate = null;
	int estimatedTimeUsage;

	public Project(String projectname, String projectID, Date startDate, Date endDate, String adminID, String adminPass,
			int estimatedTimeUsage) throws FailedLoginException {
		/*
		 * There needs to be a check that the person who's calling the project is an
		 * admin. this.adminID = adminID; this.adminPass = adminPass;
		 * Login.AdminLogin(adminID, adminPass);
		 */
		this.projectname = projectname;
		this.projectID = projectID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.estimatedTimeUsage = estimatedTimeUsage;

		Activities = new ArrayList<Activity>();
	}

	public void EditProjectDetails(String projectID) {

	}

	public void SetProjectLeader(String employeeID, String adminID, String adminPass, String projectID) {

	}

	public String getProjectLeaderID() {
		return projectLeaderID;
	}
}