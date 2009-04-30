package com.family168.taglib.jodd;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyContent;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.dao.DataIntegrityViolationException;


public class FormTagTest extends TestCase {
    FormTag tag = null;

    @Override
    protected void setUp() {
        tag = new FormTag();
        tag.setBeans("test");
        tag.setScopes("request");
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        assertEquals("test", tag.getBeans());
        assertEquals("request", tag.getScopes());
    }

    public void testDoStartTag() {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Object())
            .anyTimes();

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);

        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testDoStartTag_inPage() {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        EasyMock.expect(pageContext.getAttribute("test"))
                .andReturn(new Object()).anyTimes();
        expect(request.getAttribute("test")).andReturn(new Object())
            .anyTimes();

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);

        tag.setBeans("test");
        tag.setScopes(",request,application");
        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testDoStartTag_inPage2() {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        EasyMock.expect(pageContext.getAttribute("test"))
                .andReturn(new Object()).anyTimes();

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);

        tag.setBeans("test");
        tag.setScopes("page");
        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testDoStartTag_inSession() {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(session.getAttribute("test")).andReturn(new Object())
            .anyTimes();

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);

        tag.setBeans("test");
        tag.setScopes("session,application");
        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testDoStartTag_inApplication() {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(session.getAttribute("test")).andReturn(new Object())
            .anyTimes();

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);

        tag.setBeans("test");
        tag.setScopes("application");
        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testAll() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "";

        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(session.getAttribute("test")).andReturn(new Object())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(html);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("application");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testAll2() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "<input type='text' name=\"name\">"
            + "<input type='hidden' name='name'>"
            + "<select name='name'><option value='bean'>bean</option></select>"
            + "<textarea name='name'>test</textarea>"
            + "<input type='image' name='name'>"
            + "<input type='checkbox' name='name' value='bean'>"
            + "<input type='password' name='name'>"
            + "<input type='radio' name='name' value='bean'>"
            + " plain text <input <";

        String dest = "<input type='text' name=\"name\" value=\"bean\">"
            + "<input type='hidden' name='name' value=\"bean\">"
            + "<select name='name'><option value='bean' selected>bean</option></select>"
            + "<textarea name='name'>beantest</textarea>"
            + "<input type='image' name='name' value=\"bean\">"
            + "<input type='checkbox' name='name' value='bean' checked>"
            + "<input type='password' name='name' value=\"bean\">"
            + "<input type='radio' name='name' value='bean' checked>"
            + " plain text <input <";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testAll3() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "plain text";

        String dest = "plain text";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testAll4() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "<input type>" + "<input type='text' name>"
            + "<input type='text' name='nil'>";

        String dest = "<input type>" + "<input type='text' name>"
            + "<input type='text' name='nil' value=\"\">";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testCheckbox() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "<input type='checkbox' name='name'>"
            + "<input type='checkbox' name='name' value='other'>"
            + "<input type='button' name='nil'>"
            + "<input type='radio' name='nil'>"
            + "<input type='radio' name='nil' value='other'>";

        String dest = "<input type='checkbox' name='name'>"
            + "<input type='checkbox' name='name' value='other'>"
            + "<input type='button' name='nil'>"
            + "<input type='radio' name='nil'>"
            + "<input type='radio' name='nil' value='other'>";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testTextarea() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "<textarea name='other'></textarea>"
            + "<textarea name='nil'></textarea>";

        String dest = "<textarea name='other'></textarea>"
            + "<textarea name='nil'></textarea>";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public void testSelect() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        BodyContent bodyContent = EasyMock.createMock(BodyContent.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);
        String html = "<select name='other'></select>"
            + "<option value='output select'>output select</option>"
            + "<select name='nil'><option></option></select>"
            + "<select name='other'><option value='test'>test</option></select>"
            + "<select name='nil'><option value='bean'>bean</option></select>"
            + "<select name='types'><option value='1'>1</option><option value='2'>2</option></select>"
            + "<select name='name'><option value='1'>1</option></select>"
            + "<select name='objects'><option value='object'>object</option></select>";

        String dest = "<select name='other'></select>"
            + "<option value='output select'>output select</option>"
            + "<select name='nil'><option></option></select>"
            + "<select name='other'><option value='test'>test</option></select>"
            + "<select name='nil'><option value='bean'>bean</option></select>"
            + "<select name='types'><option value='1' selected>1</option><option value='2' selected>2</option></select>"
            + "<select name='name'><option value='1'>1</option></select>"
            + "<select name='objects'><option value='object' selected>object</option></select>";
        EasyMock.expect(pageContext.getRequest()).andReturn(request)
                .anyTimes();
        EasyMock.expect(pageContext.getSession()).andReturn(session)
                .anyTimes();
        expect(request.getAttribute("test")).andReturn(new Bean())
            .anyTimes();
        EasyMock.expect(bodyContent.getEnclosingWriter())
                .andReturn(jspWriter).anyTimes();
        EasyMock.expect(bodyContent.getString()).andReturn(html).anyTimes();
        jspWriter.print(dest);

        EasyMock.replay(pageContext);
        replay(request);
        replay(session);
        EasyMock.replay(bodyContent);
        EasyMock.replay(jspWriter);

        tag.setBeans("test");
        tag.setScopes("request");
        tag.setPageContext(pageContext);
        tag.setBodyContent(bodyContent);
        tag.doStartTag();
        tag.doAfterBody();
        tag.doEndTag();
        EasyMock.verify();
    }

    public class Bean {
        String name = "bean";
        String nil = null;
        String[] types = new String[] {"1", "2", null};
        Object[] objects = new Object[] {"object", null};

        public void setName(String nameIn) {
            name = nameIn;
        }

        public String getName() {
            return name;
        }

        public void setNil(String nil2) {
            nil = nil2;
        }

        public String getNil() {
            return nil;
        }

        public void setTypes(String[] types2) {
            types = types2;
        }

        public String[] getTypes() {
            return types;
        }

        public void setObjects(Object[] objects2) {
            objects = objects2;
        }

        public Object[] getObjects() {
            return objects;
        }
    }
}
