package com.family168;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.ReportEngine;

import org.springframework.util.Assert;


/**
 * Birt 与 Spring MVC 结合的View.
 * 参考JasperReportsView 编写
 *
 * @author efa
 * @author Lingo
 * @since 2007-03-27
 * @version 1.0
 */
public class BirtReportView {
    /**
     * 输出byte数组的大小.
     */
    private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(BirtReportView.class);

    /**
     * 报表引擎.
     */
    private ReportEngine engine;

    /**
     * 查询结果列表.
     */
    private List resultList;

    /**
     * 设计文件的在web应用中的相对路径,不含context path.
     * eg. /plugins-demo/birt/sale_report.rptdesign
     */
    private String designFilePath = null;

    /**
     * 图片生成目录在web应用中的相对路径,,不含context path
     * eg. "/plugins-demo/birt/sale_report.rptdesign/birt/images
     */
    private String imageDirectory = null;

    /**
     * jo?
     */
    private String scriptableJOName = "dsFactory";

    /**
     * 在页面上显示的图片链接,是与请求url的相对路径.
     */
    private String baseImageUrl = "images";

    /**
     * @param imageDirectoryIn 图片路径.
     */
    public void setImageDirectory(final String imageDirectoryIn) {
        imageDirectory = imageDirectoryIn;
    }

    /**
     * @param baseImageUrlIn 图片url.
     */
    public void setBaseImageUrl(final String baseImageUrlIn) {
        baseImageUrl = baseImageUrlIn;
    }

    /**
     * @param scriptableJONameIn jo?
     */
    public void setScriptableJOName(final String scriptableJONameIn) {
        scriptableJOName = scriptableJONameIn;
    }

    /**
     * 打开报表设计文件(.rptdesign).
     *
     * @param filePath 文件路径
     * @throws IOException io异常
     * @throws EngineException 引擎异常
     */
    public void openReportDesign(final String filePath)
        throws IOException, EngineException {
        designFilePath = filePath;
    }

    /**
     * 放入查询结果数据.
     *
     * @param list List
     */
    public void putModel(final List list) {
        resultList = list;
    }

    /**
     * 重载的View渲染函数.
     *
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(Map,HttpServletRequest,HttpServletResponse)
     * @param model 数据模型
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     */
    public void renderMergedOutputModel(final Map model,
        final HttpServletRequest request,
        final HttpServletResponse response) throws Exception {
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");

        OutputStream outputStream = response.getOutputStream();

        EngineConfig birtConfig = new EngineConfig();
        birtConfig.setEngineHome(context.getRealPath(
                "/WEB-INF/ReportEngine/"));
        engine = new ReportEngine(birtConfig);

        try {
            run(outputStream, context);
        } catch (EngineException e) {
            //throw new Exception("the View not run() yet");
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);

        response.setContentLength(baos.size());

        try {
            baos.writeTo(outputStream);
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            outputStream.flush();
        }
    }

    /**
     * birt 运行.
     *
     * @param outputStream 输出流
     * @param context ServletContext
     * @throws EngineException 引擎异常
     * @throws Exception 异常
     */
    private void run(final OutputStream outputStream,
        final ServletContext context) throws EngineException, Exception {
        Assert.hasText(designFilePath, "Set designFilePath first!");

        //config the htmlrendercontext
        HTMLRenderContext renderContext = new HTMLRenderContext();
        renderContext.setImageDirectory(context.getRealPath(imageDirectory));
        renderContext.setBaseImageURL(baseImageUrl);

        HashMap contextMap = new HashMap();
        contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
            renderContext);

        IReportRunnable design = null;
        logger.info(designFilePath);
        logger.info(context.getRealPath(designFilePath));
        logger.info(engine);
        design = engine.openReportDesign(context.getRealPath(
                    designFilePath));

        IRunAndRenderTask task = engine.createRunAndRenderTask(design);
        task.setAppContext(contextMap);

        BirtDataSourceObject birtDataSourceObject = new BirtDataSourceObject();
        HTMLRenderOption options = new HTMLRenderOption();
        birtDataSourceObject.setResultList(resultList);
        options.setOutputStream(outputStream);
        task.setRenderOption(options);
        task.addScriptableJavaObject(scriptableJOName, birtDataSourceObject);
        task.run();
    }
}
