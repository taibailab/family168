package com.family168.jbpm;

import org.jbpm.pvm.internal.script.ScriptManager;


public class ScriptManagerFactory {
    public ScriptManager getScriptManager() {
        return ScriptManager.getDefaultScriptManager();
    }
}
