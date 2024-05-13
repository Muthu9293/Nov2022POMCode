package com.qa.opencart.tests;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;



public class LoginPageTest extends BaseTest {
	
    //private final Logger logger = Logger.getLogger(LoginPageTest.class);


	@Test(priority = 1)
	public void loginPageTitleTest() {
       // MDC.put("testClassName", this.getClass().getSimpleName());
        //logger.info("This is a log message from loginPageTitleTest");

		String actualTitle = loginPage.getLoginPageTitle();
		//logger.info("actual login page title: " + actualTitle);
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Test(priority = 2)
	public void loginPageURLTest() {
        //MDC.put("testClassName", this.getClass().getSimpleName());
        //logger.info("This is a log message from loginPageURLTest");

		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
        //MDC.put("testClassName", this.getClass().getSimpleName());
        //logger.info("This is a log message from forgotPwdLinkExistTest");

		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	
	@Test(priority = 4)
	public void loginTest() {
        //MDC.put("testClassName", this.getClass().getSimpleName());
        //logger.info("This is a log message from loginTest");

		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}