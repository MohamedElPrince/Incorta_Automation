package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class AllContent {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content_allContent");

	//// Elements
	By header_allContentTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='All Content']");
	By body_folder_link;
	By body_folderProperties_link;
	By body_dashboard_link;
	By body_dashboardProperties_link;
	By body_folderName_link;
	By body_folderProperties_Button;
	By body_dashboardProperties_Button;
	By body_dashboardName;
	By body_FolderName_InsideFolder;
	By body_dashboardName_Button;
	
	By popup_newDashboard_dashboardName_textBox = By.name("reportName");
	By popup_newDashboard_create_button = By.xpath("//button[@type='submit'][normalize-space()='Create']");
	By popup_newFolder_folderName = By.xpath("//div[@class='inputGroup newItemModal']/input");
	By popup_folderProperties_manageFolder;
	By popup_folderProperties_manageFolder_confirmationButton;
	By popup_SchemaSettings_SharingTab_SelectFromSearchField;
	By popup_SchemaSettings_SharingTab_ClickSearchField = By.xpath("//div[@class='shareSearch ng-scope']/input[@type=\"text\"]");
	By popup_renameFolder;
	
	By popup_renameFolder_cancelButton = By.xpath("//a[contains(string(),'Cancel')]");
	By popup_rename_moveFolder_rename_done_move_Buttons = By.xpath("//button[contains(string(),'Done')]");
	
	By popup_moveFolder_folderNameToMoveTo;
	//// Functions
	public AllContent(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allContentTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_allContentTabHeader_link, "class", "selectedTab", true);
	}

	// Assert_folderIsDisplayed
	public void Assert_dashboardIsDisplayed(String name) {
		body_dashboard_link = By.xpath(" //section[@id=\"content\"]//a[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_dashboard_link, true);
	}

	// Click_folder
	// Click_folderProperties
	// Click_dashboard
	// Click_dashboardProperties

	public String setNewDashboardName() {
		String newDashboardName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_newDashboard_dashboardName_textBox, newDashboardName);
		ElementActions.click(driver, popup_newDashboard_create_button);
		return newDashboardName;
	}

	/**
	 * 
	 * @param FolderName
	 * Could Accept Folder Name Or Dashboard Name
	 */
	public void AddText(String FolderName)
	{
		ElementActions.type(driver,popup_newFolder_folderName , FolderName);
	}

	public void Click_CreateButton()
	{
		ElementActions.click(driver, popup_newDashboard_create_button);
	}
	
	public void Assert_folder_Dashboard_IsDisplayed(String FolderName)
	{
		body_folderName_link = By.xpath("//a[contains(string(),'"+FolderName+"')]");
		Assertions.assertElementExists(driver, body_folderName_link, true);
	}
	
	public void Assert_folder_Dashboard_IsNotDisplayed(String FolderName)
	{
		body_folderName_link = By.xpath("//a[contains(string(),'"+FolderName+"')]");
		Assertions.assertElementExists(driver, body_folderName_link, false);
	}

	public void Click_Folder_Dashboard_Properties(String FolderName)
	{
		body_folderProperties_Button = By.xpath("//a[@title='"+FolderName+"']/parent::div/a[contains(@class,'menu-icon')]");
		ElementActions.click(driver, body_folderProperties_Button);
	}
	
	/**
	 * 
	 * @param ActionsOnFolder
	 * shareFolder
	 * moveFolder
	 * renameFolder
	 * deleteFolder
	 */
	public void Click_FolderProperties_ManageFolderButtons(String ActionsOnFolder)
	{
		popup_folderProperties_manageFolder = By.xpath("//a[@class='"+ActionsOnFolder+"']");
		ElementActions.click(driver, popup_folderProperties_manageFolder);
	}
	
	/**
	 * 
	 * @param ConfirmationButtonName
	 * Delete [Confirm delete folder]
	 * Cancel [Cancel Confirmation for delete folder]
	 */
	public void Click_Folder_Dashboard_Properties_ManageFolderButtons_ConfirmationButtonsForDelete (String ConfirmationButtonName)
	{
		popup_folderProperties_manageFolder_confirmationButton = By.xpath("//div[contains(@class,'DeleteModal')]//a[contains(string(),'"+ConfirmationButtonName+"')]");
		ElementActions.click(driver, popup_folderProperties_manageFolder_confirmationButton);
	}
	
	public void Folder_Sharing_SearchAndSelectUsers(String SearchText)
	{
		popup_SchemaSettings_SharingTab_SelectFromSearchField = By.xpath("//h5[@class='UserData left ng-binding'][contains(text(),'"+SearchText+"')]");
		ElementActions.type(driver, popup_SchemaSettings_SharingTab_ClickSearchField, SearchText);
		ElementActions.click(driver, popup_SchemaSettings_SharingTab_SelectFromSearchField);
	}

	public void FolderProperties_RenameFolder(String FolderNewName)
	{
		popup_renameFolder= By.xpath("//input[@name='renameInput']");
		ElementActions.type(driver, popup_renameFolder, FolderNewName);
	}

	public void FolderProperties_RenameFolder_CancelButton()
	{
		ElementActions.click(driver, popup_renameFolder_cancelButton);
	}
		
	/**
	 * 
	 * @param ButtonName
	 * Done
	 * Rename
	 * Move
	 */
	public void FolderProperties_Rename_MoveFolder_Buttons(String ButtonName)
	{
		 popup_rename_moveFolder_rename_done_move_Buttons = By.xpath("//button[contains(string(),'"+ButtonName+"')]");
		ElementActions.click(driver, popup_rename_moveFolder_rename_done_move_Buttons);
	}

	public void Click_FolderProperties_MoveFolder_FolderNameToMoveTo(String FolderName)
	{
		popup_moveFolder_folderNameToMoveTo = By.xpath("//p[contains(string(),'"+FolderName+"')]");
		ElementActions.click(driver, popup_moveFolder_folderNameToMoveTo);
	}
	
	public void Assert_DashboardExist(String DashboardName)
	{
		By body_dashboardName = By.xpath("//a[@title='"+DashboardName+"']");
		Assertions.assertElementExists(driver, body_dashboardName, true);
	}

	public void Assert_FolderExist_InsideFolder(String FolderName)
	{
		body_FolderName_InsideFolder = By.xpath("//a[@title = '"+FolderName+"']");
		Assertions.assertElementExists(driver, body_FolderName_InsideFolder, true);
	}

	public void Click_FolderName(String FolderName)
	{
		body_FolderName_InsideFolder = By.xpath("//a[@title = '"+FolderName+"']");
		ElementActions.click(driver, body_FolderName_InsideFolder);
	}
	
	/**
	 * @param Actions
	 * Delete
	 * Rename
	 * Copy
	 * Share
	 * Export
	 * Move
	 */
	public void Click_DashboardProperties_ManageDashboardButtons(String Actions)
	{
		//Delete Dashboard
		//Used this variable ActionsOnFolder --> To avoid duplications between 2 buttons [Delete and export].
		body_dashboardProperties_Button = By.xpath("//a[@class='"+Actions+"']//following-sibling::a");
		ElementActions.click(driver, body_folderProperties_Button);
		//Share Dashboard
		//Rename Dashboard
		//Copy Dashboard
		//Move Dashboard
		//Export Dashboard
	}

	public void Click_Dashboard(String DashboradName)
	{
		body_dashboardName_Button = By.xpath("//a[@title='"+DashboradName+"']");
		ElementActions.click(driver, body_folderProperties_Button);
	}
	
}
