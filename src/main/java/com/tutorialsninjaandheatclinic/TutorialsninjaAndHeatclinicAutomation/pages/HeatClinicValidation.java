package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages;

import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.FetchDataFromDataBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Waits;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ExcelReader;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class HeatClinicValidation {
	WebDriver driver;
	Utility utility;
	Properties loc;
	Properties testDatafromProperty;
	Waits wait;
	ExcelReader excelReader;
	FetchDataFromDataBase fetchDataFromDataBase;

	public HeatClinicValidation(WebDriver driver) {
		this.driver = driver;
		utility = new Utility(driver);
		excelReader=new ExcelReader();
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDatafromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		wait = new Waits();
		fetchDataFromDataBase=new FetchDataFromDataBase();

	}

	/**
	 * This method validate all details of selected producr in cart
	 * 
	 * @param expecteddata
	 * @param log
	 * @throws NullCellValueException 
	 * @throws SQLException 
	 */
	public void productvalidation( LogReport log) throws NullCellValueException, SQLException {
		String productName = utility.getElement(loc.getProperty("loc.productname.txt")).getText();
		String expectedProductName=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"ProductName", "TS-01");
		log.info(assertion(productName, expectedProductName));
		String productSize = utility.getElement(loc.getProperty("loc.productsize.txt")).getText();
		String expectedProductSize=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"ProductSize", "TS-01");
		log.info(assertion(productSize, expectedProductSize));
		String productColour = utility.getElement(loc.getProperty("loc.productcolour.txt")).getText();
		String expectedProductColour=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"ProductColour", "TS-01");
		log.info(assertion(productColour, expectedProductColour));
		String productMessage = utility.getElement(loc.getProperty("loc.productmessage.txt")).getText();
		String expectedProductMessage=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"Message", "TS-01");
		log.info(assertion(productMessage, expectedProductMessage));
	}

	/**
	 * This method did assertion and return the message
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public String assertion(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			return "Validation pass:" + actual + " and " + expected + " match";
		} catch (Exception e) {
			e.printStackTrace();
			return "Validation fail:" + actual + " and " + expected + " not match";

		}
	}

	/**
	 * This method add the product in the cart with all given specification
	 * 
	 * @param log
	 */
	public void addTheShirtToCart(LogReport log) {
		log.info("Shirt is selected");
		utility.clickElement(loc.getProperty("loc.habaneroshirtbuynow.btn"));
		log.info("Shirt colour is selected");
		utility.clickElement(loc.getProperty("loc.shirtcolour.btn"));
		log.info("Shirt size is selected");
		utility.clickElement(loc.getProperty("loc.shirtsize.btn"));
		log.info("Shirt personalized message is given");
		utility.clickAndSendText(loc.getProperty("loc.shirtpersonalizedmsg.input"),
				testDatafromProperty.getProperty("personalizedname"));
		log.info("buy now is clicked");
		utility.clickElement(loc.getProperty("loc.buynow.btn"));
		wait.waitElementToBeClickable(driver, loc.getProperty("loc.viewcart.btn"));
		utility.clickElement(loc.getProperty("loc.viewcart.btn"));
		log.info("view cart is clicked");
		wait.waitPresenceOfElementLocated(driver, loc.getProperty("loc.productname.txt"));

	}

	public void viewingMenValidation(LogReport log) {
		String viewingmentext = utility.getElement(loc.getProperty("loc.viewingmens.txt")).getText();
		String[] actualviewingmentext = viewingmentext.split(" ");
		log.info(assertion(actualviewingmentext[0] + actualviewingmentext[1],
				testDatafromProperty.getProperty("viewingmen")));
		log.info("Viewing Mens is succefully validate");
	}

	/**
	 * This method verify the total amount and update the product quantity and again
	 * validte the total amount
	 * 
	 * @param expected
	 * @param log
	 * @throws NullCellValueException 
	 * @throws SQLException 
	 */
	public void totalPriceValidation( LogReport log) throws NullCellValueException, SQLException {
		log.info("total price validation");
		String price = utility.getElement(loc.getProperty("loc.totalamount.txt")).getText();
		String expectedTotalPrice=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"Totalprice", "TS-01");
		log.info(assertion(price, expectedTotalPrice));
		log.info("Update the quatity");
		utility.clearField(loc.getProperty("loc.productquantityupdate.input"));
		String quantity=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"quantity", "TS-01");
		String expectedTotlPriceAfterUpdation=fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicProductDetails",
				"TotalPriceAfterUpdate", "TS-01");
		utility.clickAndSendText(loc.getProperty("loc.productquantityupdate.input"), quantity);
		utility.clickElement(loc.getProperty("loc.productquantityupdate.btn"));
		wait.hardWait(Long.parseLong(testDatafromProperty.getProperty("waitingtime")));
		String priceafterupdate = utility.getElement(loc.getProperty("loc.subtotal.txt")).getText();
		log.info(assertion(priceafterupdate, expectedTotlPriceAfterUpdation));

	}
}
