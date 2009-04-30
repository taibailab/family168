package com.family168.core.struts2;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


/**
 * 整型转换器.
 *
 * @author Lingo
 */
public class IntegerConvertor extends StrutsTypeConverter {
    /**
     * 从字符串转换成整型.
     *
     * @param context 上下文
     * @param value 数据
     * @param toType 转换成什么类型
     * @return 整型
     */
    @Override
    public Object convertFromString(Map context, String[] value,
        Class toType) {
        if (Integer.class == toType) {
            try {
                return Integer.valueOf(value[0]);
            } catch (NumberFormatException ex) {
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
            return o.toString();
        } else {
            return null;
        }
    }
}
