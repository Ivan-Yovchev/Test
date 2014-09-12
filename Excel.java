import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class Excel {

	public static void main(String[] args) throws InvalidFormatException, IOException {

		Locale.setDefault(Locale.ROOT);
		
		HashMap<String, Double> cities = new HashMap<>(); 
		
		InputStream inp = new FileInputStream("3. Incomes-Report.xlsx");

	    Workbook wb = WorkbookFactory.create(inp);
		
	    Sheet sheet1 = wb.getSheetAt(0);
	   
	    for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
			
	    	Row r = sheet1.getRow(i);
	    	
	    	// get city
	    	Cell c = r.getCell(0);
	    	String city = c.getRichStringCellValue().getString();
		    
	    	// get money
		    c = r.getCell(5);
		    Double money = c.getNumericCellValue();
		    
		    if(cities.containsKey(city)){
		    	
		    	cities.put(city, cities.get(city) + money);
		    	
		    }
		    else{
		    	
		    	cities.put(city, money);
		    	
		    }
	    	
		}
	    
	    // sort map
	    TreeMap<String, Double> sortedCities = new TreeMap<>(cities);
	    
	    double grandTotal = 0;
	    
	    // print
	    for (String key : sortedCities.keySet()) {
			
	    	NumberFormat formatter = new DecimalFormat("#0.00");
	    	System.out.println(key + " Total -> " + formatter.format(sortedCities.get(key)));
	    	grandTotal += sortedCities.get(key);
	    	
		}
		
	    NumberFormat formatter = new DecimalFormat("#0.00");
	    System.out.println("Grand Total -> " + formatter.format(grandTotal));
	    
	}

}