package com.family168;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReportBean {
    private static final String REPORT_FILTER_VIEW = "/report/userReportFilter.jsp";
    private static final String REPORT_DESESIGN_FILE = "/report/user_report_2.rptdesign";
    private static final String REPORT_IMAGE_DIR = "/report/images";

    /**
     * ReportService.
     */
    private ReportService reportService = null;

    /**
     * @param reportService ReportService.
     */
    public void setReportService(final ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * 显示报表.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    public BirtReportView view(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        Integer status = -1;

        try {
            status = Integer.parseInt(request.getParameter("status"));
        } catch (Exception ex) {
        }

        if (status == Integer.valueOf(-1)) {
            status = null;
        }

        List userList = reportService.findUsersByReportParam(status);

        BirtReportView view = new BirtReportView();
        view.openReportDesign(REPORT_DESESIGN_FILE);
        view.setImageDirectory(REPORT_IMAGE_DIR);
        view.putModel(userList);

        return view;
    }
}
