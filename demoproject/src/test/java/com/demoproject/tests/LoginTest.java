package com.demoproject.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.demoproject.pages.LoginPage;
import com.demoproject.datahandler.ExcelDataHandler;
import com.demoproject.datahandler.PropertyDataHandler;

public class LoginTest extends BaseTest{
	
	LoginPage lpage;
	SoftAssert soft;
	PropertyDataHandler prop = new PropertyDataHandler();
	Properties allProp;
	ExcelDataHandler excelObj;
	
	@BeforeMethod
	public void invokeLoginPage() {
		lpage = new LoginPage(driver);
	}
	
	/*
	 * Verify that the user is able to launch the URL
	 */
	@Test(priority = 0, enabled = true)
	public void verifyExpectedURLisLaunched() throws IOException{
		soft = new SoftAssert();
		allProp = prop.readPropertiesFile("config.properties");
		soft.assertEquals(lpage.getURL(), allProp.getProperty("url"), "Wrong URL Launched");
		soft.assertAll();
	}
	/*
	 * Verify all fields are displayed on the login page (User Name, Password, Login Button, Heading, Logo)
	 */
	@Test(priority = 1, enabled = true)
	public void validateLoginPageFieldsAreDisplayed() throws Exception {
		soft = new SoftAssert();
		soft.assertTrue(lpage.isUserNameFieldDisplayed(), "User Name field is not displayed");
		soft.assertTrue(lpage.isPasswordFieldDisplayed(), "Password field is not displayed");
		soft.assertTrue(lpage.isLoginBtnDisplayed(), "Login Button is not displayed");
		soft.assertAll();
	}
	/*
	 * verify attribute values
	 */
	@Test(priority = 2, enabled = true)
	public void verifyLoginPageAttributeValues() throws Exception {
		soft = new SoftAssert();
		excelObj = new ExcelDataHandler("ProjectExcel.xlsx", "Login");
		soft.assertEquals(lpage.getUnamePlaceholder(), excelObj.getCellDataString(2, 2), "User name placeholder is not valid");
		soft.assertEquals(lpage.getPasswordPlaceholder(), excelObj.getCellDataString(3, 2), "User name placeholder is not valid");
		soft.assertTrue(lpage.checkLoginBtnText(excelObj.getCellDataString(4, 2)), "Login Button text is not valid");
		soft.assertAll();
	}
	/*
	 * Verify invalid login
	 */
	@Test(priority = 3, dataProvider = "Credentials", enabled = true)
	public void validateAllInvalidLogin(String uname, String pwd, String expectedError) throws Exception {
		
		soft = new SoftAssert();
		lpage.clickLogin(uname, pwd);
		soft.assertEquals(lpage.getActualErrorMessage(), expectedError);
		soft.assertAll();
	}
	/*
	 * Verify valid login
	 */
	@Test(priority = 4,  enabled = true)
	public void verifyValidLogin() throws Exception {
		
		soft = new SoftAssert();
		Properties allProp = prop.readPropertiesFile("config.properties");
		lpage.clickLogin(allProp.getProperty("username"), allProp.getProperty("password"));
		soft.assertTrue(lpage.isHomePageDisplayed(), "Login is not successful");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		soft.assertAll();
	}
	
	

	@DataProvider(name = "Credentials")
	public Object[][] getData(){
		Object[][] data= {  
	               {"","", "Epic sadface: Username is required"},  //empty login
	               {"standard_use","secret_sauc", "Epic sadface: Username and password do not match any user in this service"}, //invalid uname, invalid pwd
	               {"standard_us","secret_sauce", "Epic sadface: Username and password do not match any user in this service"}, //invalid uname, valid pwd
	               {"standard_user","secret_sauc", "Epic sadface: Username and password do not match any user in this service"} //valid uname, invalid pwd
	      };
		return data;
	}

}
