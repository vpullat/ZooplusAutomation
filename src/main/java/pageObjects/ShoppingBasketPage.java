package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingBasketPage extends BasePage {

	private By labelPrice = By.xpath("//div[@role='cell' and @class='value two__column']");
	private By buttonAddMoreProducts = By.cssSelector("div#js-zootopiaRecos-1 button");
	private By gridRecommendedProducts = By.id("#js-zootopiaRecosContainer-1");

	public ShoppingBasketPage(WebDriver driver) {
		super(driver);
	}

	public boolean waitForShoppingBasketPageLoad() {

		try {
			waitForElement(labelPrice);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForRecommendedProductsLoad() {

		try {
			waitForElement(gridRecommendedProducts);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String addMoreProducts() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		int intIterator = 0;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List<WebElement> elementName = getAllElements(buttonAddMoreProducts);
			int s = elementName.size();
			System.out.println(s);
			for (WebElement w : elementName) {

				if (w.isDisplayed() == true) {
					click(w);
					break;
				}
			}

			return "Success";
		} catch (Exception e) {
			return "Failed";
		}
	}

	public List<String> getPricesFromCart() {
		
		List<String> listProductPrices = new ArrayList<>();
		List<WebElement> listItemPrice = getAllElements(labelPrice);
	//	List<String> st = elementName.stream().map(s -> s.getText()).collect(Collectors.toList());
		listItemPrice.stream().forEach(product -> listProductPrices.add(product.getText()));

		return listProductPrices;

	}

}