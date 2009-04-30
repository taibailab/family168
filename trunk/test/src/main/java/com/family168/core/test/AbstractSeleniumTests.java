package com.family168.core.test;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;


public class AbstractSeleniumTests extends SeleneseTestCase {
    public static final String CHROME = "*chrome";
    public static final String FF = "*firefox";
    public static final String IE = "*iexplore";
    public static final String OPERA = "*opera";
    public static final String SAFARI = "*safari";
    protected Selenium user;

    @Override
    public void setUp(String url, String browserString)
        throws Exception {
        super.setUp(url, browserString);
        this.user = this.selenium;
    }
}
