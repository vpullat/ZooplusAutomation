package utilityPackage;

import java.util.ArrayList;
import java.util.List;

public class UtilityClass {

	public static List<Double> updateList(List<String> list) {
		
		for(int i=0;i<list.size();i++) {
			list.set(i, list.get(i).substring(1));
		}
		List<Double> listPrices = new ArrayList<Double>();
		
		for(int i=0;i<list.size();i++) {
			listPrices.add(Double.parseDouble(list.get(i)));
		}
		return listPrices;
	}
}
