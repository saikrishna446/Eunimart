package Eunimart.automation.commonutils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.beans.Statement;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtil {

	public static WebDriver driver = null;
	public static List<String> windowHandlers;
	public static WebElement webElement;
	public static List<WebElement> webelements;
	public static int defaultBrowserTimeOut;
	public static Date printDate = new Date();
	public static String parentWindowHandle = null;
	private static int windowCount = 1;
	public static Properties commonProperties = null;
	static String screenShotPath;
	static String dateData = printDate.toString().replace(':', '_').trim();
	static String folderName = dateData;
	static Connection con = null;
	public static String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "Reports"
			+ System.getProperty("file.separator") + "extentreport" + folderName + "" + ".html";
	private static Wait<WebDriver> wait = null;

	public static void openBrowser(String browserType) {

		try {
			if (browserType.equalsIgnoreCase("firefox")) {

				try {
					// String firefoxPath = System.getProperty("user.dir") +
					// System.getProperty("file.separator") + "drivers" +
					// System.getProperty("file.separator")+"";
					driver = new FirefoxDriver();
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else if (browserType.equalsIgnoreCase("iexplorer")) {
				System.out.println("iexplorer");
				try {
					// Add Ie Driver in the drivers and add the Driver name
					String DriverPath = System.getProperty("user.dir") + System.getProperty("file.separator")
							+ "drivers" + System.getProperty("file.separator") + "";
					System.setProperty("webdriver.ie.driver", DriverPath);
					driver = new InternetExplorerDriver();

					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");

				} catch (Exception e) {

					e.printStackTrace();
				}

			} else if (browserType.equalsIgnoreCase("chrome")) {
				String drv = "chromedriver";
				if(isWindows()) {
					drv +=".exe";
					
					
					
					
				}
				String DriverPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "drivers"
						+ System.getProperty("file.separator") + drv;
				System.setProperty("webdriver.chrome.driver", DriverPath);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });				 
				options.addArguments("--disable-notifications");

//		     	options.addArguments("--headless");	
				driver = new ChromeDriver(options);

			}

			
			
			if (driver != null) {				
				wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
						.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	
	public static String getStackTrace(Throwable t) {
	    StringWriter sw = new StringWriter();
	    t.printStackTrace(new PrintWriter(sw));
	    return sw.toString();
	}
	
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}

	public static WebElement waitUntil(final String locator) throws InterruptedException {
		            CommonUtil.wait(2);
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return findElementWithOutException(locator);
			}
		});
	}
	
	public static WebElement waitUntilValue(final String locator) {
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = findElementWithOutException(locator);
				String text = element.getAttribute("value");
				if(text != null && !text.isEmpty()) {
					return element;
				}else {
					throw new NoSuchElementException("Value is not available for elment "+element.toString());
				}
			}
		});
	}
	
	public static WebElement waitUntilValue(final String locator, String value) {
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = findElementWithOutException(locator);
				String text = element.getAttribute("value");
				if(text != null && text.equals(value)) {
					return element;
				}else {
					throw new NoSuchElementException("Value is not available for elment "+element.toString());
				}
			}
		});
	}
	
	public static void db() throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@172.16.1.182:1521:elitedemo";
			String username = "oms_owner";
			String password = "oms_owner";

			con = DriverManager.getConnection(url, username, password);
			getValues();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getValues() throws SQLException {
		// String columnLabel = "LAST_NAME";

		// ResultSet rst = stmt.executeQuery("SELECT * FROM V_HEADER_BLOCK WHERE
		// PROFILE_TYPE='LABEL' and PROFILE_CODE='NAME_LAST'");
		Statement stmt = (Statement) con.createStatement();
		ResultSet rst = ((java.sql.Statement) stmt).executeQuery(
				"SELECT OFFENDER_ID,ROOT_OFFENDER_ID,LAST_NAME FROM V_HEADER_BLOCK where LAST_NAME ='SMITH'");
		int n = 1;
		while (rst.next()) {
			for (int i = 0; i < n; i++) {
				String name = rst.getString(n);
				n++;
				System.out.print(name);
			}
		}

	}

	public static WebElement findElement(String locator) {

		if (locator != null) {

			String[] arrLocator = locator.split("#");
			String locatorTag = arrLocator[0].trim();
			String objectLocator = arrLocator[1].trim();
			try {
				if (locatorTag.equalsIgnoreCase("id")) {
					webElement = driver.findElement(By.id(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("name")) {
					webElement = driver.findElement(By.name(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("xpath")) {
					webElement = driver.findElement(By.xpath(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("linkText")) {
					webElement = driver.findElement(By.linkText(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("class")) {
					webElement = driver.findElement(By.className(objectLocator));

				} else {
					String error = "Please Check the Given Locator Syntax :" + locator;
					System.out.println("Please Check the Given Locator Syntax : " + locator);
					error = error.replaceAll("'", "\"");
					return null;
				}

			} catch (Exception exception) {
				String error = "Please Check the Given Locator Syntax :" + locator;
				error = error.replaceAll("'", "\"");

				exception.printStackTrace();

				return null;
			}
		}
		return webElement;
	}

	public static WebElement findElementWithOutException(String locator) {

		if (locator != null) {

			String[] arrLocator = locator.split("#");
			String locatorTag = arrLocator[0].trim();
			String objectLocator = arrLocator[1].trim();
			// try {
			if (locatorTag.equalsIgnoreCase("id")) {
				webElement = driver.findElement(By.id(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("name")) {
				webElement = driver.findElement(By.name(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("xpath")) {
				webElement = driver.findElement(By.xpath(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("linkText")) {
				webElement = driver.findElement(By.linkText(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("class")) {
				webElement = driver.findElement(By.className(objectLocator));

			} else {
				String error = "Please Check the Given Locator Syntax :" + locator;
				System.out.println("Please Check the Given Locator Syntax : " + locator);
				error = error.replaceAll("'", "\"");
				return null;
			}

			/*
			 * } catch (Exception exception) { String error =
			 * "Please Check the Given Locator Syntax :" + locator; error =
			 * error.replaceAll("'", "\"");
			 * 
			 * exception.printStackTrace();
			 * 
			 * return null; }
			 */
		}
		return webElement;
	}

	public static List<WebElement> findElements(String locator) {

		if (locator != null) {
			String[] arrLocator = locator.split("#");
			String locatorTag = arrLocator[0].trim();
			String objectLocator = arrLocator[1].trim();

			if (locatorTag.equalsIgnoreCase("id")) {
				webelements = driver.findElements(By.id(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("name")) {
				webelements = driver.findElements(By.name(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("xpath")) {
				webelements = driver.findElements(By.xpath(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("linkText")) {
				webelements = driver.findElements(By.linkText(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("class")) {
				webelements = driver.findElements(By.className(objectLocator));
			} else {
				System.out.println("Please Check the Locator Syntax Given :" + locator);
				return null;
			}
		}
		return webelements;
	}

	public static void createFloder() {
		System.out.println(printDate.toString().trim());
		String dateData = printDate.toString().replace(':', '_').trim();
		String folderName = "Execute" + dateData;
		System.out.println(folderName);
		String FolderPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "ScreenShots";
		screenShotPath = FolderPath + "/" + folderName;

		File file = new File(screenShotPath);

		file.mkdir();

	}

	public static void TakeScreenShot(String methodName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile
			// //method
			FileUtils.copyFile(src, new File(screenShotPath + "/" + methodName + ".png"));
		}

		catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	public static void windowmaximize() {
		driver.manage().window().maximize();

	}

	public static void mouseover(String locators) {

		WebElement element = CommonUtil.findElement(locators);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void keyBoard_SendKeys1(String text) {
		Keyboard key = ((HasInputDevices) driver).getKeyboard();
		key.sendKeys(text);

	}

	public static void RightClick(String locator) {
		Actions action = new Actions(driver);
		action.contextClick(CommonUtil.findElement(locator)).build().perform();

	}
	
	public static void RightClick(WebElement element) {
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
	}

	public static void keyBoard_SendKeys(String DoctorName) {
		Keyboard key = ((HasInputDevices) driver).getKeyboard();
		key.sendKeys(DoctorName);
	}

	public static String getTextFromTextBox(String locator) {
		WebElement element;
		String text = "NO VALUE RETRIVED";
		try {
			element = findElement(locator);

			text = element.getAttribute("value").trim();
			System.out.println(" Elemet text :::" + text);
		} catch (Exception e) {
		}
		element = null;
		return text;
	}
	
	public static String getTextFromTextBox(WebElement element) {		
		String text = "NO VALUE RETRIVED";
		try {
			text = element.getAttribute("value").trim();
			System.out.println(" Elemet text :::" + text);
		} catch (Exception e) {
		}
		element = null;
		return text;
	}

	public static boolean isElmentPrsent(String locator) {

		WebElement element = CommonUtil.findElement(locator);
		if (element.isDisplayed()) {

			return true;
		} else {

			return false;
		}
	}

	public static boolean isElmentEnabled(String locator) {

		WebElement element = CommonUtil.findElement(locator);
		if (element.isEnabled()) {

			return true;
		} else {

			return false;
		}
	}

	public static void closeBrowser() {
		if (driver != null)
			driver.close();
	}

	public static void openUrl(String url) {

		try {
			if (driver != null)
				driver.get(url);
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void waitForPageToStartLoading() {
		try {
			Object result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
			int iCount = 0;
			while (!result.toString().equalsIgnoreCase("loading") && iCount < 10) {
				Thread.sleep(1000);
				result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
				iCount++;
			}
		} catch (Exception e) {
			System.out.println("Exception from page load : " + e.getMessage());
		}
	}

	public static void waitForPageload() {

		try {

			Object result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
			int iCount = 0;
			while (!result.toString().equalsIgnoreCase("complete") && iCount < 10) {

				explicitlyWait(5);
				result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
				iCount++;
				System.out.println("Browser Status in While Loop::" + result.toString() + "::");
			}
			System.out.println("Page Load is completed");
		} catch (Exception e) {
			System.out.println("Exception from page load : " + e.getMessage());
		}
	}

	public static void enterText(String field, String value) {

		try {
			webElement = findElement(field);
			if (webElement.isDisplayed()) {
				webElement.clear();
				webElement.sendKeys(value);

			}
		} catch (Exception e) {
			System.out.println("Error occured whlie entering the value into textbox" + value + " *** "
					+ getErroMsg(e.getMessage()));
			String error = "Error occured whlie entering the value into textbox:" + value;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}
	}
	
	public static void enterText(WebElement webElement, String value) {
		if (webElement != null && webElement.isDisplayed()) {	
			Actions actions = new Actions(driver);			
			if (webElement != null) {
				webElement.clear();
				actions.moveToElement(webElement).click().perform();
			}
			//webElement.clear();
			try {
				Thread.sleep(1000);
			}catch(Exception e) {}
			webElement.sendKeys(value);
		}
	}

	public static void clickEnter(String field, String value) {

		try {
			webElement = findElement(field);
			if (webElement.isDisplayed()) {
				webElement.clear();
				webElement.sendKeys(Keys.ENTER);
			}
		} catch (Exception e) {
			System.out.println(
					"Error occured whlie pressing the enter key" + value + " *** " + getErroMsg(e.getMessage()));
			String error = "Error occured whlie pressing the enter";
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}
	}

	public static void ScrollByUseingXpath(String xpath) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(xpath));
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void ScrollByCoordinates(int x) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0," + x + ");");
	}

	public static void scrollDown() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 250);");
	}

	public static void scrollDownFull() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 950);");
	}

	public static void centerScrollDown() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 450);");
	}

	public static void scrollUp() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -150);");
	}

	public static void selectValueFromDropDownBox(String field, String value) {
		try {
			webElement = findElement(field);
			if (webElement.isDisplayed()) {
				Select select = new Select(webElement);
				select.selectByVisibleText(value);
			}
		} catch (Exception e) {
			System.out.println("Error occured whlie select the value from dropdownbox " + value + " *** "
					+ getErroMsg(e.getMessage()));
			String error = "Error occured whlie select the value from dropdownbox :" + value;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}
	}

	public static boolean isAlertPresent() {

		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			// Ex.printStackTrace();
			return false;
		}
	}

	// It will wait seconds
	public static void explicitlyWait(int time) {

		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void click(String field) {
		try {
			WebElement element = findElement(field);
			Actions actions = new Actions(driver);			
			if (element != null) {
				actions.moveToElement(element).click().perform();
			}
		} catch (Exception e) {
			System.out.println(
					"Error occured whlie click on the elements " + field + " *** " + getErroMsg(e.getMessage()));
			String error = "Error occured whlie click on the element :" + field;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}
	}
	
	public static void click(WebElement element) {   
		   if (element != null) {
		    Actions actions = new Actions(driver);   
		    if (element != null) {
		     actions.moveToElement(element).click().perform();
		    }
		   }
		 }
	
	
	
	public static void moveToclick(WebElement element) {
		Actions actions = new Actions(driver);			
		if (element != null) {
			actions.moveToElement(element).click().perform();
		}
	}

	public static void click(String field, int time) {
		try {
			WebElement element = findElement(field);
			if (element != null)
				element.click();
			explicitlyWait(time);
		} catch (Exception e) {
			System.out.println(
					"Error occured whlie click on the element " + field + " *** " + getErroMsg(e.getMessage()));
			String error = "Error occured whlie click on the element :" + field;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}
	}

	public static void isChecked(String field) {

		try {
			WebElement element = findElement(field);
			if (element != null)

				if (!element.isSelected()) {
					element.click();
				}

		} catch (Exception e) {
			System.out.println(
					"Error occured whlie selecting the check box " + field + " *** " + getErroMsg(e.getMessage()));
			String error = "Error occured whlie selecting the check box :" + field;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}

	}

	public static void isUnChecked(String field) {

		try {
			WebElement element = findElement(field);
			if (element != null)

				if (element.isSelected()) {
					element.click();
				}

		} catch (Exception e) {
			System.out.println(
					"Error occured whlie unchecking  the check box " + field + " *** " + getErroMsg(e.getMessage()));
			String error = "Error occured whlie unchecking  the check box :" + field;
			error = error.replaceAll("'", "\"");

			e.printStackTrace();

		}

	}

	public static String getText(String locator) {
		WebElement element;
		String text = "NO VALUE RETRIVED";
		try {
			element = findElement(locator);
			if (element != null)
				text = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		element = null;
		return text;
	}
	
	public static String getText(WebElement element) {		
		String text = "NO VALUE RETRIVED";
		if (element != null) {
			text = element.getText();
		}
		return text;
	}

	public static String getAttribute(String locator, String attribute) {
		WebElement element;
		String text = "NO VALUE RETRIVED";
		try {
			element = findElement(locator);
			if (element != null)
				text = element.getAttribute(attribute).trim();
		} catch (Exception e) {
		}
		element = null;
		return text;

	}

	/*
	 * * This method will return true when the radio button or check box is
	 * selected. return : boolean
	 */

	public static boolean isElementSelected(String locator) {
		WebElement element = findElement(locator);
		return element.isSelected();
	}

	public static void wait(int time) {

		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void horizontalscroll(String field) {

		WebElement scrollArea = findElement(field);

		// Scroll to div's most right:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);

		// Or scroll the div by pixel number:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 450", scrollArea);
	}

	public static String getTextWithOutExceptionHandling(String locator) {
		WebElement element;
		String text = "NO VALUE RETRIVED";
		element = findElementWithOutException(locator);
		if (element != null)
			text = element.getText();
		element = null;
		return text;
	}

	public static String getSelectedDropDownValue(String dropDownLocator) {
		String selectedValue = "";
		try {
			WebElement element = findElement(dropDownLocator);
			Select selectBox = new Select(element);
			selectedValue = selectBox.getFirstSelectedOption().getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedValue;
	}

	/*
	 * public static WebElement findElement(String locator) {
	 * 
	 * if (locator != null) {
	 * 
	 * String[] arrLocator = locator.split("#"); String locatorTag =
	 * arrLocator[0].trim(); String objectLocator = arrLocator[1].trim(); try { if
	 * (locatorTag.equalsIgnoreCase("id")) { webElement =
	 * driver.findElement(By.id(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("name")) { webElement =
	 * driver.findElement(By.name(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("xpath")) { webElement =
	 * driver.findElement(By.xpath(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("linkText")) { webElement =
	 * driver.findElement(By.linkText(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("class")) { webElement =
	 * driver.findElement(By.className(objectLocator));
	 * 
	 * } else { String error = "Please Check the Given Locator Syntax :" + locator;
	 * System.out.println("Please Check the Given Locator Syntax : " + locator);
	 * error = error.replaceAll("'", "\""); return null; }
	 * 
	 * } catch (Exception exception) { String error =
	 * "Please Check the Given Locator Syntax :" + locator; error =
	 * error.replaceAll("'", "\"");
	 * 
	 * exception.printStackTrace();
	 * 
	 * return null; } } return webElement; }
	 * 
	 * public static List<WebElement> findElements(String locator) {
	 * 
	 * if (locator != null) { String[] arrLocator = locator.split("#"); String
	 * locatorTag = arrLocator[0].trim(); String objectLocator =
	 * arrLocator[1].trim();
	 * 
	 * if (locatorTag.equalsIgnoreCase("id")) { webelements =
	 * driver.findElements(By.id(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("name")) { webelements =
	 * driver.findElements(By.name(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("xpath")) { webelements =
	 * driver.findElements(By.xpath(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("linkText")) { webelements =
	 * driver.findElements(By.linkText(objectLocator)); } else if
	 * (locatorTag.equalsIgnoreCase("class")) { webelements =
	 * driver.findElements(By.className(objectLocator)); } else {
	 * System.out.println("Please Check the Locator Syntax Given :" + locator);
	 * return null; } } return webelements; }
	 */

	public static void closeCriticalAlert(String criticalAlert) {

		WebElement webElement = findElement(criticalAlert);
		if (webElement.isDisplayed()) {

			driver.findElement(By.xpath(".//*[@id='ok']")).click();
		}
	}

	public static void switchWindow() {

		waitForWindowToAppear();
		String mainWindowHandle = driver.getWindowHandle();
		Set<?> sWindows = driver.getWindowHandles();
		Iterator<?> ite = sWindows.iterator();
		while (ite.hasNext()) {
			String popupHandle = ite.next().toString();
			if (!popupHandle.contains(mainWindowHandle)) {
				driver.switchTo().window(popupHandle);
			}
		}
		waitForPageload();
	}

	public static void switchWindows() {
		explicitlyWait(2);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public static String getErroMsg(String msg) {

		int a = msg.indexOf("(");
		return msg.substring(0, a);
	}

	public static void navigateUrl(String url) {

		try {
			driver.get(url);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String HitF9Key() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_F9);
		robot.keyRelease(KeyEvent.VK_F9);
		return "String";

	}

	/*public static String HitEscKey() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		return "String";

	}*/
	
	// New Esc Method to headless scripting 
	
       public static String HitEscKey() throws AWTException{
		
    	   Robot robot = new Robot();
    	   robot.keyPress(KeyEvent.VK_ESCAPE);
    	   robot.keyRelease(KeyEvent.VK_ESCAPE);
    	   return "String";

		

	}
	public static int getNumberOfRowsInTable(String tableId) {
		// Grab the table
		WebElement table = findElement(tableId);
		// Now get all the TR elements from the table
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + allRows.size());
		return allRows.size();

	}

	public static void handleAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String getNumbersFromString(String text) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(text);
		while (m.find()) {
			return m.group().trim();
		}

		return null;
	}

	/*
	 * public static void isMailRecived(String requestId) {
	 * 
	 * boolean flag = false;
	 * 
	 * try { if (EmailUtils.isMailRecived(requestId)) { flag = true; } else {
	 * explicitlyWait(1);
	 * 
	 * } } catch (ParseException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * if (flag) { CommonUtil.logMessage("Mail recived successfully.",
	 * "Mail recived successfully.", "PASS"); } else { CommonUtil.logMessage(
	 * "Mail recived successfully.", "Mail not recived successfully.", "FAIL"); }
	 * 
	 * }
	 */

	/*
	 * public static void switchToOriginalWindow() { try { if (windowHandlers.size()
	 * > 1) windowHandlers.remove(windowHandlers.size() - 1); Iterator<String> itr =
	 * windowHandlers.iterator(); String handler = ""; while (itr.hasNext()) handler
	 * = itr.next(); System.out.println( "handler : " + handler.toString()); //
	 * System.out.println("Title is :" +driver.getTitle().toString()); if (handler
	 * != "") driver = driver.switchTo().window(handler);
	 * System.out.println("Title is :" + driver.getTitle().toString());
	 * 
	 * } catch (Exception e) { logMessage("Switch to parent window.",
	 * "Exception occured. Exception : " + e.getMessage(), "fail"); } }
	 */

	public static void switchToOriginalWindow() {
		// String s1 = getParentWindow();
		driver.switchTo().window(getParentWindow());
	}

	public static void parseChars(String letter, Robot robot) {
		for (int i = 0; i < letter.length(); i++) {
			char chary = letter.charAt(i);
			typeCharacter(Character.toString(chary), robot);
		}
	}

	public static void typeCharacter(String letter, Robot robot) {

		if (Character.isLetterOrDigit(letter.charAt(0))) {
			try {
				boolean upperCase = Character.isUpperCase(letter.charAt(0));
				String variableName = "VK_" + letter.toUpperCase();
				KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');
				Class<? extends KeyEvent> clazz = ke.getClass();
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(ke);
				robot.delay(80);
				if (upperCase)
					robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);
				if (upperCase)
					robot.keyRelease(KeyEvent.VK_SHIFT);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			if (letter.equals("!")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("@")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("#")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("#")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("$")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_4);
				robot.keyRelease(KeyEvent.VK_4);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("%")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("^")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_6);
				robot.keyRelease(KeyEvent.VK_6);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("&")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("*")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_8);
				robot.keyRelease(KeyEvent.VK_8);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("=")) {
				robot.keyPress(KeyEvent.VK_EQUALS);
				robot.keyRelease(KeyEvent.VK_EQUALS);
			} else if (letter.equals(" ")) {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			} else if (letter.equals("/")) {
				robot.keyPress(KeyEvent.VK_BACK_SLASH);
				robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			} else if (letter.equals("\\")) {
				robot.keyPress(KeyEvent.VK_BACK_SLASH);
				robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			} else if (letter.equals("_")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(":")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(";")) {
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
			} else if (letter.equals(",")) {
				robot.keyPress(KeyEvent.VK_COMMA);
				robot.keyRelease(KeyEvent.VK_COMMA);
			} else if (letter.equals("?")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(" ")) {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			} else if (letter.equals(".")) {
				robot.keyPress(KeyEvent.VK_PERIOD);
				robot.keyRelease(KeyEvent.VK_PERIOD);
			}

		}
	}

	public static void downloadFile(String fileName) {

		String filePath = System.getProperty("user.dir") + File.separator + fileName + ".zip";

		System.out.println("filePath Download Method : -" + filePath);
		try {

			File f = new File(filePath.trim());
			if (filePath.endsWith(".zip")) {

				if (f.exists()) {
					f.delete();
					System.out.println("Deleted File Name : " + filePath);

				}
			}

			filePath = System.getProperty("user.dir") + File.separator + fileName + ".XLS";
			File f1 = new File(filePath.trim());
			if (filePath.endsWith(".XLS")) {

				if (f1.exists()) {
					f1.delete();
					System.out.println("Deleted File Name : " + filePath);

				}
			}

			filePath = System.getProperty("user.dir") + File.separator + fileName + ".CSV";
			File f2 = new File(filePath.trim());
			if (filePath.endsWith(".CSV")) {

				if (f2.exists()) {
					f2.delete();
					System.out.println("Deleted File Name : " + filePath);

				}
			}

			filePath = System.getProperty("user.dir") + File.separator + fileName;
			Robot robot = new Robot();
			Thread.sleep(20000);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);

			// robot.keyPress(KeyEvent.VK_ENTER);
			// robot.keyRelease(KeyEvent.VK_ENTER);

			/*
			 * robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_Y);
			 * robot.keyRelease(KeyEvent.VK_ALT); robot.keyRelease(KeyEvent.VK_Y);
			 */
			Thread.sleep(1000);

			parseChars(filePath, robot);

			Thread.sleep(2000);

			/*
			 * robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
			 * Thread.sleep(2000);
			 * 
			 */
			// parseChars(fileName,robot);
			// Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			/**
			 * robot.keyPress(KeyEvent.VK_ALT); robot.keyPress(KeyEvent.VK_Y);
			 * robot.keyRelease(KeyEvent.VK_ALT); robot.keyRelease(KeyEvent.VK_Y);
			 */

			Thread.sleep(4000);
			// robot.keyPress(KeyEvent.VK_ESCAPE);
			// robot.keyRelease(KeyEvent.VK_ESCAPE);
			// Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void selectMouseOverOption(String field) {

		Actions action = new Actions(driver);
		WebElement element = findElement(field);
		action.moveToElement(element).build().perform();

		// action.click();

		// action.perform();

		explicitlyWait(5);
		System.out.println("Successfully open the options");
	}
	/*
	 * public static void mouseactions(String locators) {
	 * 
	 * WebElement element = CommonUtil.findElement(locators); Actions action = new
	 * Actions(driver); action.moveToElement(element).build().perform();
	 * 
	 * }
	 */

	public static String getParentWindow() {
		String main_window = driver.getWindowHandle();
		return main_window;
	}

	public static void switchToNewWindow() {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle); // switch focus of WebDriver to
													// the next found window
													// handle (that's your newly
													// opened window)
		}
	}

	public static void switchToFrame(String locator) {
		WebElement element = CommonUtil.findElement(locator);
		driver.switchTo().frame(element);
	}

	public static void switchToFrame() {
		driver.switchTo().frame(0);
	}

	public static void doubleclick(String field) {
		webElement = CommonUtil.findElement(field);
		Actions builder = new Actions(driver);
		org.openqa.selenium.interactions.Action doubleClick = builder.doubleClick(webElement).build();
		doubleClick.perform();
	}

	public static void closeAllBrowsers() {
		driver.quit();
	}

	public static void waitForWindowToAppear() {

		for (int j = 0; j <= 15; j++) {

			int noOfWindows = driver.getWindowHandles().size();

			if (windowCount != noOfWindows) {

				windowCount = noOfWindows;
				System.out.println("window count :" + windowCount);

				/*
				 * for (String winHandle : driver.getWindowHandles()) {
				 * driver.switchTo().window(winHandle); }
				 */
				break;
			} else {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String getTitle() {
		return driver.getTitle();
	}

	public static void getDimensions() {
		System.out.println("*********Height********" + driver.manage().window().getSize().getHeight());
		System.out.println("*********Width********" + driver.manage().window().getSize().getWidth());
	}

	public static String GetCLassName(String xpath) {
		driver.findElement(By.xpath(xpath)).getAttribute("class");

		return "String";

	}

	/*public static String HitEnterKey() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		return "String";

	}*/
	public static String HitEnterKey()throws AWTException {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ENTER).build().perform();
		return "String";

	}

	public static String Backspace() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		return "String";

	}

	public static String HitEnterTAB() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		return "String";

	}

	public static String Split(String srg) {

		String file = srg;
		String[] array = file.split("\\.");
		System.out.println("input string: " + file);
		System.out.println("output array after splitting with regex'\\.' : " + Arrays.toString(array));
		return file;

	}

	public static List<String> Parse(String str) {
		List<String> output = new ArrayList<String>();
		Matcher match = Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher(str);
		while (match.find()) {
			output.add(match.group());
		}
		return output;
	}

	public static void tabChange() {
		Keyboard key = ((HasInputDevices) driver).getKeyboard();
		key.sendKeys(Keys.TAB);

	}

	public static void enterkey() {
		Keyboard key = ((HasInputDevices) driver).getKeyboard();
		key.sendKeys(Keys.ENTER);
	}

	public static void ClearFields(String field) {
		try {
			webElement = findElement(field);
			webElement.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void DragAndDrop() {

	}
	
	public static void switchToActiveElement() {
		driver.switchTo().activeElement();
	}
	
	public static void navigateToMenu(String... menus) throws InterruptedException {
		for(String menu: menus) {
			try {
				Thread.sleep(250);
			}catch(Exception e) {}
			CommonUtil.click(CommonUtil.waitUntil(menu));
		}
	}

}// End Class
