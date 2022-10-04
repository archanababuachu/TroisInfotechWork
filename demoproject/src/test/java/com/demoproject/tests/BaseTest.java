package com.demoproject.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.demoproject.actions.WebActionHelper;
import com.demoproject.base.AutomationBase;
import com.demoproject.datahandler.PropertyDataHandler;


public class BaseTest {

	public static WebDriver driver;
	
	@Parameters("browserType")
	@BeforeTest
	public void launch(String browserType) throws IOException {
		
		AutomationBase autoObj = new AutomationBase();
		PropertyDataHandler prop = new PropertyDataHandler();
		WebActionHelper wahObj = new WebActionHelper();
		
		driver = autoObj.initializeDriver(browserType);
		
		Properties allProp = prop.readPropertiesFile("config.properties");
		wahObj.launchURL(driver, allProp.getProperty("url"));
	}
	
	@AfterTest
	public void quit() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		driver.quit();
	}
}
