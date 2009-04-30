package com.family168.drools;

import org.drools.RuleBase;


/**
 * RuleBase Loader接口.
 * 实现类将从数据库或drl文件中读取规则并构建RuleBase.
 *
 * @author calvin
 */
public interface RuleBaseLoader {
    public RuleBase buildRuleBase() throws Exception;
}
