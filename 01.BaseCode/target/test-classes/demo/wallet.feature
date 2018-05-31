Feature: Create an account

Scenario: User wants to create an account with valid data
Given user accesses create account module
When  I give "laveena" as a name,"9664140119" as a mobileNumber,2000 as a amount
Then System should create Account with name as "laveena",mobileNumber as "9664140119",amount as 2000

Scenario: User tries to create an account with invalid mobilenumber
Given  user wants to create an account
When  I give "Neha" as a name,"95678999100" as a mobileNumber,1000 as a amount
Then System should give error message as "Invalid mobile number"

Scenario: User tries to create an account with negative amount
Given   user wants to create an account
When I give "RK" as name, "9674632276" as a mobileNumber,"-900" as a amount
Then System should give message "Invalid Amount"

Scenario: User tried to invoke showBalance module
Given   user tried to invoke showBalance module
When I give "9674632276" as mobileNumber
Then System should return customer with mobileNumber as "9674632276"

Scenario: User tried to invoke showBalance module
Given   user tried to invoke showBalance module
When I give "" as mobileNumber
Then System should give error message as "No mobile number entered"

Scenario: User tried to find customer with a mobile number but customer details were not found
Given   user tried to invoke showBalance module
When I give "9674632234" as mobileNumber
Then System should give error message as "Customer Not Found"

Scenario: User tried to deposit amount with mobile number
Given   user tried to invoke deposit module with initial balance 1000
When I give "9674632276" as mobileNumber and 1000 as amount
Then System should return customer with mobile number as "9674632276" amount as 2000.

Scenario: User tried to deposit amount without entering mobile number
Given   user tried to invoke deposit module with initial balance 1000
When I give "" as mobileNumber and 1000 as amount
Then System should give error message as "No mobile number entered"

Scenario: User tried to deposit amount with mobile number and without entering amount
Given   user tried to invoke deposit module with initial balance 1000
When I give "9674632276" as mobileNumber and "" as amount
Then System should give error message as "Invalid Amount"

Scenario: User tried to deposit amount with mobile number and negative amount
Given   user tried to invoke deposit module with initial balance 1000
When I give "9674632276" as mobileNumber and "-1000" as amount
Then System should give error message as "Invalid Amount"

Scenario: User tried to invoke fund transfer method
Given   user tried to invoke fundtransfer module with one account having balance 2000 and the other 1000
When I give "9674632276" as mobileNumber of creditor and "9674632290" as mobileNumber of debitor and "1000" as amount
Then System should return customer with mobile number as "9674632276" amount as 1000.

Scenario: User tried to invoke fund transfer method without specifying taker
Given   user tried to invoke fundtransfer module with one account having balance 2000 and the other 1000
When I give "" as mobileNumber of creditor and "9674632290" as mobileNumber of debitor and "1000" as amount
Then System should give error message as "No mobile number entered"

Scenario: User tried to invoke fund transfer method without specifying taker
Given   user tried to invoke fundtransfer module with one account having balance 2000 and the other 1000
When I give "9674632276" as mobileNumber of creditor and "" as mobileNumber of debitor and "1000" as amount
Then System should give error message as "No mobile number entered"

Scenario: User tried to invoke fund transfer method without entering amount
Given   user tried to invoke fundtransfer module with one account having balance 2000 and the other 1000
When I give "9674632276" as mobileNumber of creditor and "9674632290" as mobileNumber of debitor and "" as amount
Then System should give error message as "Invalid Amount"

Scenario: User tried to invoke fund transfer method and amount as negative
Given   user tried to invoke fundtransfer module with one account having balance 2000 and the other 1000
When I give "9674632276" as mobileNumber of creditor and "9674632290" as mobileNumber of debitor and "-1000" as amount
Then System should give error message as "Invalid Amount"

Scenario: User tried to invoke fund transfer method with amount greater than giver balance
Given   user tried to invoke fundtransfer with one account having balance 2000 and the other 1000
When I give "9674632276" as mobileNumber of creditor and "9674632290" as mobileNumber of debitor and "3000" as the amount
Then System should give error message as "Invalid Amount"