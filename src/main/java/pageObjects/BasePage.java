package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);

	}

	public void waitTillPageIsLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		wait.until(pageLoadCondition);
	}

	public void click(By elementBy) {
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			driver.findElement(elementBy).click();
		} catch (Exception e) {
			throw e;
		}
	}

	public void click(WebElement elementToClick) {
		
		try {
			elementToClick.click();

		} catch (Exception e) {
			throw e;
		}
	}

	public void waitForElement(By elementBy) {
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));

		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	public void waitForElement(WebElement element) {
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));

		} 
		catch (Exception e) {
			throw e;
		}
	}

	public List<WebElement> getAllElements(By elementBy) {
		
		try {
			List<WebElement> elementName = driver.findElements(elementBy);
			return elementName;
		} catch (Exception e) {
			return null;
		}
		
	}

	// Overloaded method to get child elements from a parent element
	public List<WebElement> getAllElements(By parentBy, By childBy) {
		
		try {
			WebElement parentElement = driver.findElement(parentBy);
			List<WebElement> elementName = parentElement.findElements(childBy);
			return elementName;
		} catch (Exception e) {
			return null;
		}
		
	}

	public String getText(By elementBy) {
		
		String strText = "";
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			strText = driver.findElement(elementBy).getText();

		} catch (Exception e) {
			return strText;
		}
		return strText;
	}

	public void enterText(By elementBy, String textToType) {
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			driver.findElement(elementBy).sendKeys(textToType);

		} catch (Exception e) {
			throw e;
		}
	}

	public void srollToElement(By elementBy) {
		
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", driver.findElement(elementBy));
		} catch (Exception e) {
			throw e;
		}
	}
}
