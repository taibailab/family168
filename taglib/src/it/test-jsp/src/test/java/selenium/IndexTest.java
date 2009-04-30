package com.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.server.SeleniumServer;


public class IndexTest extends SeleneseTestCase {

    public void setUp() throws Exception {
        setUp("http://localhost:8080/", "*chrome");

        //setUp("http://localhost:8080/", "*iexplore");
        //setUp("http://localhost:8080/", "*opera");
        //setUp("http://localhost:8080/", "*safari");
    }


    public void tearDown() {
        selenium.stop();
    }

    public void testIndex() {
        selenium.open("/test-jsp/index.jsp");
        verifyTrue(selenium.isTextPresent("[首页]"));
    }
}
