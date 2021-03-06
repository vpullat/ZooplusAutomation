package pageObjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingBasketPage extends BasePage {

	private By labelPrice = By.xpath("//div[@role='cell' and @class='value two__column']");
	private By buttonAddMoreProducts = By.cssSelector("div#js-zootopiaRecos-1 button");
	private By gridRecommendedProducts = By.id("#js-zootopiaRecosContainer-1");
	private By gridYouMightAlsoLike = By.id("#js-zootopiaTitle-0");
	private By gridYourShoppingBasket = By.cssSelector("div.cart__table.two__column");
	private By totalPrice = By.cssSelector("span.v3-text--no-margin.v3-text--right");
	private By labelDeliveryTo = By.cssSelector("div.js-shipping-cost.shipping__country__label");
	private By buttonSelectCountry = By.cssSelector("button.dropdown__button.v3-text--greyDark");
	private By inputPostCode = By.cssSelector("div.tippy-content input.v3-form__input");
	private By labelCountryName = By.xpath(
			"//div[@class='js-countryDropdown dropdown dropdown--isOpen']//ul[@class='dropdown__list v3-text--greyDark']//li[@data-label='Portugal']");
	private By dropDownCountryList = By.cssSelector(".tippy-box .dropdown__list.v3-text--greyDark");
	private By buttonUpdate = By.cssSelector(".tippy-box button.v3-btn--fullWidth");
	private By labelDeliveryFee = By.cssSelector("span.shipping__cost__value");

	public ShoppingBasketPage(WebDriver driver) {
		super(driver);
	}

	public boolean waitForShoppingBasketPageLoad() {

		try {
			waitForElement(labelPrice);
		} catch (Exception e) {
			return false;
		}
		return true;
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
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String addMoreProducts() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		int intIterator = 0;
		int intAttempts = 0;

		try {
			List<WebElement> elementName = getAllElements(buttonAddMoreProducts);

			while (elementName.size() == 0 && intAttempts < 5) {
				elementName = getAllElements(buttonAddMoreProducts);
				intAttempts++;
			}

			intAttempts = 0;

			while (intIterator == 0 && intAttempts < 5) {
				for (WebElement elm : elementName) {

						try {
							click(elm);
							intIterator++;
							break;
						} catch (Exception e) {

						}
				}
				if(intIterator ==0) {
					driver.navigate().refresh();
					waitForRecommendedProductsLoad();
					elementName = getAllElements(buttonAddMoreProducts);
				}
				
				intAttempts++;
			}
		} catch (Exception e) {
			return "Failed";
		}
		return "Success";
	}

	public List<String> getPricesFromCart() {

		List<String> listProductPrices = new ArrayList<>();
		try {

			List<WebElement> listItemPrice = getAllElements(labelPrice);
			listItemPrice.stream().forEach(product -> listProductPrices.add(product.getText()));

		} catch (Exception e) {

			return listProductPrices;
		}
		return listProductPrices;
	}

	public String constructXpathAndClickIncrement(String addProduct) {

		try {
			By buttonIncrementProduct = By.xpath("//div[contains(text(),'" + addProduct
					+ "')]//preceding-sibling::div[@class='quantity regular__product two__column']//button[@class='v3-counter__btn v3-counter__btn--right js-inc-amount']");
			List<WebElement> incrementElements = getAllElements(gridYourShoppingBasket, buttonIncrementProduct);

			int itrAttempt = 0;
			while (incrementElements.size() > 0 && itrAttempt < 10) {
				for (WebElement elm : incrementElements) {

					try {
						click(elm);
					} catch (Exception e) {

					}
					waitForLoadAfterCartPageUpdate();
					incrementElements = getAllElements(gridYourShoppingBasket, buttonIncrementProduct);
					itrAttempt++;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return "Failed";
		}
		return "Success";
	}

	public String constructXpathAndClickDelete(String deletButton) {

		try {
			By buttonDeleteProduct = By.xpath("//div[contains(text(), '" + deletButton
					+ "')]//preceding-sibling::div[@class='quantity regular__product two__column']//button[@class='remove__btn--icon js-remove__btn']");
			List<WebElement> deleteElements = getAllElements(gridYourShoppingBasket, buttonDeleteProduct);

			int itrAttempt = 0;
			while (deleteElements.size() > 0 && itrAttempt < 10) {
				for (WebElement elm : deleteElements) {

					try {
						click(elm);
					} catch (Exception e) {

					}
					waitForLoadAfterCartPageUpdate();
					deleteElements = getAllElements(gridYourShoppingBasket, buttonDeleteProduct);
					itrAttempt++;
				}
			}
		} catch (Exception e) {
			return "Failed";
		}
		return "Success";
	}

	public String getSubTotalPrice() {
		String strTotalPrice = "0";
		try {
			strTotalPrice = getText(totalPrice);
		} catch (Exception e) {
			return strTotalPrice;
		}
		return strTotalPrice;
	}

	public String updateDeliveryCountry(String textToEnter) {

		try {
			click(labelDeliveryTo);
			click(buttonSelectCountry);
			waitForElement(dropDownCountryList);
			srollToElement(labelCountryName);
			click(labelCountryName);
			enterText(inputPostCode, textToEnter);
			click(buttonUpdate);
			return "country updated";
		} catch (Exception e) {
			return "country update failed " + e;
		}
	}

	public String getDeliveryFee() {
		String strTotalPrice = "0";
		try {
			strTotalPrice = getText(labelDeliveryFee);
		} catch (Exception e) {
			return "0";
		}
		return strTotalPrice;
	}

}
