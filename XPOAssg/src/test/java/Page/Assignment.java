package Page;

import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;


import com.opencsv.CSVReader;


public class Assignment {
	
	
	String Lot_drop="Lot";
	String EnterDate_field ="EntryDate";
	String LeaveDate_field = "ExitDate";
	String Calcualte_Btn ="//input[@name='Submit']";
	String Cost_txt ="//span[@class='SubHead']/font/b";
	WebDriver FF ;
	
	
	@BeforeClass
	public void openURl() {
		FF= new FirefoxDriver();
		String Url="http://adam.goucher.ca/parkcalc/";	
		FF.get(Url);
		
	}
	
	private static final String COMMENT_PREFIX="--";
	
	@Test(dataProvider="login")
	public void TermParking(String lot,String Entry,String Leaving,String Cost) {
	
		System.out.println(Cost);
		switch(lot) {
		case "Short-Term Parking":
			calulation(lot,Entry,Leaving,Cost);
	
		break;
		case "Long-Term Surface Parking":
			calulation(lot,Entry,Leaving,Cost);
	
		break;
		}
		
	}

	@DataProvider(name="login")
	public Object[][] filereader(){
	try {
	FileReader filereader = new FileReader("CSV/Data.csv"); 
	  
    CSVReader csvReader = new CSVReader(filereader); 
    List <String[]> complete = csvReader.readAll();
    complete.remove(0);

    Iterator<String []> itr = complete.iterator();
   while (itr.hasNext()) {
	   String[] row = itr.next();
	   System.out.println(row);
	   if(row[0].startsWith(COMMENT_PREFIX)) {
		   itr.remove();
		   
	   }
   
}
   Object[][] arrayResp = new Object[complete.size()][];
   for(int i = 0; i < complete.size(); i++){
	   System.out.println(complete.get(i));
       arrayResp[i] = complete.get(i);
   }
   return arrayResp;
 
   
}catch(FileNotFoundException ex) {
	System.out.println(ex);
}catch(IOException ex){
	System.out.println(ex);
     

}
return new Object[][] {};}
	
	@AfterClass
	public void closeBrowser() {
		FF.quit();
	}

public void calulation(String lot,String Enter, String Leave, String Cost) {
	Select select = new Select(FF.findElement(By.id(Lot_drop)));
	select.selectByVisibleText(lot);
	FF.findElement(By.id(EnterDate_field)).clear();
	FF.findElement(By.id(EnterDate_field)).sendKeys(Enter);
	FF.findElement(By.id(LeaveDate_field)).clear();
	FF.findElement(By.id(LeaveDate_field)).sendKeys(Leave);
	FF.findElement(By.xpath(Calcualte_Btn)).click();
	assertThat(Cost.equals(FF.findElement(By.xpath(Cost_txt)).getText()));
	
}
}
