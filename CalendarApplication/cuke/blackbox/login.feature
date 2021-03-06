Feature: Admin login
	Description: The administrator logs into the system and also logs out
	Actor: Administrator or employee
	
Scenario: Administrator can login
	Given a user is not logged in
	And an adminstrator exists
	When the adminstrator enters the ID "admin" and the password "adminadmin"
	Then the adminstrator is logged in
	
Scenario: Administrator has the wrong password
	Given a user is not logged in
	And an adminstrator exists
	When the adminstrator enters the ID "admin" and the password "wrong password"
	Then the administrator is not logged in
	And the user gets the error message "incorrect ID or password"

Scenario:
	Given an adminstrator exists
	And an adminstrator is logged in
	And an employee exists with the ID "test" and the password "password"
	When the adminstrator removes the user with the ID "test"
	Then the user is removed from the list of users
	
Scenario: Employee can login
	Given a user is not logged in
	And an employee exists with the ID "test" and the password "password"
	When the employee enters the ID "test" and the password "password"
	Then the employee is logged in
	
Scenario: Employee has the wrong password
	Given a user is not logged in
	And an employee exists with the ID "test" and the password "password"
	When the employee enters the ID "test" and the password "wrong password"
	Then the employee is not logged in
	And the user gets the error message "incorrect ID or password"
	
Scenario: User wants to log out
	Given a user exists
	And a user is logged in
	When the user logs out
	Then the user is no longer logged in
	
Scenario: User logging in while another user is already logged in
	Given an employee exists with the ID "test" and the password "password"
	And a user is logged in
	When the employee enters the ID "test" and the password "password"
	Then the user gets the error message "another user is already logged in"
	And the employee is not logged in
