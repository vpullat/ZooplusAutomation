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
	private By gridYouMightAlsoLike = By.id("#js-zootopiaTitle-0");
	private By totalPrice = By.cssSelector("span.v3-text--no-margin.v3-text--right");
	private By labelDeliveryTo = By.cssSelector("div.js-shipping-cost.shipping__country__label");
	private By buttonSelectCountry = By.cssSelector("button.dropdown__button.v3-text--greyDark");
	private By inputPostCode = By.cssSelector("div.tippy-content input.v3-form__input");
	private By labelCountryName = By.xpath("//div[@class='js-countryDropdown dropdown dropdown--isOpen']//ul[@class='dropdown__list v3-text--greyDark']//li[@data-label='Portugal']");
	private By dropDownCountryList = By.cssSelector(".tippy-box .dropdown__list.v3-text--greyDark");
	private By buttonUpdate = By.cssSelector(".tippy-box button.v3-btn--fullWidth");
	private By labelDeliveryFee = By.cssSelector("span.shipping__cost__value");
	
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

	public boolean waitForLoadAfterCartPageUpdate() {

		try {
			waitForElement(gridYouMightAlsoLike);
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
		// List<String> st = elementName.stream().map(s ->
		// s.getText()).collect(Collectors.toList());
		listItemPrice.stream().forEach(product -> listProductPrices.add(product.getText()));

		return listProductPrices;

	}

	public void constructXpathAndClickIncrement(String addProduct) {

		try {
			By incrementProduct = By.xpath("//div[@class='cart__table two__column']//div[contains(text(),'" + addProduct
					+ "')]//preceding-sibling::div[@class='quantity regular__product two__column']//button[@class='v3-counter__btn v3-counter__btn--right js-inc-amount']");
			List<WebElement> incrementElements = getAllElements(incrementProduct);

			for (WebElement w : incrementElements) {

				//if (w.isDisplayed() == true) {
					click(w);
				//}

				waitForLoadAfterCartPageUpdate();
			}

			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void constructXpathAndClickDelete(String deletButton) {

		try {
			By incrementProduct = By.xpath("//div[@class='cart__table two__column']//div[contains(text(), '"+deletButton
					+"')]//preceding-sibling::div[@class='quantity regular__product two__column']//button[@class='remove__btn--icon js-remove__btn']");
			List<WebElement> deleteElements = getAllElements(incrementProduct);

			for (WebElement w : deleteElements) {

				if (w.isDisplayed() == true) {
					click(w);
				}

				waitForLoadAfterCartPageUpdate();
			}

			
		} catch (Exception e) {
			
		}

	}
	
	public String getSubTotalPrice() {

		try {
			String strTotalPrice = getText(totalPrice);
			return strTotalPrice;
		}
		catch(Exception e){
			return "0";
		}
	}
	
	public String updateDeliveryCountry(String textToEnter) {
		
		try {
			click(labelDeliveryTo);
			click(buttonSelectCountry);
			waitForElement(dropDownCountryList);
			srollToElement(labelCountryName);
			click(labelCountryName);
			enterText(inputPostCode,textToEnter);
			click(buttonUpdate);
			return "country updated";
		}
		catch(Exception e){
			return "country update failed "+e;
		}
	}
	
	public String getDeliveryFee() {

		try {
			String strTotalPrice = getText(labelDeliveryFee);
			return strTotalPrice;
		}
		catch(Exception e){
			return "0";
		}
	}

}
