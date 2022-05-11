package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartEmptyPage extends BasePage {

	public CartEmptyPage(WebDriver driver) {
		super(driver);
	}

	By buttonAcceptCookie = By.id("onetrust-accept-btn-handler");
	By buttonAddToCart = By.cssSelector("#js-zootopiaRecosEmpty button");
	private By labelRecommnededProducts = By
			.xpath("//div[@class='custom__slider']//h3[contains(text(),'Need some inspiration?')]");

	public String acceptCookies() {
		try {
			click(buttonAcceptCookie);
			return "Success";
		} catch (Exception e) {
			return "Cookie setting not available -" + e;
		}
	}

	public ShoppingBasketPage addProductFromRecommended() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			// List<WebElement> elementName = driver.findElements(buttonAddToCart);
			List<WebElement> elementName = getAllElements(buttonAddToCart);
			int s = elementName.size();
			System.out.println(s);
			for (WebElement w : elementName) {

				// js.executeScript("arguments[0].scrollIntoView();", w);
				if (w.isDisplayed() == true) {
					click(w);
					break;
				}
			}

			return new ShoppingBasketPage(driver);
		} catch (Exception e) {
			return null;
		}
	}


	public boolean waitForEmptyCartPageLoad() {

		try {
			waitForElement(labelRecommnededProducts);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
