package automation.eunimart.pageobjects;

import java.util.Properties;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import Eunimart.automation.commonutils.CommonUtil;
import Eunimart.automation.commonutils.PropertiesFileReader;
import Eunimart.automation.commonutils.ReadExcel;

public class SignUp_SignIn_Flow {

	public static Properties elementProperties;

	static {
		elementProperties = PropertiesFileReader.getInstance().readProperties("eunimart.element.properties");

	}

	static ExtentReports logger = ExtentReports.get(SignUp_SignIn_Flow.class);

	public static void verifySignUp_SignIn_Flow() {
		try {
			logger.startTest("Verify the Functionality of Eunimart SignUp_SignIn Process flow");
			CommonUtil.click(elementProperties.getProperty("Eunimart.SignUp"));
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"),
					ReadExcel.getCellData(6, 1, "Eunimart"));
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"),
					ReadExcel.getCellData(7, 1, "Eunimart"));
			logger.log(LogStatus.INFO, "User Enter the Passowrd");

			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Passwordconfirmation"),
					ReadExcel.getCellData(7, 1, "Eunimart"));
			logger.log(LogStatus.INFO, "user Re-Enter the Password");

			CommonUtil.click(elementProperties.getProperty("Eunimart.Checkbox"));
			logger.log(LogStatus.INFO, "User Select the I agree checkbox");

			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.SignUp.Register"));
			logger.log(LogStatus.INFO, "User Click on Register Button");
			CommonUtil.wait(1);
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.OTP"),
					ReadExcel.getCellData(8, 1, "Eunimart"));
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

			CommonUtil.wait(1);

			CommonUtil.click(elementProperties.getProperty("Eunimart.SingUp.Goback"));
			logger.log(LogStatus.INFO, "user Click on the Go Back button");

			CommonUtil.wait(5);

			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Email"),
					ReadExcel.getCellData(6, 1, "Eunimart"));
			logger.log(LogStatus.INFO, "User Enter the Email Id");
			CommonUtil.enterText(elementProperties.getProperty("Eunimart.Enter.Password"),
					ReadExcel.getCellData(7, 1, "Eunimart"));
			logger.log(LogStatus.INFO, "User Enter the Invalid Passowrd");
			CommonUtil.click(elementProperties.getProperty("Eunimart.keepsign"));
			logger.log(LogStatus.INFO, "User Select the Keep Sign in checkbox");
			CommonUtil.wait(1);
			CommonUtil.click(elementProperties.getProperty("Eunimart.Signin.Button"));
			CommonUtil.wait(2);
			logger.log(LogStatus.INFO, "User Click on Sign-in Button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
