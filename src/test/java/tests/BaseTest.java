package tests;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.CartEmptyPage;
import utilityPackage.UtilityClass;

import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class BaseTest {

	protected WebDriver driver;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		Properties objProperties=null;
		String appURL = null;

		// Open the browser and navigate to zooplus.com
		try {
			objProperties = UtilityClass.readConfigFile();
			appURL = objProperties.getProperty("url");
		} catch (Exception e) {
			appURL = "https://www.zooplus.com/checkout/overview";
		}
		driver.get(appURL);
		driver.manage().window().maximize();

		CartEmptyPage p = new CartEmptyPage(driver);
		String strCookieSetting = p.acceptCookies();
		p.waitTillPageIsLoaded(driver);

		if (strCookieSetting != "Success") {
			System.out.println("Accepting cookies failed");
		}
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}
}
