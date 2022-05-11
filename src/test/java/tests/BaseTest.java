package tests;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.CartEmptyPage;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class BaseTest {
	
	public WebDriver driver;
  
  @BeforeClass
  public void setup () {
	  WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		By signInButtonBy = By.className("btnSignIn");
		
		// Navigate to the demoqa website
		driver.get("https://www.zooplus.com/checkout/overview");
      //Maximize Window
      driver.manage().window().maximize();
      
      CartEmptyPage p = new CartEmptyPage(driver);
      String strCookieSetting = p.acceptCookies();
      p.waitTillPageIsLoaded(driver);
      
      if(strCookieSetting!= "Success") {
    	  System.out.println("Accepting cookies failed");
      }
  }

  @AfterClass
	  public void teardown () {
	        driver.quit();
	    }
  }

