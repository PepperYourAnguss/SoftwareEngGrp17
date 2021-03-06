package cuke.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import business_logic.Admin;
import business_logic.Employee;
import business_logic.FailedLoginException;
import business_logic.Management;
import business_logic.User;
import cucumber.api.java.en.*;

/*
 * @author Andreas Handberg
 */

public class LoginSteps {
	
	ErrorHandler errorHandler;
	Admin testAdmin;
	Management management;
	
	public LoginSteps(ErrorHandler errorHandler, Management management) {
		this.errorHandler = errorHandler;
		this.management = management;
	}
	
	@Given("^a user is logged in$")
	public void aUserIsLoggedIn() throws Exception {
		if(!management.userIsLoggedIn()) {
			management.userLogin("admin", "adminadmin");
		}
	    assertTrue(management.userIsLoggedIn());
	}
	
	@Given("^an adminstrator is logged in$")
	public void anAdminstratorIsLoggedIn() throws Exception {
		management.logUserOut();
	    management.userLogin("admin", "adminadmin");
	}
	
	@Given("^a user is not logged in$")
	public void aUserIsNotLoggedIn() throws Exception {
		management.logUserOut();
	    assertFalse(management.userIsLoggedIn());
	}
	
	@Given("^an employee with the ID \"([^\"]*)\" and the password \"([^\"]*)\" is logged in$")
	public void anEmployeeWithTheIDAndThePasswordIsLoggedIn(String ID, String password) throws Exception {
		if(management.userIsLoggedIn()) {
			management.logUserOut();
		}
	    management.userLogin(ID, password);//TODO: Code smell - this should be Id and password. Same for remainder part of this class.
	}
	
	@Given("^an adminstrator exists$")
	public void anAdminstratorExists() throws Exception {
	    if(management.getAdmins().size() == 0) {
	    	testAdmin = (Admin) management.addUser(new Admin());
	    }
	    assertTrue(management.getAdmins().size() > 0);
	}
	
	@Given("^an employee exists with the ID \"([^\"]*)\" and the password \"([^\"]*)\"$")
	public void anEmployeeExistsWithTheIDAndThePassword(String ID, String password) throws Exception {
	    if(management.getUserByID(ID) == null) {
	    	User user = management.getLoggedInUser();
	    	management.logUserOut();
	    	management.userLogin("admin", "adminadmin");
	    	errorHandler.testEmployee = (Employee) management.addUser(new Employee(ID, password));
	    	management.logUserOut();
	    	if(user != null) {
	    		management.userLogin(user.getUserID(), user.getPassword());
	    	}   	
	    }
	    assertTrue(management.getEmployees().size() > 0);
	}
	
	@Given("^a user exists$")
	public void aUserExists() throws Exception {
		anEmployeeExistsWithTheIDAndThePassword("test", "password");
		anAdminstratorExists();
	}

	@When("^the adminstrator enters the ID \"([^\"]*)\" and the password \"([^\"]*)\"$")
	public void theAdminstratorEntersThePasswordAndTheID(String ID, String password) throws Exception {
		try {
			management.userLogin(ID, password);
		}
		catch(FailedLoginException e) {
			errorHandler.errorMessage = e.getMessage();
		}
	}
	
	@When("^the user logs out$")
	public void theUserLogsOut() throws Exception {
		management.logUserOut();
	}
	
	@When("^the employee enters the ID \"([^\"]*)\" and the password \"([^\"]*)\"$")
	public void theEmployeeEntersThePasswordAndTheID(String ID, String password) throws Exception {
	    try {
	    	management.userLogin(ID, password);
	    }
	    catch(FailedLoginException e) {
	    	errorHandler.errorMessage = e.getMessage();
	    }
	}
	
	@When("^the adminstrator removes the user with the ID \"([^\"]*)\"$")
	public void theAdminstratorRemovesTheUserWithTheID(String ID) throws Exception {
	    management.removeUser(management.getUserByID(ID));
	}

	@Then("^the adminstrator is logged in$")
	public void theAdminstratorIsLoggedIn() throws Exception {	
		assertTrue(management.adminIsLoggedIn());
	}
	
	@Then("^the administrator is not logged in$")
	public void theAdministratorIsNotLoggedIn() throws Exception {
		assertTrue(management.getLoggedInUserID() == null || !management.getLoggedInUserID().equals(testAdmin.getUserID()));
	}
	
	@Then("^the user is removed from the list of users$")
	public void theUserIsRemovedFromTheListOfUsers() throws Exception {
	    assertFalse(management.getUsers().contains(errorHandler.testEmployee));
	}
	
	@Then("^the user is no longer logged in$")
	public void theUserIsNoLongerLoggedIn() throws Exception {
	    assertFalse(management.userIsLoggedIn());
	}
	
	@Then("^the employee is not logged in$")
	public void theEmployeeIsNotLoggedIn() throws Exception {
	    assertTrue(management.getLoggedInUserID() == null || !management.getLoggedInUserID().equals(errorHandler.testEmployee.getUserID()));
	}
	
	@Then("^the employee is logged in$")
	public void theEmployeeIsLoggedIn() throws Exception {
	    assertTrue(management.employeeIsLoggedIn());
	    assertTrue(management.getLoggedInUserID().equals(errorHandler.testEmployee.getUserID()));
	}	
}
