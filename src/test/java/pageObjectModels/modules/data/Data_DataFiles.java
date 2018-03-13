package pageObjectModels.modules.data;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;
import com.shaftEngine.io.FileManager;

public class Data_DataFiles {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_data_dataFiles");
	String dataFilesFolderPath = System.getProperty("testDataFolderPath") + "dataFiles/";

	//// Elements
	By header_dataFilesTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Data Files']");
	// header_dataFiles_label;
	// body_tableHeader_label;
	By body_name_link;
	// body_type_label;
	// body_permission_label;
	// body_uploadDate_label;
	// body_owner_label;

	By popup_uploadNewDataFile_selectFromYourPC_textBox = By
			.xpath("//div[contains(@class,'folderActions')]//input[@type='file']");
	By popup_uploadNewDataFile_uploadSuccessMessage_label = By
			.xpath("//div[contains(@class,'folderActions')]//p[@class='uploadSuccess']");
	By popup_uploadNewDataFile_ok_button = By
			.xpath("//div[contains(@class,'folderActions')]//button[@ng-click='modal.closeMe()']");

	//// Functions
	public Data_DataFiles(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_dataFilesTabIsSelected() {
		CustomAssertions.cAssertElementAttribute(driver, header_dataFilesTabHeader_link, "class", "selectedTab", true);
	}

	public void Assert_nameIsDisplayed(String name) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		CustomAssertions.cAssertElementExists(driver, body_name_link, true);
	}
	// Assert_typeIsDisplayed
	// Assert_permissionIsDisplayed
	// Assert_uploadDateIsDisplayed
	// Assert_ownerIsDisplayed

	// Click_name

	public String AddDataFile(String dataFileName, String dataFileExtension) {

		String dataFilePath = dataFilesFolderPath + dataFileName + "." + dataFileExtension;
		dataFilePath = (new File(dataFilePath)).getAbsolutePath();

		String tempDataFileName = dataFileName + "_" + String.valueOf(System.currentTimeMillis());
		String tempFilePath = dataFilesFolderPath + tempDataFileName + "." + dataFileExtension;
		tempFilePath = (new File(tempFilePath)).getAbsolutePath();

		FileManager.copyFile(dataFilePath, tempFilePath);

		ElementActions.type(driver, popup_uploadNewDataFile_selectFromYourPC_textBox, tempFilePath);

		FileManager.deleteFile(tempFilePath);

		return tempDataFileName;

	}

	public void Assert_dataFileUploadingWasSuccessful(String dataFileName) {
		CustomAssertions.cAssertElementAttribute(driver, popup_uploadNewDataFile_uploadSuccessMessage_label, "text",
				testDataReader.getCellData("DataFileUploadSuccessMessage"), true);
		ElementActions.click(driver, popup_uploadNewDataFile_ok_button);

	}
}
