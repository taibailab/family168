package com.family168.drools.support;

import java.util.Iterator;

import com.family168.drools.RuleBaseLoader;
import com.family168.drools.RuleBaseManager;
import com.family168.drools.RuleTemplate;

import org.drools.FactHandle;
import org.drools.WorkingMemory;


/**
 * 内置的RuleBase管理对象
 *
 * @author Schweigen
 * @see RuleBaseManager
 */
public class RuleTemplateImpl implements RuleTemplate {
    private RuleBaseManager ruleBaseManager = new RuleBaseManager();

    public void setRuleBaseLoader(RuleBaseLoader ruleBaseLoader) {
        ruleBaseManager.setRuleBaseLoader(ruleBaseLoader);
    }

    /**
     * @see RuleTemplate#executeRules(String,Object...)
     */
    public Iterator executeRules(String groupName, Object... facts)
        throws Exception {
        WorkingMemory wm = ruleBaseManager.getSession(groupName);

        for (Object obj : facts) {
            /*
                * wm cannot assert null object, so it's necessary for validating the
                * object before assertion
                */
            if (null == obj) {
                continue;
            }

            FactHandle fact = wm.getFactHandle(obj);

            if (null == fact) {
                wm.insert(obj);
            } else {
                wm.update(fact, obj);
            }
        }

        wm.fireAllRules();

        return wm.iterateObjects();
    }

    /**
     * @see #executeRules(Object...)
     */
    public Iterator executeRules(Object... facts) throws Exception {
        return executeRules(null, facts);
    }
}
