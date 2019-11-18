package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.testscripts;

import java.sql.SQLException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.TestBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class TutorialsNinjaFunctionalityValidation extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;

	@BeforeTest
	public void intailization() {
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDataFromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		log = new LogReport();
		utility = new Utility(driver);
		validationPage = new TutorialsNinjaValidation(driver);

	}

	@Test(priority = 1)
	public void tutorialsNinjaHomePageValidation() {

		log.info("Homepage is invoking");
		log.info(validationPage.pageRedirection(testDataFromProperty.getProperty("homepagetitle")));
		log.info("HomePage validate successfull");

	}

	@Test(priority = 2)
	public void tutorialsNinjaProductValidation() {

		try {
			validationPage.productSearchFunctionality(log);
		} catch ( NullCellValueException | SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

	}

	@Test(priority = 3)
	public void cartValidation() {
		utility.clickElement(loc.getProperty("loc.shoppingcart.btn"));
		log.info("Grand total before updating the quantity");
		utility.scrollDownPage(testDataFromProperty.getProperty("scrolldown"));
		String grandTotal = utility.getElement(loc.getProperty("loc.grandtotla.txt")).getText();
		log.info(validationPage.assertion(grandTotal, testDataFromProperty.getProperty("grandtotal")));
		utility.scrollIntoview(loc.getProperty("loc.cartpage.quantity.input"));
		utility.clearField(loc.getProperty("loc.cartpage.quantity.input"));
		utility.clickAndSendText(loc.getProperty("loc.cartpage.quantity.input"),
				testDataFromProperty.getProperty("updatequatity"));
		utility.clickElement(loc.getProperty("loc.updatequantity.btn"));
		String afterUpdationGrandTotal = utility.getElement(loc.getProperty("loc.grandtotla.txt")).getText();
		log.info("Grand total after quantity updation");
		log.info(validationPage.assertion(afterUpdationGrandTotal,
				testDataFromProperty.getProperty("grandtotalafterupdate")));

	}

}
