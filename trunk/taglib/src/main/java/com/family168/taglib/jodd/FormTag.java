package com.family168.taglib.jodd;

import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Form tag populates a included form with values from the one or more beans.
 * Beans are stored in various scopes. This tag is <i>smart</i> so everything
 * happens automatically. However, there are several easy-to-work-with limits
 * that must be fulfilled to make this tag working properly:
 * <ul>
 * <li>All values attributes must not exist.</li>
 * <li>Textarea content must be empty.</li>
 * <li>All<sup>*</sup> attributes and its names must be directly connected with the
 * <code>="</code> or <code>='</code>.</li>
 * <li>All<sup>*</sup> attributes values must be surrounded by quotes (" or ').</li>
 * <li>of course, there should be no duplicated names, both across the form and beans.</li>
 * </ul>
 *
 * <sup>*</sup>note: not all, just: name, value and type attributes, however, it is
 * recommended to use the above style everywhere.
 * <p>
 *
 * Availiable scopes are:
 * <ul>
 * <li>page,</li>
 * <li>session, and</li>
 * <li>request</li>
 * </ul>
 * 来自http://jodd.sourceforge.net
 *
 * @author najgor@users.sourceforge.net
 * @author Lingo
 * @since 2007-03-17
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class FormTag extends BodyTagSupport {
    /**
     * 持久化.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 日志.
     */
    private static Log logger = LogFactory.getLog(FormTag.class);

    // ---------------------------------------------------------------- tag parameters
    /**
     * bean的名字，可以用,或空格分隔.
     */
    private String beanNames = null;

    /**
     * 保存bean的范围，page, request, session.
     */
    private String scopes = null;

    // ---------------------------------------------------------------- tag methods
    /**
     * 保存所有bean的值的map.
     */
    private HashMap beansValues = null;

    /**
     * Sets bean names with value of the "bean" attribute.
     *
     * @param v      bean names
     */
    public void setBeans(String v) {
        beanNames = v;
    }

    /**
     * Gets bean names.
     *
     * @return bean names
     */
    public String getBeans() {
        return beanNames;
    }

    /**
     * Sets the value of "scope" attribute, that represent beans scope.
         * 可能是page, request, session。默认是page
     *
     * @param v scopes
     */
    public void setScopes(String v) {
        scopes = v;
    }

    /**
     * Return value of the "scope" attribute.
     *
     * @return bean scopes
     */
    public String getScopes() {
        return scopes;
    }

    /**
     * Copies properties of all specified bean into one map.
     *
     * @return EVAL_BODY_AGAIN
     */
    public int doStartTag() {
        beansValues = new HashMap();

        //String[] b = StringUtil.splitc(beanNames, ", ");
        //String[] s = StringUtil.splitc(scopes.toLowerCase(), ", ");
        String[] b = StringUtils.split(beanNames, ", ");
        String[] s = StringUtils.split(scopes.toLowerCase(Locale.CHINA),
                ", ");

        //logger.info(java.util.Arrays.asList(b));
        //logger.info(java.util.Arrays.asList(s));
        HttpServletRequest request = (HttpServletRequest) pageContext
            .getRequest();
        HttpSession session = (HttpSession) pageContext.getSession();

        for (int i = 0; i < b.length; i++) {
            Object bean = null;

            if ((s[i].length() == 0) || (s[i].equals("page"))) {
                bean = pageContext.getAttribute(b[i]);
            } else if (s[i].equals("request")) {
                bean = request.getAttribute(b[i]);
            } else if (s[i].equals("session")) {
                bean = session.getAttribute(b[i]);
            }

            try {
                if (bean != null) {
                    //Map map = BeanUtils.describe(bean);
                    Map map = PropertyUtils.describe(bean);
                    //beansValues.putAll((Map) BeanUtil.getAllProperties(bean));
                    beansValues.putAll(map);

                    //logger.info(map);
                    //logger.info(beansValues);
                }
            } catch (IllegalAccessException ex) {
                logger.error(ex);
            } catch (InvocationTargetException ex) {
                logger.error(ex);
            } catch (NoSuchMethodException ex) {
                logger.error(ex);
            }
        }

        return EVAL_BODY_AGAIN;
    }

    /**
     * Performs smart form population.
     *
     * @return SKIP_BODY
     */
    public int doAfterBody() {
        BodyContent body = getBodyContent();

        try {
            JspWriter out = body.getEnclosingWriter();
            String bodytext = body.getString();

            if ((beansValues != null) && (beansValues.size() > 0)) {
                bodytext = populateForm(bodytext, beansValues);
            }

            out.print(bodytext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * End of tag.
     *
     * @return EVAL_PAGE
     */
    public int doEndTag() {
        return EVAL_PAGE;
    }

    // ---------------------------------------------------------------- populate
    /**
     * 把bean的数值组装到html中.
     *
     * @param html 原始html数据
     * @param values bean的数据
     * @return 返回组装的结果
     */
    private String populateForm(String html, HashMap values) {
        int i = 0;
        int s = 0;
        StringBuffer result = new StringBuffer(html.length());
        String currentSelectName = null;

        while (true) {
            // find starting tag
            i = html.indexOf('<', s);

            if (i == -1) {
                result.append(html.substring(s));

                break; // input tag not found
            }

            result.append(html.substring(s, i)); // tag found, all before tag is stored
            s = i;

            // find closing tag
            i = html.indexOf('>', i);

            if (i == -1) {
                result.append(html.substring(s));

                break; // closing tag not found
            }

            i++;

            // match tags
            String tag = html.substring(s, i);
            String tagName = HtmlUtil.getTagName(tag);

            if (tagName.equalsIgnoreCase("input")) {
                String tagType = HtmlUtil.getAttribute(tag, "type");

                if (tagType != null) {
                    String name = HtmlUtil.getAttribute(tag, "name");

                    if (values.containsKey(name)) {
                        //String value = StringUtil.toString(values.get(name));
                        String value = null;

                        if (values.get(name) != null) {
                            value = values.get(name).toString();
                        }

                        tagType = tagType.toLowerCase(Locale.CHINA);

                        if (tagType.equals("text")) {
                            tag = HtmlUtil.addAttribute(tag, "value", value);
                        } else if (tagType.equals("hidden")) {
                            tag = HtmlUtil.addAttribute(tag, "value", value);
                        } else if (tagType.equals("image")) {
                            tag = HtmlUtil.addAttribute(tag, "value", value);
                        } else if (tagType.equals("password")) {
                            tag = HtmlUtil.addAttribute(tag, "value", value);
                        } else if (tagType.equals("checkbox")) {
                            String tagValue = HtmlUtil.getAttribute(tag,
                                    "value");

                            if (tagValue == null) {
                                tagValue = "true";
                            } else if (tagValue.equals(value)) {
                                tag = HtmlUtil.addAttribute(tag, "checked");
                            }
                        } else if (tagType.equals("radio")) {
                            String tagValue = HtmlUtil.getAttribute(tag,
                                    "value");

                            if ((tagValue != null)
                                    && tagValue.equals(value)) {
                                tag = HtmlUtil.addAttribute(tag, "checked");
                            }
                        }
                    }
                }
            } else if (tagName.equalsIgnoreCase("textarea")) {
                String name = HtmlUtil.getAttribute(tag, "name");

                if (values.containsKey(name)) {
                    Object value = values.get(name);

                    if (value != null) {
                        //tag += ServletUtil.encodeHtml(StringUtil.toString(value));
                        //tag += HtmlEncoder.encode(StringUtil.toString(
                        //        value));
                        //if (values.get(name) != null) {
                        tag += HtmlEncoder.encode(values.get(name)
                                                        .toString());
                    } else {
                        tag += HtmlEncoder.encode(null);

                        //}
                    }
                }
            } else if (tagName.equalsIgnoreCase("select")) {
                currentSelectName = HtmlUtil.getAttribute(tag, "name");
            } else if (tagName.equalsIgnoreCase("/select")) {
                currentSelectName = null;
            } else if (tagName.equalsIgnoreCase("option")
                    && (currentSelectName != null)) {
                String tagValue = HtmlUtil.getAttribute(tag, "value");

                if ((tagValue != null)
                        && values.containsKey(currentSelectName)) {
                    Object vals = values.get(currentSelectName);

                    //logger.info(vals);
                    //logger.info(vals.getClass());
                    //logger.info(values);
                    if (vals != null) {
                        if (!vals.getClass().isArray()) {
                            if (vals.toString().equals(tagValue)) {
                                tag = HtmlUtil.addAttribute(tag, "selected");
                            }
                        } else {
                            String[] vs = convertToStringArray(vals);

                            for (int k = 0; k < vs.length; k++) {
                                String vsk = vs[k];

                                if ((vsk != null) && vsk.equals(tagValue)) {
                                    tag = HtmlUtil.addAttribute(tag,
                                            "selected");
                                }
                            }
                        }
                    }
                }
            }

            result.append(tag);
            s = i;
        }

        return result.toString();
    }

    /**
     * 把Object转换成String[].
     *
     * @param value 原始object
     * @return String[]
     */
    private String[] convertToStringArray(Object value) {
        if (value == null) {
            // 如果参数为null
            return new String[0];
        } else if (value.getClass().isArray()) {
            // 如果参数类型为数组
            if (value instanceof String[]) {
                return (String[]) value;
            } else {
                // 否则就是Object[]数组，不可能是基础类型数组吗？
                // FIXME: int[]
                Object[] valueArray = (Object[]) value;
                String[] result = new String[valueArray.length];

                for (int i = 0; i < valueArray.length; i++) {
                    result[i] = valueArray[i].toString();
                }

                return result;
            }
        } else {
            // 其他类型
            return new String[] {value.toString()};
        }
    }
}
