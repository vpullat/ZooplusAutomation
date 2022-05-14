package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CartEmptyPage;
import pageObjects.ShoppingBasketPage;
import utilityPackage.UtilityClass;

public class NewTest extends BaseTest {
	@Test
	public void fnAddProductToCart() {

		try {
			CartEmptyPage objEmptyCart = new CartEmptyPage(driver);
			objEmptyCart.waitForEmptyCartPageLoad();
			ShoppingBasketPage objShoppingBasket = objEmptyCart.addProductFromRecommended();

			// Waiting for the shopping cart page to fully load
			objShoppingBasket.waitForRecommendedProductsLoad();

			// Validate the overview URL
			String strURL = driver.getCurrentUrl();
			boolean b = strURL.contains("overview");
			System.out.println("Printing Shopping cart URL::: "+strURL);
			Assert.assertTrue(b, "overview not found in shopping basket url");

			// Add 3 more products
			for (int i = 0; i < 3; i++) {
				String strAddMore = objShoppingBasket.addMoreProducts();
				boolean hasCartUpdated = objShoppingBasket.waitForRecommendedProductsLoad();
			}

			// Get and print the prices in ascending and descending order
			List<String> priceList = new ArrayList<String>();
			priceList = objShoppingBasket.getPricesFromCart();

			List<Double> listItemPrices = UtilityClass.updateList(priceList);
			System.out.println("prices of items before sort:: " + listItemPrices);

			Collections.sort(listItemPrices, Collections.reverseOrder());
			System.out.println("prices of items after sorting in descending order:: " + listItemPrices);

			Double dblLeastPrice = listItemPrices.get(listItemPrices.size() - 1);
			String strLeastPrice = "€" + String.valueOf(dblLeastPrice);

			Double dblMaxPrice = listItemPrices.get(0);
			String strMaxPrice = "€" + String.valueOf(dblMaxPrice);

			// Increment the product/products with the lowest price
			objShoppingBasket.constructXpathAndClickIncrement(strLeastPrice);

			// Delete the product/products with the highest price
			objShoppingBasket.constructXpathAndClickDelete(strMaxPrice);

			// Get the sum of prices in the cart and Subtotal price
			priceList = objShoppingBasket.getPricesFromCart();
			listItemPrices = UtilityClass.updateList(priceList);

			double dblSubTotal = 0.0;
			for (Double d : listItemPrices) {
				dblSubTotal = dblSubTotal + d;
			}

			dblSubTotal = UtilityClass.roundDoubleValues(dblSubTotal);

			// Pint and assert the prices
			String strSumOfPrice = "€" + String.valueOf(dblSubTotal);
			System.out.println("Sum of prices of the products in cart:: " + strSumOfPrice);
			String strSubTotalPrice = objShoppingBasket.getSubTotalPrice();
			System.out.println("Subtotal price:: " + strSubTotalPrice);

			Assert.assertEquals(strSumOfPrice, strSubTotalPrice,
					"Sum of prices in cart does not match with Subtotal price");

			// Update the delivery country to Portugal
			objShoppingBasket.updateDeliveryCountry("5000");
			objShoppingBasket.waitForLoadAfterCartPageUpdate();

			// Fetch and print the delivery fee
			String strDeliveryFee = objShoppingBasket.getDeliveryFee().trim();
			System.out.println("Delivery price to Portugal::" + strDeliveryFee);

			// Get the sum of prices in the cart and Subtotal price
			priceList = objShoppingBasket.getPricesFromCart();
			listItemPrices = UtilityClass.updateList(priceList);

			dblSubTotal = 0.0;
			for (Double d : listItemPrices) {
				dblSubTotal = dblSubTotal + d;
			}

			dblSubTotal = UtilityClass.roundDoubleValues(dblSubTotal);
			String strFinalSumOfPrice = "€" + String.valueOf(dblSubTotal);

			// Pint and assert the prices
			System.out.println("Sum of final prices of the products in cart:: "+strFinalSumOfPrice);
			String strFinalSubTotalPrice = objShoppingBasket.getSubTotalPrice();
			System.out.println("Final Subtotal price:: "+strFinalSubTotalPrice);

			Assert.assertEquals(strFinalSumOfPrice, strFinalSubTotalPrice,
					"Final sum of prices in cart does not match with the final Subtotal price");
		} catch (Exception e) {
			Assert.assertTrue(false, "Test failed with Exception::" + e);
		}
	}
}
