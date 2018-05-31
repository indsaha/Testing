package demo;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.beans.Customer;
import demo.exception.InvalidInputException;
import demo.repo.WalletRepoImpl;
import demo.service.WalletService;
import demo.service.WalletServiceImpl;

public class AppTest {
	private WalletService service;
	private Customer result;
	private String errorMessage;
	@Given("^user accesses create account module$")
	public void user_accesses_create_account_module() throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
    }
	@When("^I give \"([^\"]*)\" as a name,\"([^\"]*)\" as a mobileNumber,(\\d+) as a amount$")
	public void i_give_as_a_name_as_a_mobileNumber_as_a_amount(String arg1, String arg2, int arg3) throws Exception {
		
			if(arg2.length() > 10) {
				try {
					service.createAccount(arg1, arg2, new BigDecimal(arg3));

				} 
				catch(InvalidInputException e)
				{
					errorMessage = e.getMessage();
				}
			}
		else {
			result= service.createAccount(arg1, arg2, new BigDecimal(arg3));
		}
	}

	@Then("^System should create Account with name as \"([^\"]*)\",mobileNumber as \"([^\"]*)\",amount as (\\d+)$")
	public void system_should_create_Account_with_name_as_mobileNumber_as_amount_as(String arg1, String arg2, int arg3) throws Exception {
		assertEquals(arg1,result.getName()); 
		assertEquals(arg2,result.getMobileNo()); 
		assertEquals(new BigDecimal(arg3),result.getWallet().getBalance()); 
	}
	
	
	@Given("^user wants to create an account$")
	public void user_wants_to_create_an_account() throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
	}
	
	@When("^I give \"([^\"]*)\" as name, \"([^\"]*)\" as a mobileNumber,\"([^\"]*)\" as a amount$")
	public void i_give_as_name_as_a_mobileNumber_as_a_amount(String arg1, String arg2, String arg3) throws Exception {
		if(Integer.parseInt(arg3) < 0)
		{
			try
			{
				service.createAccount(arg1, arg2, new BigDecimal(arg3));
			}
			catch(InvalidInputException e)
			{
				errorMessage = e.getMessage();
			}
		}
		else{
			result= service.createAccount(arg1, arg2, new BigDecimal(arg3));
		}
	}


	
	@Then("^System should give error message as \"([^\"]*)\"$")
	public void system_should_give_error_message_as(String arg1) throws Exception {
		assertEquals(errorMessage,arg1); 
	}
	
	@Then("^System should give message \"([^\"]*)\"$")
	public void system_should_give_message(String arg1) throws Exception {
		assertEquals(errorMessage,arg1); 
	}
	
	//showBalance
	
	@Given("^user tried to invoke showBalance module$")
	public void user_tried_to_invoke_showBalance_module() throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
	}

	@When("^I give \"([^\"]*)\" as mobileNumber$")
	public void i_give_as_mobileNumber(String arg1) throws Exception {
	    if(arg1.length() == 0)
	    {
	    	try {
	    		service.createAccount("RK", "9674632276",new BigDecimal(1000));
				service.showBalance(arg1);
			} 
			catch(InvalidInputException e)
			{
				errorMessage = e.getMessage();
			}
	    }
	    else
	    {
	    	try {
	    		service.createAccount("RK", "9674632276",new BigDecimal(1000));
				result = service.showBalance(arg1);
			} 
			catch(InvalidInputException e)
			{
				errorMessage = e.getMessage();
			}
	    }
	}

	@Then("^System should return customer with mobileNumber as \"([^\"]*)\"$")
	public void system_should_return_customer_with_mobileNumber_as(String arg1) throws Exception {
		assertEquals(arg1,result.getMobileNo()); 
		}
	
	@Then("^System should return message as \"([^\"]*)\"$")
	public void system_should_return_message_as(String arg1) throws Exception {
		assertEquals(errorMessage,arg1); 
	}

	//deposit
	
	@Given("^user tried to invoke deposit module with initial balance (\\d+)$")
	public void user_tried_to_invoke_deposit_module_with_initial_balance(int arg1) throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
        service.createAccount("RK", "9674632276", new BigDecimal(arg1));
	}

	@When("^I give \"([^\"]*)\" as mobileNumber and (\\d+) as amount$")
	public void i_give_as_mobileNumber_and_as_amount(String arg1, int arg2) throws Exception {
	  	try {
		    		result = service.depositAmount(arg1, new BigDecimal(arg2));
				} 
				catch(InvalidInputException e)
				{
					errorMessage = e.getMessage();
				}
	  }

	@When("^I give \"([^\"]*)\" as mobileNumber and \"([^\"]*)\" as amount$")
	public void i_give_as_mobileNumber_and_as_amount(String arg1, String arg2) throws Exception {
		  if(arg1.length() == 0)
		    {
		    	try {
		    		service.depositAmount(arg1, new BigDecimal(arg2));
				} 
				catch(InvalidInputException e)
				{
					errorMessage = e.getMessage();
				}
		    }
		  else
			  if(arg2.length() == 0)
			    {
			    	try {
			    		int	arg = 0;
			    		service.depositAmount(arg1, new BigDecimal(arg));
					} 
					catch(InvalidInputException e)
					{
						errorMessage = e.getMessage();
					}
			    }
			  else
				  if(Integer.parseInt(arg2) < 0 )
				    {
				    	try {
				    		service.depositAmount(arg1, new BigDecimal(arg2));
						} 
						catch(InvalidInputException e)
						{
							errorMessage = e.getMessage();
						}
				    }

	}

	
	@Then("^System should return customer with mobile number as \"([^\"]*)\" amount as (\\d+)\\.$")
	public void system_should_return_customer_with_mobile_number_as_amount_as(String arg1, int arg2) throws Exception {
		assertEquals(arg1,result.getMobileNo()); 
		assertEquals(arg2,result.getWallet().getBalance().intValue());
	}

	@Given("^user tried to invoke fundtransfer module with one account having balance (\\d+) and the other (\\d+)$")
	public void user_tried_to_invoke_fundtransfer_module_with_one_account_having_balance_and_the_other(int arg1, int arg2) throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
        service.createAccount("RK", "9674632276", new BigDecimal(arg1));
        service.createAccount("Coffee", "9674632290", new BigDecimal(arg2));
	}
	
	@Given("^user tried to invoke fundtransfer with one account having balance (\\d+) and the other (\\d+)$")
	public void user_tried_to_invoke_fundtransfer_with_one_account_having_balance_and_the_other(int arg1, int arg2) throws Exception {
		service = new WalletServiceImpl(new WalletRepoImpl(new HashMap<String,Customer>()));
        result = service.createAccount("RK", "9674632276", new BigDecimal(arg1));
        service.createAccount("Coffee", "9674632290", new BigDecimal(arg2));
	}


	@When("^I give \"([^\"]*)\" as mobileNumber of creditor and \"([^\"]*)\" as mobileNumber of debitor and \"([^\"]*)\" as the amount$")
	public void i_give_as_mobileNumber_of_creditor_and_as_mobileNumber_of_debitor_and_as_the_amount(String arg1, String arg2, String arg3) throws Exception {
		  if(Integer.parseInt(arg3) > result.getWallet().getBalance().intValue() )
		    {
		    	try {
		    		service.fundTransfer(arg1, arg2, new BigDecimal(arg3));
				} 
				catch(InvalidInputException e)
				{
					errorMessage = e.getMessage();
				}
		    }
     }
	@When("^I give \"([^\"]*)\" as mobileNumber of creditor and \"([^\"]*)\" as mobileNumber of debitor and \"([^\"]*)\" as amount$")
	public void i_give_as_mobileNumber_of_creditor_and_as_mobileNumber_of_debitor_and_as_amount(String arg1, String arg2, String arg3) throws Exception {
		 if(arg1.length() == 0 || arg2.length() == 0)
		    {
		    	try {
		    		service.fundTransfer(arg1, arg2, new BigDecimal(arg3));
				} 
				catch(InvalidInputException e)
				{
					errorMessage = e.getMessage();
				}
		    }
		 
		  else
			  if(arg3.length() == 0)
			    {
			    	try {
			    		int	arg = 0;
			    		service.fundTransfer(arg1, arg2, new BigDecimal(arg));
					} 
					catch(InvalidInputException e)
					{
						errorMessage = e.getMessage();
					}
			    }
			  else
				  if(Integer.parseInt(arg3) < 0 )
				    {
				    	try {
				    		service.fundTransfer(arg1, arg2, new BigDecimal(arg3));
						} 
						catch(InvalidInputException e)
						{
							errorMessage = e.getMessage();
						}
				    }
				  else
				  {
					  try {
						    result = null;
				    		result = service.fundTransfer(arg1, arg2, new BigDecimal(arg3));
						} 
						catch(InvalidInputException e)
						{
							errorMessage = e.getMessage();
						}  
				  }
	}
	

	
}
