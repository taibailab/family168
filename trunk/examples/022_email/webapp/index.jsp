<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="org.springframework.mail.SimpleMailMessage"%>
<%@page import="org.springframework.mail.javamail.JavaMailSender"%>
<%@page import="org.springframework.mail.javamail.MimeMessageHelper"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    JavaMailSender mailSender = (JavaMailSender) ctx.getBean("mailSender");
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("from");
    msg.setTo("to");
    msg.setSubject("subject");
    msg.setText("text");

    String content = "mail";
    MimeMessage mimeMsg = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
    helper.setTo(msg.getTo());
    helper.setSubject(msg.getSubject());
    helper.setFrom(msg.getFrom());
    helper.setText(content, true);
    msg.setText(content);
    mailSender.send(msg);
%>
