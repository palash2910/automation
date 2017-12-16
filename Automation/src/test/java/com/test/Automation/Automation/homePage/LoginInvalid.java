package com.test.Automation.Automation.homePage;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.Automation.Automation.testBase.TestBase;
import com.test.Automation.Automation.uiActions.HomePage;

public class LoginInvalid extends TestBase{
	
	public static final Logger log = Logger.getLogger(LoginInvalid.class.getName());	
	HomePage homepage;

	
	@DataProvider(name="loginData")
	public String[][] getTestData()
	{
		String[][] testRecords=getData("TestData.xlsx","LoginTestData");
		return testRecords;
	}
	
	
	@BeforeTest
	public void setup() throws IOException
	{
		init();
	}
	
	@Test(dataProvider="loginData")	
	public void verifyLogin(String emailAddress, String password, String runMode)
	{
		if(runMode.equalsIgnoreCase("n"))
		{
			throw new SkipException("Not to be in run");
		}
		log.info("starting test");
		homepage = new HomePage(driver);
		homepage.loginToApplication(emailAddress, password);
		getScreenShot("testLogin."+emailAddress);
		Assert.assertEquals(homepage.getInvalidLoginText(), "Authentication failed.");
		log.info("Finished test");
		
	}
	
	/**@AfterClass
	public void endTest()
	{
		driver.close();
	}
	**/


}
