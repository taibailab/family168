package com.family168;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;


public class ReportServlet extends HttpServlet {
    protected void service(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        try {
            ApplicationContext ctx = null;
            ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

            ReportBean reportBean = (ReportBean) ctx
                .getBean("reportBean");
            System.out.println(reportBean);

            BirtReportView view = reportBean.view(request, response);
            System.out.println(view);
            view.renderMergedOutputModel(null, request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
