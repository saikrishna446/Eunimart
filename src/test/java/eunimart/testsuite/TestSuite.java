package eunimart.testsuite;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;

import Eunimart.automation.commonutils.CommonUtil;
import Eunimart.automation.commonutils.ReadExcel;
import automation.eunimart.pageobjects.SignIn;
import automation.eunimart.pageobjects.SignUp;
import automation.eunimart.pageobjects.SignUp_SignIn_Flow;

public class TestSuite {

	public static Properties elementProperties;

	ExtentReports extent = ExtentReports.get(TestSuite.class);

	/*
	 * This are the mandatory fields before executing the script Opening the Browser
	 * and URL
	 * 
	 */
	@BeforeTest
	public void beforeTest() {
		extent.init(CommonUtil.filePath, true);
		CommonUtil.openBrowser(ReadExcel.getCellData(2, 3, "Eunimart"));
		CommonUtil.openUrl(ReadExcel.getCellData(1, 3, "Eunimart"));
		CommonUtil.windowmaximize();
		CommonUtil.getDimensions();
		CommonUtil.createFloder();
	}

	/*
	 * Eunimart SingUp
	 */
	@Test(priority = 1, enabled = false)
	public void verifySignUpprocessEunimart() {

		SignUp.verifytheNavigationEunimartPage();
		SignUp.verifythefunctionalityofSignUpvalidation();
		SignUp.verifytheSingupOTPvalidation();
	}
	/*
	 * Eunimart SignIn
	 */
	@Test(priority = 2, enabled = false)
	public void verifySignInprocessEunimart(){

		SignUp.verifytheNavigationEunimartPage();
		SignIn.verifySigninInputDataValidations();
		SignIn.verifySigninUserEmailPassWordValidations();
	}
	
	/*
	 * Eunimart SignUp&SignIn Process
	 */
	@Test(priority = 3, enabled = true)
	public void verifySignUpSignInprocessEunimart(){

		SignUp.verifytheNavigationEunimartPage();
		SignUp_SignIn_Flow.verifySignUp_SignIn_Flow();
	}
	
	/*
	 * This are the mandatory fields after executing the script close the Browser
	 */
	@AfterTest
	public void afterTest() throws InterruptedException {
		CommonUtil.closeBrowser();
	}
}
