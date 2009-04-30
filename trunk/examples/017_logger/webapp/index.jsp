<%@page contentType="text/html;charset=UTF-8"%>
<%
    System.out.println("");
    System.out.println("");
    // commons-logging
    org.apache.commons.logging.Log logger1 = org.apache.commons.logging.LogFactory.getLog(this.getClass());
    logger1.info("logger1 info");

    // log4j
    org.apache.log4j.Logger logger2 = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    logger2.info("logger2 info");

    // jdk4 log
    java.util.logging.Logger logger3 = java.util.logging.Logger.getLogger(this.getClass().getName());
    logger3.info("logger3 info");

    // slf4j
    org.slf4j.Logger logger4 = org.slf4j.LoggerFactory.getLogger(this.getClass());
    logger4.info("logger4 info");

%>

