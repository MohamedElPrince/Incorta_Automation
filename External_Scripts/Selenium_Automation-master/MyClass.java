package newpackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;


import java.util.Random;

public class MyClass {

	public static void main(String[] args) {
		// declaration and instantiation of objects/variables
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://iwebdemo.incorta.com:6060/incorta/#/login";
		driver.get(baseUrl); // launch Firefox and direct it to the Base URL


		WebElement ten = driver.findElement(By.name("tenant"));
		ten.sendKeys("company");
		WebElement uname = driver.findElement(By.name("userName"));
		uname.sendKeys("super@incorta.com");
		WebElement pass = driver.findElement(By.name("password"));
		pass.sendKeys("SuperPass123");

		WebElement login_button = driver.findElement(By.className("signInBtn"));
		login_button.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement dashboard_button =driver.findElement(By.id("contentItem"));
		dashboard_button.click();

		// Selects Drop Down Menu
		WebElement dropmenu = driver.findElement(By.id("dropdownMenu1"));
		dropmenu.click();
		
		// Selects Create Dashboard from Menu
		WebElement drop_dash = driver.findElement(By.xpath("/html/body/div[2]/section/section/section/section/section/div/div/div/ul/li[2]"));
		drop_dash.click();

		int count = 0;
		Random num = new Random();
		count = num.nextInt(100)+1;
		String s_count = "auto_dash" + count;
		
		WebElement dash_name = driver.findElement(By.name("reportName"));
		dash_name.sendKeys(s_count);
		count++;
		
		
		WebElement create_dash_button = driver.findElement(By.className("userSaveBtn"));
		create_dash_button.click();
		
		WebElement filter_dash_done = driver.findElement(By.id("saveButton_Charts"));
		filter_dash_done.click();
		//filter_dash_done.click();
		
		WebElement dash_done = driver.findElement(By.xpath("/html/body/div[2]/section/section/section/aside/section/section/div/nav/ul/li[6]/a/img"));
		dash_done.click();
		
		
		
		driver.close();  //close Webpage
		driver.quit();   //quit Firefox
		System.exit(0);  // exit the program explicitly
	}
}
