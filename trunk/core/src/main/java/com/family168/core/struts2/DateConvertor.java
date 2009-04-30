package com.family168.core.struts2;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


/**
 * 日期转换器.
 *
 * @author Lingo
 */
public class DateConvertor extends StrutsTypeConverter {
    /** * 日期格式化器. */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yy年MM月dd日");

    /**
     * 字符串转换成日期.
     *
     * @param context 上下文
     * @param value 数据
     * @param toType 转换成什么类型
     * @return 日期
     */
    @Override
    public Object convertFromString(Map context, String[] value,
        Class toType) {
        if (Date.class == toType) {
            try {
                return dateFormat.parse(value[0]);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

        return null;
    }

    /**
     * 转换成字符串.
     *
     * @param context 上下文
     * @param o 对象
     * @return 字符串
     */
    @Override
    public String convertToString(Map context, Object o) {
        if (o != null) {
            return dateFormat.format((Date) o);
        } else {
            return null;
        }
    }
}
