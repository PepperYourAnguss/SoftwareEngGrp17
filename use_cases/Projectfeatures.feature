Feature: Create a project or edit the details of an existing project.
	Description: 
	Actor: Administrator and Project Leader

Scenario: Create a project
	Given the user is logged in as an administrator
	When a project with name "name" ID "ID1234" start date "4/12/2020" end date "4/2/2021" and estimated time used "40" is created 
	Then the project is created
##
Scenario: Project is finished
	Given a project with ID "ID1234" exists
	And the user ID "TestUser" matches project leader ID
	And the project state is "Completed"
	When the user sets the project name to "New Name"
	Then the user gets the error message "Completed projects cannot be altered"
	
Scenario: Project is not finished
	Given a project with ID "ID1234" exists
	And the user ID "TestUser" matches project leader ID
	And the project state is "Incomplete"
	When the user sets the project name to "NewName"
	Then the project name is updated to "NewName"
#	
Scenario: Non-project leader attempts to edit project details
	Given a project with ID "ID1234" exists
	And the user ID "NewUser" does not match the project leader ID
	When the user sets the project name to "BadName"
	And the user gets the error message "User is not project leader"