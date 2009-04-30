<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.*"%>
<%@page import="org.compass.core.support.search.CompassSearchResults"%>
<%
    String queryString = request.getParameter("queryString");
    if (queryString != null && !queryString.trim().equals("")) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
        CompassSearchService compassSearchService = (CompassSearchService) ctx.getBean("compassSearchService");
        AdvancedSearchCommand searchCommand = new AdvancedSearchCommand();
        searchCommand.setQuery(queryString);
        searchCommand.setHighlightFields(new String[]{"name"});

        CompassSearchResults searchResults = compassSearchService.search(searchCommand);
        request.setAttribute("searchResults", searchResults);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <title>Compass Demo</title>
    </head>
    <body>
        <form>
            <input type="text" name="queryString">
            <input type="submit">boy, girl, child
        </form>
        <div class="content">
            <div class="left">
<c:if test="${not empty searchResults}">
                elapsed: ${searchResults.searchTime}ms
    <c:if test="${empty searchResults.hits}">
        没有找到符合条件的结果。<span style="color:red;font-weight:bold">请确保已初始化索引。</span>
    </c:if>
    <c:forEach var="hit" items="${searchResults.hits}">
        <c:choose>
            <c:when test="${hit.alias == 'child'}">
                <div class="left_content">
                    <p>
                        <a href="#" class="title"><c:if test="${empty hit.highlightedText['name']}">${hit.data.name}</c:if>${hit.highlightedText["name"]}</a>
                        <br/>
                        Id: ${hit.data.id}
                        <br/>
                        Parent: ${hit.data.parent.name}
                        <br/>
                    </p>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</c:if>
            </div>
<c:if test="${! empty searchResults.pages}">
            <div class="left_pages">
        <c:forEach var="page" items="${searchResults.pages}" varStatus="pagesStatus">
            <c:choose>
                <c:when test="${page.selected}">
                    ${page.from}-${page.to}items
                </c:when>
                <c:otherwise>
                    <a href="javascript:$('advanceSearch').page.value=${pagesStatus.index};$('advanceSearch').submit();">${page.from}-${page.to}条</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
            </div>
</c:if>

        </div>
    </body>
</html>
