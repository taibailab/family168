<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<%@page import="java.util.*"%>
<%@page import="com.family168.security.domain.Dept"%>
<%!
    String renderDeptList(List<Dept> deptList) {
        StringBuffer buff = new StringBuffer();
        int len = deptList.size();
        for (int i = 0; i < len; i++) {
            Dept dept = deptList.get(i);
            renderDept(buff, dept, i == 0, i == len - 1);
        }
        return buff.toString();
    }

    void renderDept(StringBuffer buff, Dept dept, boolean isFirst, boolean isLast) {
        buff.append("<li>");
        String cls = null;
        if (isFirst) {
            cls = "first";
        } else if (isLast) {
            cls = "last";
        }
        if (!dept.isLeaf()) {
            cls += " section";
        }
        buff.append("<div class=\"")
            .append(cls)
            .append("\"><a href=\"#\">")
            .append(dept.getName())
            .append("</a></div>");
        if (!dept.isLeaf()) {
            buff.append("<ul>");
            int index = 0;
            int len = dept.getChildren().size();
            for (Dept child : dept.getChildren()) {
                renderDept(buff, child, index == 0, index == len - 1);
                index++;
            }
            buff.append("</ul>");
        }
        buff.append("</li>");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="/commons/meta.jsp"%>
    <title>组织结构</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/orgmap/orgmap.css" />
  </head>
  <body>
    <h1>组织结构图</h1>
    <hr/>
    <div id="contain">
      <ul id="map" class="solo">
        <li>
          <div class="root section"><a href="#">组织结构</a></div>
          <ul>
            <%=renderDeptList((List<Dept>)request.getAttribute("list"))%>
      </ul>
    </div>
<%--
<#macro renderDept dept isFirst isLast>
  <li>
    <div class="<#if isFirst>first<#elseif isLast>last</#if><#if dept.leaf!=true> section</#if>"><a href="#">${dept.name!}</a></div>
  <#if dept.leaf!=true>
    <ul>
    <#list dept.children! as child>
      <@renderDept child child_index==0 child_has_next!=true/>
    </#list>
    </ul>
  </#if>
  </li>
</#macro>
    <div id="contain">
      <ul id="map" class="solo">
        <li>
          <div class="root section"><a href="#">组织结构</a></div>
          <ul>
<#list deptList! as dept>
  <@renderDept dept dept_index==0 dept_has_next!=true/>
</#list>
      </ul>
    </div>
--%>
  </body>
</html>
