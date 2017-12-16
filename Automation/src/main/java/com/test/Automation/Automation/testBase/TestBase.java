package com.test.Automation.Automation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.Automation.Automation.excelReader.Read;

public class TestBase {
	
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	
	public WebDriverEventListener eventListener;
	Properties OR;

	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	public static WebDriver driver;
	String driverpath="C:\\Users\\Palash Dharwa\\Desktop\\Selenium\\chromedriver.exe";
	
	Read read;
	
	public void init() throws IOException
	{
		loadData();
		extent=new ExtentReports(System.getProperty("user.dir")+"\\Automation\\src\\main\\java\\com\\test\\Automation\\Automation\\report\\test.html",false);
		selectBrowser(OR.getProperty("browser"));
		getUrl(OR.getProperty("url"));
		String log4jfile="log4j.properties";
		PropertyConfigurator.configure(log4jfile);
		
	}
	
	public void selectBrowser(String browser)
	{
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", driverpath);
			log.info("creating object of browser");
			driver=new ChromeDriver();
		}
			
	}
	public void getUrl(String url)
	{
		log.info("Navigating to:"+url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String[][] getData(String excelName, String sheetName)
	{
		
		
		String path=System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\Automation\\Automation\\data\\"+excelName;
		read=new Read(path);
		String data[][] = read.getDataFromSheet(sheetName, excelName);
		return data;
	}
	
	public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\com\\test\\Automation\\Automation\\screenShot\\";
			File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadData() throws IOException {
	 OR=new Properties();
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\test\\Automation\\Automation\\config\\config.properties");
		FileInputStream f = new FileInputStream(file);
		OR.load(f);

	}
	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\com\\test\\Automation\\Automation\\screenShot\\";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
	
	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}

	public void closeBrowser() {
		//driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}
	
}
