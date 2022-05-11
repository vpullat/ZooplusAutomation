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
	  
	  
	  System.out.println(listItemPrices);
	  
	  Collections.sort(listItemPrices, Collections.reverseOrder());
	  
	  System.out.println(listItemPrices);
//	  
//	  String strAddFirst2 = p.addMoreProducts();
//	  boolean hasCartUpdated2 = p.waitForElementToLoad();
//	  
//	  String strAddFirst3 = p.addMoreProducts();
//	  boolean hasCartUpdated3 = p.waitForElementToLoad();
//	  
//	  List<String> list = p.getPricesFromCart();
//	  
//	  for(String s: list) {
//		  System.out.println(s);
//	  }
//	  
//      try {
//		Thread.sleep(30000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
  }
}
