package com.demoproject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demoproject.actions.SendKeysActionHelper;
import com.demoproject.actions.ValidationActionHelper;
import com.demoproject.actions.WebActionHelper;

public class LoginPage {
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	WebDriver driver;
	WebActionHelper wahObj = new WebActionHelper();
	ValidationActionHelper vahObj = new ValidationActionHelper();
	SendKeysActionHelper skahObj = new SendKeysActionHelper();
	
	@FindBy(id="user-name")
	WebElement uname;
	@FindBy(id="password")
	WebElement password;
	@FindBy(id="login-button")
	WebElement loginBtn;
	@FindBy(xpath="//div//h3[@data-test='error']")
	WebElement errorContainer;
	@FindBy(xpath="//span[text()='Products']")
	WebElement homePageHeading;
	
	public String getURL() {
		return wahObj.getSiteURL(driver);
	}

	public boolean isUserNameFieldDisplayed() throws Exception {
		return vahObj.isElementVisible(driver, uname);
	}

	public boolean isPasswordFieldDisplayed() throws Exception {
		return vahObj.isElementVisible(driver, password);
	}

	public boolean isLoginBtnDisplayed() throws Exception {
		return vahObj.isElementVisible(driver, loginBtn);
	}

	public String getUnamePlaceholder() {
		return uname.getAttribute("placeholder");
	}

	public String getPasswordPlaceholder() {
		return password.getAttribute("placeholder");
	}

	public boolean checkLoginBtnText(String expected) {
		String actual = loginBtn.getAttribute("value");
		if(actual.equalsIgnoreCase(expected))
			return true;
		else
			return false;
	}

	public void clickLogin(String username, String pwd) throws Exception {
		skahObj.clearTextAndSendKeys(driver, uname, username);
		skahObj.clearTextAndSendKeys(driver, password, pwd);
		loginBtn.click();
		
	}

	
	public String getActualErrorMessage() {
		return errorContainer.getText();
	}

	public boolean isHomePageDisplayed() throws Exception {
		return vahObj.isElementVisible(driver, homePageHeading);
	}

	

}
