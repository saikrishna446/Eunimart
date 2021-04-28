package automation.eunimart.pageobjects;

import java.util.Properties;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import Eunimart.automation.commonutils.CommonUtil;
import Eunimart.automation.commonutils.PropertiesFileReader;

public class SignIn {

	public static Properties elementProperties;

	static {
		elementProperties = PropertiesFileReader.getInstance().readProperties("eunimart.element.properties");

	}
	static ExtentReports logger = ExtentReports.get(SignIn.class);

	public static void verifySigninInputDataValidations() {
		try {
			logger.startTest("Verify the functionality validation of Sign-in Input Data validation");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"), "test12@gmail.com");
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"), "Sai@");
			logger.log(LogStatus.INFO, "User Enter the Passowrd only 4 charcters");
			CommonUtil.click(elementProperties.getProperty("Eunimart.keepsign"));
			logger.log(LogStatus.INFO, "User Select the Keep Sign in checkbox");
			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.Signin.Button"));
			CommonUtil.wait(2);
			logger.log(LogStatus.INFO, "User Click on Sign-in Button");
			if (CommonUtil.findElement(elementProperties.getProperty("Eunimart.EmailVerification2")).isDisplayed()) {

				String objsingin = CommonUtil.getText(elementProperties.getProperty("Eunimart.EmailVerification2"));
				System.out.println(objsingin);
				if (objsingin.equalsIgnoreCase("Error in Input Validations.")) {
					logger.log(LogStatus.PASS, "System Displays the Error Message " + objsingin);
				} else if (CommonUtil.findElement(elementProperties.getProperty("Eunimart.EmailVerification"))
						.isDisplayed()) {
					String objsingin1 = CommonUtil
							.getText(elementProperties.getProperty("Eunimart.EmailVerification2"));
					System.out.println(objsingin1);
					if (objsingin1.equalsIgnoreCase("Incorrect Username or Password.")) {
						logger.log(LogStatus.PASS, "System Displays the Error Message " + objsingin1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void verifySigninUserEmailPassWordValidations() {
		try {
			logger.startTest("Verify the functionality validation of Sign-in Incorrect Username&Password validation");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"), "test12@gmail.com");
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"), "Sai@12345");
			logger.log(LogStatus.INFO, "User Enter the Invalid Passowrd");
			CommonUtil.click(elementProperties.getProperty("Eunimart.keepsign"));
			logger.log(LogStatus.INFO, "User Select the Keep Sign in checkbox");
			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.Signin.Button"));
			CommonUtil.wait(2);
			logger.log(LogStatus.INFO, "User Click on Sign-in Button");
			if (CommonUtil.findElement(elementProperties.getProperty("Eunimart.EmailVerification")).isDisplayed()) {

				String objsingin1 = CommonUtil.getText(elementProperties.getProperty("Eunimart.EmailVerification"));
				System.out.println(objsingin1);
				if (objsingin1.equalsIgnoreCase("Incorrect Username or Password.")) {
					logger.log(LogStatus.PASS, "System Displays the Error Message " + objsingin1);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
