package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.testscripts;

import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.FetchDataFromDataBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.TestBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.dataProvider.TestDataProvider;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Waits;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.HeatClinicValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ExcelReader;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class HeatClinicFuctionalityTesting extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;
	Waits wait;
	HeatClinicValidation heatClinicValidation;
	ExcelReader excelReader;
	FetchDataFromDataBase fetchDataFromDataBase;

	@BeforeTest
	public void intailization() {
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDataFromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		log = new LogReport();
		excelReader = new ExcelReader();
		fetchDataFromDataBase = new FetchDataFromDataBase();
		utility = new Utility(driver);
		validationPage = new TutorialsNinjaValidation(driver);
		wait = new Waits();
		heatClinicValidation = new HeatClinicValidation(driver);

	}

	@Test(priority = 1)
	public void headerRedirectionValidation() throws NullCellValueException {
		String headerIndex;
		String pageTitle;
		try {
			int rowNumber = fetchDataFromDataBase.getRowCount(FileConstant.DATABASENAME, "heatClinicMenuTitleBar");

			log.info("Hearder is traverse");

			for (int index = 1; index < rowNumber; index++) {

				headerIndex = fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicMenuTitleBar", "HeaderIndex",
						"TS-" + index);

				pageTitle = fetchDataFromDataBase.fetchData(FileConstant.DATABASENAME, "heatClinicMenuTitleBar",
						"PageTitle", "TS-" + index);
				utility.clickElement(loc.getProperty("loc.headermenu.btn").replace("index", headerIndex));
				log.info(validationPage.pageRedirection(pageTitle));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 2, dataProvider = "shirtdata", dataProviderClass = TestDataProvider.class)
	public void merchandiseFunctionality(String[] expecteddata) {
		Actions action = new Actions(driver);
		WebElement wb = utility.getElement(loc.getProperty("loc.merchandise.btn"));
		action.moveToElement(wb).build().perform();
		wait.waitElementToBeClickable(driver, loc.getProperty("loc.merchandise.mens.btn"));
		utility.clickElement(loc.getProperty("loc.merchandise.mens.btn"));
		heatClinicValidation.viewingMenValidation(log);
		heatClinicValidation.addTheShirtToCart(log);

		try {
			heatClinicValidation.productvalidation(log);
		} catch (NullCellValueException | SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

		try {
			heatClinicValidation.totalPriceValidation(log);
		} catch (NullCellValueException | SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

}
