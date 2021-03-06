package cuke.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import business_logic.Admin;
import business_logic.Employee;
import business_logic.Management;
import business_logic.OperationNotAllowedException;
import cucumber.api.java.en.*;

/*
 * @author Carsten Andersen
 */

public class CreateProjectSteps {
	ErrorHandler errorHandler;
	Employee testEmployee;
	Admin testAdmin;
	Management management;
//	public <managementApp> CreateProjectSteps(managementApp timeManagementApp) {
//		this.managementApp = managementApp;
	public CreateProjectSteps(Management management, ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		this.management = management;
	}
	@Given("^the user is logged in as an administrator$")
	public void theUserIsLoggedInAsAnAdministrator() throws Exception {
	    if(!management.adminIsLoggedIn()) {
	    	management.logUserOut();
	    	management.userLogin("admin", "adminadmin");
	    }
	    assertTrue(management.getLoggedInUser().hasAdminPermissions());
	}

	@When("^a project with name \"([^\"]*)\" ID \"([^\"]*)\" start date \"([^\"]*)\" end date \"([^\"]*)\" and estimated time used \"([^\"]*)\" is created$")
	public void aProjectWithNameIDStartDateEndDateAndEstimatedTimeUsedIsCreated(String arg1, String arg2, String arg3, String arg4, String arg5) throws Exception {
	    SimpleDateFormat DateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	   int estimatedTime = Integer.parseInt(arg5);
	   Date startDate = DateFormatter.parse(arg3);
	   Date endDate = DateFormatter.parse(arg4);
		
		management.createProject(arg1, arg2, startDate, endDate, estimatedTime);
	}

	@Then("^the project is created$")
	public void theProjectIsCreated() throws Exception {
	    assertTrue(management.getListOfProjects().contains(management.getProjectByID("ID1234")));
	}

	@Given("^a project with ID \"([^\"]*)\" exists$")
	public void aProjectWithIdExists(String arg1) throws Exception {
		if(!management.getListOfProjects().contains(management.getProjectByID(arg1))) {
			if(!management.adminIsLoggedIn()) {
				management.logUserOut();
				management.userLogin("admin", "adminadmin");
			}
		SimpleDateFormat DateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		int estimatedTime = Integer.parseInt("40");
		Date startDate = DateFormatter.parse("05/11/2020");
		Date endDate = DateFormatter.parse("10/11/2020");
			
		management.createProject("name", arg1, startDate, endDate, estimatedTime);
		}
		assertTrue(management.getListOfProjects().contains(management.getProjectByID(arg1)));
	}
	
	@When("^the user sets the project name to \"([^\"]*)\"$")
	public void theUserSetsTheProjectNameTo(String arg1) throws Exception {
		try {
			management.getProjectByID("ID1234").setProjectName(arg1);
		} catch (OperationNotAllowedException e) {
			
			errorHandler.errorMessage= e.getMessage();
		}
	}

	@Then("^the project name is updated to \"([^\"]*)\"$")
	public void theProjectNameIsUpdated(String arg1) throws Exception {
		assertTrue(management.getProjectByID("ID1234").getProjectName() == arg1);
	}
	
	@When("^the adminstrator removes the project with the ID \"([^\"]*)\"$")
	public void theAdminstratorRemovesTheProjectWithTheID(String arg1) throws Exception {
	    management.removeProject(management.getProjectByID(arg1));
	}
	
	@Then("^the project is removed from the list of projects$")
	public void theProjectIsRemovedFromTheListOfProjects() throws Exception {
	    assertFalse(management.getListOfProjects().contains(errorHandler.testProject));
	}


	@Given("^the user ID \"([^\"]*)\" does not match the project leader ID$")
	public void theUserIDDoesNotMatchTheProjectLeaderID(String arg1) throws Exception {
		testEmployee = new Employee(arg1,"newpass");
		management.addUser(testEmployee);
		if(management.getProjectByID("ID1234").getProjectLeaderID() == management.getLoggedInUserID()) {
			management.logUserOut();
			management.userLogin(arg1, "newpass");
		}
		try {
		management.getProjectByID("ID1234").setProjectName("BadName");
		}
		catch(OperationNotAllowedException e) {
	    	errorHandler.errorMessage = e.getMessage();
	    }
	}

	@Then("^the user gets the error message \"([^\"]*)\"$")
	public void theUserGetsTheErrorMessage(String arg1) throws Exception {
		assertTrue(errorHandler.errorMessage.equals(arg1));

	}

	@Given("^the project does not have a leader$")
	public void theProjectDoesNotHaveALeader() throws Exception {
	    assertTrue(management.getProjectByID("ID1234").getProjectLeaderID() == "");
	}

	@When("^the admin designates an employee to be project leader$")
	public void theAdminDesignatesAnEmployeeToBeProjectLeader() throws Exception {
	    testEmployee = new Employee("testID", "testPass");
	    management.addUser(testEmployee);
	    management.getProjectByID("ID1234").setProjectLeader("testID");
	}

	@Then("^the employee becomes project leader for the project$")
	public void theEmployeeBecomesProjectLeaderForTheProject() throws Exception {
	    assertTrue(management.getProjectByID("ID1234").getProjectLeaderID() == "testID");
	}
}
