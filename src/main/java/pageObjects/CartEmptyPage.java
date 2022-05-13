package pageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartEmptyPage extends BasePage {

	public CartEmptyPage(WebDriver driver) {
		super(driver);
	}

	private By buttonAcceptCookie = By.id("onetrust-accept-btn-handler");
	private By buttonAddToCart = By.cssSelector("#js-zootopiaRecosEmpty button");
	private By labelRecommnededProducts = By
			.xpath("//div[@class='custom__slider']//h3[contains(text(),'Need some inspiration?')]");

	public String acceptCookies() {
		try {
			click(buttonAcceptCookie);
		} catch (Exception e) {
			return "Cookie setting not available -" + e;
		}
		return "Success";
	}

	public ShoppingBasketPage addProductFromRecommended() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		int intIterator = 0;
		int intAttempts = 0;

		try {
			List<WebElement> elementName = getAllElements(buttonAddToCart);

			while (elementName.size() == 0 && intAttempts < 5) {
				elementName = getAllElements(buttonAddToCart);
				intAttempts++;
			}

			intAttempts = 0;

			while (intIterator == 0 && intAttempts < 5) {
				for (WebElement w : elementName) {

					if (w.isDisplayed() == true) {
						click(w);
						intIterator++;
						break;
					}
				}
				intAttempts++;
			}
		} 
		catch (Exception e) {
			return null;
		}
		return new ShoppingBasketPage(driver);
	}

	public boolean waitForEmptyCartPageLoad() {

		try {
			waitForElement(labelRecommnededProducts);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
