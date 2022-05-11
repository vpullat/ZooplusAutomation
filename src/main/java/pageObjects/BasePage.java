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

	public WebDriver driver;
	public WebDriverWait wait;

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

		// WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(pageLoadCondition);
	}

	public void click(By elementBy) {
		// waitVisibility(elementBy);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			driver.findElement(elementBy).click();
		} catch (Exception e) {
			throw e;
		}
	}

	public void click(WebElement elementToClick) {
		// waitVisibility(elementBy);
		try {

			wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
			elementToClick.click();

		} catch (Exception e) {
			throw e;
		}
	}

	public void waitForElement(By elementBy) {
		// waitVisibility(elementBy);
		try {

			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			// elementToClick.click();

		} catch (Exception e) {
			throw e;
		}
	}

	public List<WebElement> getAllElements(By elementBy) {
		// waitVisibility(elementBy);
		try {

			List<WebElement> elementName = driver.findElements(elementBy);
			return elementName;
			// elementToClick.click();

		} catch (Exception e) {
			throw e;
		}
	}

	public String getText(By elementBy) {
		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			String strText = driver.findElement(elementBy).getText();
			return strText;

		} catch (Exception e) {
			throw e;
		}
	}

	public String enterText(By elementBy, String textToType) {
		try {

			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			driver.findElement(elementBy).sendKeys(textToType);
			return textToType;

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
