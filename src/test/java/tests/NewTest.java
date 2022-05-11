package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.CartEmptyPage;
import pageObjects.ShoppingBasketPage;
import utilityPackage.UtilityClass;

public class NewTest extends BaseTest{
  @Test
  public void f() {
	  
	  
	  CartEmptyPage p = new CartEmptyPage(driver);
	  p.waitForEmptyCartPageLoad();
	  ShoppingBasketPage objShoppingBasket = p.addProductFromRecommended();
      
	  objShoppingBasket.waitForShoppingBasketPageLoad();
      
      String strURL = driver.getCurrentUrl();
      boolean b = strURL.contains("overview");
      Assert.assertTrue(b, "overview not found in shopping basket url");
	  System.out.println(strURL);
	  
	  objShoppingBasket.waitForRecommendedProductsLoad();
	  
	  // Add 3 more products
	  for(int i=0;i<3;i++) {
	  String strAddMore = objShoppingBasket.addMoreProducts();
	  boolean hasCartUpdated1 = objShoppingBasket.waitForRecommendedProductsLoad();
  }
	  List<String> priceList = new ArrayList<String>();
	  priceList = objShoppingBasket.getPricesFromCart();
	  
	  List<Double> listItemPrices = UtilityClass.updateList(priceList);
	//  listItemPrices = UtilityClass.updateList(priceList);
	  
	  
	  System.out.println("prices of itesm before sort::"+listItemPrices);
	  
	  Collections.sort(listItemPrices, Collections.reverseOrder());
	  
	  System.out.println("prices of itesm after sorting in descending order::"+listItemPrices);
	  
	  Double dblLeastPrice = listItemPrices.get(listItemPrices.size()-1);
	  String strLeastPrice = "€"+String.valueOf(dblLeastPrice);
	  
	  Double dblMaxPrice = listItemPrices.get(0);
	  String strMaxPrice = "€"+String.valueOf(dblMaxPrice);
	  
	  objShoppingBasket.constructXpathAndClickIncrement(strLeastPrice);
	  
	  objShoppingBasket.constructXpathAndClickDelete(strMaxPrice);
	  
	  
	  priceList = objShoppingBasket.getPricesFromCart();
	  
	  listItemPrices = UtilityClass.updateList(priceList);
	  
	  double dblSubTotal = 0.0;
	  
	  for(Double d: listItemPrices) {
		  dblSubTotal = dblSubTotal+d;
	  }
	  
	  String strSumOfPrice = "€"+String.valueOf(dblSubTotal);
	  
	  System.out.println(strSumOfPrice);
	  
	  String strTotalPrice = objShoppingBasket.getSubTotalPrice();
	  
	  System.out.println(strTotalPrice);
	  
	  Assert.assertEquals(strSumOfPrice, strTotalPrice, "Sum of prices in cart does not match with total price");
	  
	  objShoppingBasket.updateDeliveryCountry("5000");
	  
	  objShoppingBasket.waitForLoadAfterCartPageUpdate();
	  
	  String strDeliveryFee = objShoppingBasket.getDeliveryFee().trim();
	  
	  System.out.println(strDeliveryFee);

  }
}
