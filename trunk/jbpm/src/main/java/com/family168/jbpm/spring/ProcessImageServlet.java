package com.family168.jbpm.spring;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmContext;

import org.jbpm.graph.def.ProcessDefinition;


/**
 * 用来显示流程图的servlet.
 *
 * @author Lingo
 */
public class ProcessImageServlet extends HttpServlet {
    /** * serial. */
    private static final long serialVersionUID = 1L;

    /**
     * 处理GET请求.
     *
     * @param request 请求
     * @param response 相应
     * @throws ServletException servlet异常
     * @throws IOException io异常
     */
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        long processDefinitionId = Long.parseLong(request.getParameter(
                    "definitionId"));
        JbpmContext jbpmContext = JbpmUtils.getJbpmContext(request);

        ProcessDefinition processDefinition = jbpmContext.getGraphSession()
                                                         .loadProcessDefinition(processDefinitionId);
        byte[] bytes = processDefinition.getFileDefinition()
                                        .getBytes("processimage.jpg");
        OutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();

        // leave this in.  it is in case we want to set the mime type later.
        // get the mime type
        // String contentType = URLConnection.getFileNameMap().getContentTypeFor( fileName );
        // set the content type (=mime type)
        // response.setContentType( contentType );
    }
}
