package utilityPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UtilityClass {

	public static List<Double> updateList(List<String> list) {

		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).substring(1));
		}
		List<Double> listPrices = new ArrayList<Double>();

		for (int i = 0; i < list.size(); i++) {
			listPrices.add(Double.parseDouble(list.get(i)));
		}
		return listPrices;
	}

	public static double roundDoubleValues(double dNumber) {

		double roundDouble = Math.round(dNumber * 100.0) / 100.0;
		return roundDouble;
	}
	
	public static Properties readConfigFile() throws IOException {

		String localDirectory = System.getProperty("user.dir");
		
		File file = new File(localDirectory + "\\src\\main\\resources\\config\\config.properties");
		
		FileInputStream objFileStream = null;
	      Properties objProperties = null;
	      try {
	    	  objFileStream = new FileInputStream(file);
	    	  objProperties = new Properties();
	    	  objProperties.load(objFileStream);
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	    	  objFileStream.close();
	      }
	      return objProperties;
	   }
	}

