package com.family168.jbpm.spring;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

import org.dom4j.xpath.DefaultXPath;

import org.jbpm.JbpmContext;

import org.jbpm.file.def.FileDefinition;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.Token;

import org.jbpm.taskmgmt.exe.TaskInstance;


/**
 * 这个是用来显示流程图，并定位当前所在的节点.
 *
 * @author Lingo
 */
public class ProcessImageTag extends TagSupport {
    /** serial. */
    private static final long serialVersionUID = 1L;

    /** * logger. */
    private static Log logger = LogFactory.getLog(ProcessImageTag.class);

    /** * 类型，显示当前任务的流程节点位置. */
    public static final int TYPE_TASK = 0;

    /** * 类型，显示当前token的流程节点位置. */
    public static final int TYPE_TOKEN = 1;

    /** 默认的边框宽度. */
    public static final int DEFAULT_BORDER_WIDTH = 4;

    /** 左边距. */
    public static final int DEFAULT_LEFT_OFFSET = 10;

    /** 高度. */
    public static final int DEFAULT_HEIGHT = 20;

    /** * 当前token的颜色. */
    private String currentTokenColor = "red";

    /** * 子token颜色. */
    private String childTokenColor = "blue";

    /** * token名称颜色. */
    private String tokenNameColor = "blue";

    /** * 任务id. */
    private long taskInstanceId = -1L;

    /** * token的id. */
    private long tokenInstanceId = -1L;

    /** * 保存流程图节点位置xml的数据. */
    private byte[] gpdBytes = null;

    /**
     * 保存流程图，图片的数据.
     * 这个可以判断此流程是否发布了图片，如果没有发布，就不显示了。
     */
    private byte[] imageBytes = null;

    /** * 当前token，需要TYPE_TOKEN才会用到. */
    private Token currentToken = null;

    /** * 当前使用的流程定义. */
    private ProcessDefinition processDefinition = null;

    /** * 显示的类型. */
    private int type;

    /** * 当前所在的节点名称. */
    private String nodeName = null;

    /**
     * 标签结束后，对使用的变量进行清理.
     * 主要是怕这个是单例模式，对以后的调用造成影响，至于他究竟是不是单例模式，我也不知道。
     */
    @Override
    public void release() {
        this.taskInstanceId = -1L;
        this.tokenInstanceId = -1L;
        this.gpdBytes = null;
        this.imageBytes = null;
        this.currentToken = null;
        this.type = -1;
        this.nodeName = null;
    }

    /**
     * 这了是标签的入口，一切都是从这里开始，也是从这里结束的.
     *
     * @return EVAL_PAGE继续解析剩下的页面内容
     * @throws JspException 可能抛出jsp异常
     */
    @Override
    public int doEndTag() throws JspException {
        try {
            // 初始化需要的数据，(nodeName或currentToken)和processDefinition
            this.initialize();
            // 根据processDefinition获得gpd.xml，这里包含了流程图各个节点的位置
            this.retrieveByteArrays();

            // 只有在流程图位置信息和流程图片都存在的情况下，才绘制table
            if ((this.gpdBytes != null) && (this.imageBytes != null)) {
                this.writeTable();
            }
        } catch (IOException e) {
            logger.error(e, e);
            throw new JspException("table couldn't be displayed", e);
        } catch (DocumentException e) {
            logger.error(e, e);
            throw new JspException("table couldn't be displayed", e);
        }

        this.release();

        return EVAL_PAGE;
    }

    /**
     * 初始化一些数据.
     *
     * 如果类型是task，就获得taskNode的名称
     * 如果类型是token，就获得当前token的实例
     * 然后顺便计算出ProcessDefinition来，把他的id传给后台就能获得流程图
     */
    private void initialize() {
        JbpmContext jbpmContext = JbpmUtils.getJbpmContext(pageContext);

        if (this.type == TYPE_TASK) {
            TaskInstance taskInstance = jbpmContext.getTaskMgmtSession()
                                                   .loadTaskInstance(this.taskInstanceId);
            this.nodeName = taskInstance.getTask().getTaskNode().getName();
            this.processDefinition = taskInstance.getTaskMgmtInstance()
                                                 .getProcessInstance()
                                                 .getProcessDefinition();
        } else if (this.type == TYPE_TOKEN) {
            this.currentToken = jbpmContext.getGraphSession()
                                           .loadToken(this.tokenInstanceId);
            this.nodeName = this.currentToken.getNode().getName();
            this.processDefinition = this.currentToken.getProcessInstance()
                                                      .getProcessDefinition();
        }
    }

    /**
     * 根据processDefinition获得gpd.xml和processimage.jpg.
     * gpd.xml里包含了流程图各个节点的位置，
     * processimage.jpg可以判断这个流程定义是否发布了流程图片，如果没有发布，就不显示了。
     */
    private void retrieveByteArrays() {
        try {
            FileDefinition fileDefinition = processDefinition
                .getFileDefinition();
            this.gpdBytes = fileDefinition.getBytes("gpd.xml");
            this.imageBytes = fileDefinition.getBytes("processimage.jpg");
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    /**
     * 开始绘制显示的html表格.
     *
     * @throws IOException 可能出现io异常
     * @throws DocumentException 可能出现xml解析异常
     */
    private void writeTable() throws IOException, DocumentException {
        int borderWidth = DEFAULT_BORDER_WIDTH;

        // 获得流程图根节点
        Element rootDiagramElement = DocumentHelper.parseText(new String(
                    gpdBytes)).getRootElement();

        // 获得总图形的宽和高
        int width = this.getAttribute(rootDiagramElement, "width");
        int height = this.getAttribute(rootDiagramElement, "height");

        // 生成显示图片的url
        String imageLink = ((HttpServletRequest) pageContext.getRequest())
            .getContextPath() + "/processimage?definitionId="
            + processDefinition.getId();

        // 准备开始往jsp里写
        JspWriter jspOut = pageContext.getOut();

        if (this.type == TYPE_TOKEN) {
            List allTokens = new ArrayList();
            this.walkTokens(currentToken, allTokens);

            jspOut.println(
                "<div style='position:relative; background-image:url("
                + imageLink + "); width: " + width + "px; height: "
                + height + "px;'>");

            for (int i = 0; i < allTokens.size(); i++) {
                Token token = (Token) allTokens.get(i);

                //check how many tokens are on teh same level (= having the same parent)
                int offset = i;

                if (i > 0) {
                    while ((offset > 0)
                            && ((Token) allTokens.get(offset - 1)).getParent()
                                    .equals(token.getParent())) {
                        offset--;
                    }
                }

                Box box = extractBoxConstraint(rootDiagramElement,
                        token.getNode().getName());

                jspOut.println("<div style='position:absolute; left: "
                    + box.getX() + "px; top: " + box.getY() + "px; ");

                if (i == (allTokens.size() - 1)) {
                    jspOut.println("border: " + currentTokenColor);
                } else {
                    jspOut.println("border: " + childTokenColor);
                }

                // Adjust for borders
                // firefox与ie使用border显示的方式不同，用!important进行调整
                // ie不认识!important，就使用后面的长宽
                int adjustWidth = box.getW() - (borderWidth * 2);
                int adjustHeight = box.getH() - (borderWidth * 2);
                jspOut.println(" " + borderWidth + "px groove; "
                    + "width: " + adjustWidth + "px !important; "
                    + "height: " + adjustHeight + "px !important; "
                    + "width: " + box.getW() + "px; height: " + box.getH()
                    + "px;'>");

                if (token.getName() != null) {
                    jspOut.println("<span style='color:" + tokenNameColor
                        + ";font-style:italic;position:absolute;left:"
                        + (box.getX() + DEFAULT_LEFT_OFFSET) + "px;top:"
                        + ((i - offset) * DEFAULT_HEIGHT) + ";'>&nbsp;"
                        + token.getName() + "</span>");
                }

                jspOut.println("</div>");
            }

            jspOut.println("</div>");
        } else if (this.type == TYPE_TASK) {
            // 绘制task的位置
            Box box = this.extractBoxConstraint(rootDiagramElement,
                    this.nodeName);

            jspOut.println(
                "<table border=0 cellspacing=0 cellpadding=0 width="
                + width + " height=" + height + ">");
            jspOut.println("  <tr>");
            jspOut.println("    <td width=" + width + " height=" + height
                + " style=\"background-image:url(" + imageLink
                + ")\" valign=top>");
            jspOut.println(
                "      <table border=0 cellspacing=0 cellpadding=0>");
            jspOut.println("        <tr>");
            jspOut.println("          <td width="
                + (box.getX() - borderWidth) + " height="
                + (box.getY() - borderWidth)
                + " style=\"background-color:transparent;\"></td>");
            jspOut.println("        </tr>");
            jspOut.println("        <tr>");
            jspOut.println(
                "          <td style=\"background-color:transparent;\"></td>");
            jspOut.println("          <td style=\"border-color:"
                + currentTokenColor + "; border-width:" + borderWidth
                + "px; border-style:groove; background-color:transparent;\" width="
                + box.getW() + " height="
                + (box.getH() + (2 * borderWidth)) + ">&nbsp;</td>");
            jspOut.println("        </tr>");
            jspOut.println("      </table>");
            jspOut.println("    </td>");
            jspOut.println("  </tr>");
            jspOut.println("</table>");
        }
    }

    /**
     * 根据节点名称，从xml中查询，然后获得x,y,width,height的数据.
     *
     * @param root xml根节点
     * @param currentNodeName 节点名称
     * @return 包含的方位数据
     */
    private Box extractBoxConstraint(Element root, String currentNodeName) {
        XPath xPath = new DefaultXPath("//node[@name='" + currentNodeName
                + "']");
        Element node = (Element) xPath.selectSingleNode(root);
        Box box = new Box(this.getAttribute(node, "x"),
                this.getAttribute(node, "y"),
                this.getAttribute(node, "width"),
                this.getAttribute(node, "height"));

        return box;
    }

    /**
     * 从element里获得属性，并将属性值转换成int.
     *
     * @param element xml节点
     * @param attributeName 属性名
     * @return 转换成int的属性值
     */
    private int getAttribute(Element element, String attributeName) {
        try {
            return Integer.parseInt(element.attribute(attributeName)
                                           .getValue());
        } catch (NumberFormatException ex) {
            logger.warn(ex, ex);

            return 0;
        }
    }

    /**
     * 遍历token.
     *
     * @param parent 上级token
     * @param allTokens 所有token
     */
    private void walkTokens(Token parent, List allTokens) {
        Map children = parent.getChildren();

        if ((children != null) && (children.size() > 0)) {
            Collection childTokens = children.values();

            for (Iterator iterator = childTokens.iterator();
                    iterator.hasNext();) {
                Token child = (Token) iterator.next();
                walkTokens(child, allTokens);
            }
        }

        allTokens.add(parent);
    }

    /** * @param id 设置task的id. */
    public void setTask(long id) {
        this.type = TYPE_TASK;
        this.taskInstanceId = id;
    }

    /** * @param id 设置token的id. */
    public void setToken(long id) {
        this.type = TYPE_TOKEN;
        this.tokenInstanceId = id;
    }

    /** * 内部类. 保存x,y,width,height四个数据. */
    public static class Box {
        /** * x. */
        private int x;

        /** * y. */
        private int y;

        /** * w. */
        private int w;

        /** * h. */
        private int h;

        /**
         * x,y和宽高.
         *
         * @param x int
         * @param y int
         * @param w int
         * @param h int
         */
        public Box(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        /** * @return x. */
        public int getX() {
            return x;
        }

        /** * @return y. */
        public int getY() {
            return y;
        }

        /** * @return w. */
        public int getW() {
            return w;
        }

        /** * @return h. */
        public int getH() {
            return h;
        }
    }
}
