package com.family168.drools;

import java.util.Iterator;


/**
 * 规则引擎的使用接口. 屏蔽规则引擎的所有实现细节
 *
 * @author anders
 * @author Schweigen
 */
public interface RuleTemplate {
    /**
     * 执行规则
     *
     * @param facts     参与规则运算的事实列表
     * @param groupName 所执行规则的分组名称，如果为null ，则为默认的"MAIN"<br>
     *                  分组名称在drl文件中定义，如agenda-group "OrderPricing"
     */
    public Iterator executeRules(String groupName, Object... facts)
        throws Exception;

    /**
     * @see #executeRules(String,Object...)
     */
    public Iterator executeRules(Object... facts) throws Exception;
}
