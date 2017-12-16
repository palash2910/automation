package com.test.Automation.Automation.uiActions;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.Automation.Automation.homePage.LoginInvalid;
import com.test.Automation.Automation.testBase.TestBase;

public class HomePage extends TestBase {
	
	public static final Logger log = Logger.getLogger(HomePage.class.getName());	
	
	WebDriver driver;
	@FindBy(xpath="//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")
	WebElement signIn;
	
	@FindBy(xpath="//*[@id=\"email\"]")
	WebElement loginEmailAddress;
	
	@FindBy(xpath="//*[@id=\"passwd\"]")
	WebElement loginPassword;
	
	@FindBy(xpath="//*[@id=\"SubmitLogin\"]/span")
	WebElement submitButton;
	
	
	@FindBy(xpath="//*[@id=\"center_column\"]/div[1]/ol/li")
	WebElement authenticationFailed;
	
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginToApplication(String emailAddress, String password)
	{
		signIn.click();
		log.info("clicked on sign in"+signIn.toString()	);
		loginEmailAddress.sendKeys(emailAddress);
		loginPassword.sendKeys(password);
		submitButton.click();
	}
	
	public String getInvalidLoginText()
	{
		return authenticationFailed.getText();
	}
	


}
