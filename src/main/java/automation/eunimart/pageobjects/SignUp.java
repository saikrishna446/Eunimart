package automation.eunimart.pageobjects;

import java.util.Properties;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import Eunimart.automation.commonutils.CommonUtil;
import Eunimart.automation.commonutils.PropertiesFileReader;

public class SignUp extends CommonUtil {

	public static Properties elementProperties;

	static {
		elementProperties = PropertiesFileReader.getInstance().readProperties("eunimart.element.properties");

	}
	static ExtentReports logger = ExtentReports.get(SignUp.class);

	public static void verifytheNavigationEunimartPage() {
		try {

			logger.startTest("Verify the Navigation of the Eunimart Home Page");
			CommonUtil.wait(5);
			String objtitle = CommonUtil.getTitle();
			System.out.println(objtitle);
			if (objtitle.equalsIgnoreCase("Vdezi")) {
				logger.log(LogStatus.PASS, "User Succesfully Open the Eunimart Home Page");
			} else {
				logger.log(LogStatus.FAIL, "User Un-Succesfully Open the Eunimart Home Page");
				CommonUtil.TakeScreenShot("verifytheNavigationEunimartPage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void verifythefunctionalityofSignUpvalidation() {
		try {
			logger.startTest("verify the functionality of SignUp Validations with already user Email id");
			CommonUtil.click(elementProperties.getProperty("Eunimart.SignUp"));
			logger.log(LogStatus.INFO, "User Click on SignUp Tab");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"), "test12@gmail.com");
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"), "Sai@12345");
			logger.log(LogStatus.INFO, "User Enter the Passowrd");

			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Passwordconfirmation"), "Sai@12345");
			logger.log(LogStatus.INFO, "user Re-Enter the Password");

			CommonUtil.click(elementProperties.getProperty("Eunimart.Checkbox"));
			logger.log(LogStatus.INFO, "User Select the I agree checkbox");
			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.SignUp.Register"));
			logger.log(LogStatus.INFO, "User Click on Register Button");
			CommonUtil.wait(1);
			if (CommonUtil.findElements(elementProperties.getProperty("Eunimart.SignUp.Validation")).size() > 0) {

				String objsingup = CommonUtil.getText(elementProperties.getProperty("Eunimart.SignUp.Validation"));
				System.out.println(objsingup);
				if (objsingup.equalsIgnoreCase("This email is already is in use.")) {
					logger.log(LogStatus.PASS, "System Displays the Error Message " + objsingup);
				}

			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void verifytheSingupOTPvalidation() {
		try {

			logger.startTest("Veerify the functionality of OTP Validation");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"), "test7712589@gmail.com");
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"), "Sai@12345");
			logger.log(LogStatus.INFO, "User Enter the Passowrd");

			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Passwordconfirmation"), "Sai@12345");
			logger.log(LogStatus.INFO, "user Re-Enter the Password");

//			CommonUtil.click(elementProperties.getProperty("Eunimart.Checkbox"));
//			logger.log(LogStatus.INFO, "User Select the I agree checkbox");
			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.SignUp.Register"));
			logger.log(LogStatus.INFO, "User Click on Register Button");
			CommonUtil.wait(1);
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.OTP"), "456789");
			CommonUtil.wait(1);
			logger.log(LogStatus.INFO, "User Enter the Incorrect OTP");
			CommonUtil.click(elementProperties.getProperty("Eunimart.VerifyMove.button"));
			CommonUtil.wait(1);
			logger.log(LogStatus.INFO, "User Click on the Verify Move Button");
			if (CommonUtil.findElement(elementProperties.getProperty("Eunimart.Enter.OTPvalidation")).isDisplayed()) {
				String objsingupOTP = CommonUtil.getText(elementProperties.getProperty("Eunimart.Enter.OTPvalidation"));
				System.out.println(objsingupOTP);
				if (objsingupOTP.equalsIgnoreCase("Incorrect OTP.")) {
					logger.log(LogStatus.PASS, "System Displays the Error Message " + objsingupOTP);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
