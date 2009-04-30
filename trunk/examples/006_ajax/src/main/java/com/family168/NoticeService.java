package com.family168;

import java.util.Collection;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;


public class NoticeService {
    public void send(String message) {
        WebContext wctx = WebContextFactory.get();
        ScriptBuffer script = new ScriptBuffer();
        script.appendScript("receiveMessages('").appendData(message)
              .appendScript("');");

        ServerContext sctx = ServerContextFactory.get(wctx
                .getServletContext());
        Collection<ScriptSession> pages = sctx.getScriptSessionsByPage(
                "/ajax/dwr-push.html");

        for (ScriptSession session : pages) {
            session.addScript(script);
        }
    }
}
