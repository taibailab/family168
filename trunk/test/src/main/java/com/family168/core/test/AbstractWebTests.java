package com.family168.core.test;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


@TestExecutionListeners(inheritListeners = false, value =  {
    WebScopeTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class}
)
public abstract class AbstractWebTests
    extends AbstractJUnit38SpringContextTests {
}
