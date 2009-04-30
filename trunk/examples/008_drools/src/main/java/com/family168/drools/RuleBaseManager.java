/*
 * RuleBaseManager.java
 *
 * Created on 2006-4-28
 * Original Author: Schweigen
 *
 * Changes
 * ---------
 * $Log: $
 *
 */
package com.family168.drools;

import org.apache.commons.lang.StringUtils;

import org.drools.RuleBase;
import org.drools.WorkingMemory;


/**
 * 缓存与管理RuleBase 的类.
 * 在JBoss Rules的后继版本中,对不断加强对规则库的管理,加入热切换规则等功能.
 * 以后将随JBoss Rules的开发而升级.
 *
 * @author Schweigen
 */
public class RuleBaseManager {
    private RuleBaseLoader ruleBaseLoader;
    private RuleBase ruleBase;

    public void setRuleBaseLoader(RuleBaseLoader ruleBaseLoader) {
        this.ruleBaseLoader = ruleBaseLoader;
    }

    /**
     * @see RuleBaseManager#getSession(String)
     */
    public WorkingMemory getSession(String ruleGroup) throws Exception {
        if (ruleBase == null) {
            ruleBase = ruleBaseLoader.buildRuleBase();
        }

        WorkingMemory wm = ruleBase.newStatefulSession();

        // 设置focus的规则组，组名称在drl中定义，如agenda-group "OrderPricing"
        if (StringUtils.isNotEmpty(ruleGroup)) {
            wm.setFocus(ruleGroup);
        }

        return wm;
    }

    /**
     * @see RuleBaseManager#getSession(String)
     */
    public WorkingMemory getSession() throws Exception {
        return getSession(null);
    }
}
