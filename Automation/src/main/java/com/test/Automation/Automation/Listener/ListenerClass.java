package com.test.Automation.Automation.Listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.test.Automation.Automation.testBase.TestBase;

public class ListenerClass extends TestBase implements ITestListener{

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult arg0) {
		
		if(!result.isSuccess())
		{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			try {
				String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\com\\test\\Automation\\Automation\\screenShot\\";
				File destFile = new File((String) reportDirectory + "hello "+ "_" + formater.format(calendar.getTime()) + ".png");
				FileUtils.copyFile(scrFile, destFile);
				// This will help us to link the screen shot in testNG report
				Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		
	}

}
