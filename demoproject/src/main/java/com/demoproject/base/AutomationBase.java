package com.demoproject.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationBase {

static WebDriver driver;

	
	/**
	 * method to intialize driver	
	 * @return 
	 */
	public WebDriver initializeDriver(String browserType) {
		
		 
		try {
			switch (browserType) {
			case "chrome": 
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();

			default:
				break;
			}
		}catch (Exception e) {
			System.out.println("Launch Exception (WebActionHelper) : "+e.getMessage());
		}
		return driver;
	}
	
	public static WebDriver getDriver() {
		return driver;
	}

}
