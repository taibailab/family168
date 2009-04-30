package com.family168.taglib.jodd;

import java.util.Locale;


/**
 * Html utils for working with tag's names and attributes.
 * 来自http://jodd.sourceforge.net
 *
 * @author najgor@users.sourceforge.net
 * @author Lingo
 * @since 2007-03-17
 * @version 1.0
 */
public final class HtmlUtil {
    /**
     * 工具类的私有构造方法.
     */
    private HtmlUtil() {
    }

    // ---------------------------------------------------------------- tag name

    /**
     * Returns tag's name. Given string represents a HTML body of a tag,
     * therefore it <b>must</b> start with '<'.
     *
     * @param tagBody tag's body
     *
     * @return tag's name, or <code>null</code> if tag not found
     */
    public static String getTagName(String tagBody) {
        return getTagName(tagBody, 0);
    }

    /**
     * Returns tag's name. Given string represents a HTML body and given starting index
     * <b>must</b> be the index of tag's start (i.e. '<').
     * <p>
     *
     * Names of ending tags will always start with '/' character.
     *
     * @param body   hmtl body
     * @param i      index of tag's start
     *
     * @return tag's name, or <code>null</code> if tag not found
     */
    public static String getTagName(String body, int i) {
        if (body == null) {
            return null;
        }

        if (body.charAt(i) != '<') {
            return null; // no tag
        }

        int start = i + 1; // skip '<'
        int len = body.length();
        boolean isEndTag = false;

        // skip all non-letters
        while (start < len) {
            char c = body.charAt(start);

            if (c == '>') {
                return null; // tag end found => name not found
            }

            if (c == '/') { // this is an end tag
                start++;
                isEndTag = true;

                continue;
            }

            if (!Character.isWhitespace(c)) {
                break;
            }

            start++;
        }

        if (start == len) {
            return null; // tag name not found
        }

        int end = start;

        // skip all letters
        while (end < len) {
            char c = body.charAt(end);

            if (Character.isWhitespace(c) || (c == '>')) {
                break;
            }

            end++;
        }

        if (end == len) {
            return null; // tag end not found
        }

        String tagName = body.substring(start, end);

        if (isEndTag) {
            tagName = "/" + tagName;
        }

        return tagName;
    }

    // ---------------------------------------------------------------- tag attribute

    /**
     * Returns value of the first founded attribute that matches given name.
     * It is assumed that given string represents tag's body.
     * Note: attribute <b>must</b> end with the <code>="</code> or <code>='</code>.
     * Attribute name is not case sensitive.
     *
     * @param tagBody  tag body
     * @param attrName attribute name
     *
     * @return attribute value or <code>null</code> if attribute not found
     */
    public static String getAttribute(String tagBody, String attrName) {
        return getAttribute(tagBody, attrName, 0);
    }

    /**
     * Returns value of the first founded attribute that matches given name.
     * Given string may not be just a tag's body, however, start and end
     * parameters must define tags body.
     * Note: attribute <b>must</b> end with the <code>="</code> or <code>='</code>.
     * Attribute name is not case sensitive.
     *
     * @param body     html body
     * @param attrName attribute name
     * @param start    index of tag's start
     *
     * @return attribute value or <code>null</code> if attribute not found
     */
    public static String getAttribute(String body, String attrName,
        int start) {
        if (body == null) {
            return null;
        }

        char quote = '\"';
        int end = body.indexOf('>');

        if (end == -1) {
            return null; // tag's end not found
        }

        int i = indexOfIgnoreCase(body, attrName + "=\"", start);

        if ((i == -1) || (i > end)) {
            i = indexOfIgnoreCase(body, attrName + "='", start);

            if ((i == -1) || (i > end)) {
                return null;
            }

            quote = '\'';
        }

        String value = null;
        i += (attrName.length() + 2);

        int s = i;
        int j = -1;

        while (true) {
            j = body.indexOf(quote, s);

            if (j == -1) {
                break; // closed quation not found
            }

            if (body.charAt(j - 1) == '\\') {
                s = j + 1;

                continue;
            } else {
                value = body.substring(i, j);

                break;
            }
        }

        return value;
    }

    // ---------------------------------------------------------------- add attribute & value

    /**
     * Adds attribute and its value to a tag. Attribute is added to the end of
     * the tag, just before closing '>'. If name is not specified, nothing will
     * be added. If value is not specified, it will be set to an empty string.
     *
     * @param tagBody tag body
     * @param name    attribute name
     * @param value   attribute value
     *
     * @return tag string with added attribute and value
     */
    public static String addAttribute(String tagBody, String name,
        String value) {
        return addAttribute(tagBody, name, value, 0);
    }

    /**
     * Adds attribute and its value to a tag. Attribute is added to the end of
     * the tag, just before closing '>'. If name is not specified, nothing will
     * be added. If value is not specified, it will be set to an empty string.
     *
     * @param body   html body
     * @param name   attribute name
     * @param value  attribute value
     * @param i      tag's offset in html body
     *
     * @return tag string with added attribute and value
     */
    public static String addAttribute(String body, String name,
        String value, int i) {
        if (body == null) {
            return null;
        }

        if (name == null) {
            return body;
        }

        if (value == null) {
            value = "";
        }

        int end = body.indexOf('>', i);

        if (end == -1) {
            return body;
        }

        StringBuffer result = new StringBuffer(body.length());
        result.append(body.substring(i, end)).append(' ');
        result.append(name).append('=').append('"');
        //result.append(ServletUtil.encodeHtml(value)).append('"');
        result.append(HtmlEncoder.encode(value)).append('"');
        result.append(body.substring(end));

        return result.toString();
    }

    // ---------------------------------------------------------------- add attribute, no value

    /**
     * Adds single attribute without value to a tag. Attribute is added to the
     * end of the tag, just before closing '>'. If name is not specified, nothing
     * will be added.
     *
     * @param tagBody tag body
     * @param name    attribute name
     *
     * @return tag string with added attribute
     */
    public static String addAttribute(String tagBody, String name) {
        return addAttribute(tagBody, name, 0);
    }

    /**
     * Adds single attribute without value to a tag. Attribute is added to the
     * end of the tag, just before closing '>'. If name is not specified, nothing
     * will be added.
     *
     * @param body   html body
     * @param name   attribute name
     * @param i      tag's offset in html body
     *
     * @return tag string with added attribute
     */
    public static String addAttribute(String body, String name, int i) {
        if (body == null) {
            return null;
        }

        if (name == null) {
            return body;
        }

        int end = body.indexOf('>', i);

        if (end == -1) {
            return body;
        }

        StringBuffer result = new StringBuffer(body.length());
        result.append(body.substring(i, end)).append(' ');
        result.append(name).append(body.substring(end));

        return result.toString();
    }

    /**
     * Finds first index of a substring in the given source string with ignored
     * case. This seems to be the fastest way doing this, with common string
     * length and content (of course, with no use of Boyer-Mayer type of
     * algorithms). Other implementations are slower: getting char array frist,
     * lowercasing the source string, using String.regionMatch etc.
     *
     * @param src        source string for examination
     * @param subS       substring to find
     * @param startIndex starting index from where search begins
     *
     * @return index of founded substring or -1 if substring is not found
     */
    public static int indexOfIgnoreCase(String src, String subS,
        int startIndex) {
        String sub = subS.toLowerCase(Locale.CHINA);
        int sublen = sub.length();
        int total = src.length() - sublen + 1;

        for (int i = startIndex; i < total; i++) {
            int j = 0;

            while (j < sublen) {
                char source = Character.toLowerCase(src.charAt(i + j));

                if (sub.charAt(j) != source) {
                    break;
                }

                j++;
            }

            if (j == sublen) {
                return i;
            }
        }

        return -1;
    }
}
