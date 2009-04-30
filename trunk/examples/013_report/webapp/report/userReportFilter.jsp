<%@ page import="org.springside.core.utils.DateUtil,
                 java.util.Date" %>
<%--
  Author: efa
  Date: 2006-3-14
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <%@ include file="/components/calendar/calendar.jsp" %>
    <link href="<c:url value="/styles/admin.css"/>" type="text/css" rel="stylesheet">
</head>

<body>
<div class="pageTitle"><fmt:message key="biReport.saleReport.title"/></div>

<form name="mainform"
      action="<c:url value="/report/saleReport.do?method=view" />"
      method="post" target="_blank">
    <table class="border" width="90%" cellSpacing=0 cellPadding=2 align="center">
        <tr>
            <td class="left"><fmt:message key="biReport.saleReport.orderStatus"/>:</td>
            <td class="right">
                <select name="status">
                    <option value="all">所有</option>
                    <c:forEach var="entry" items="${statusEnum}">
                        <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="left"><fmt:message key="biReport.saleReport.shipDate"/>:</td>
            <td class="right">
                从:<input type="text" name="beginDate" value="2006-1-1" id="beginDate">
                <img id="calBeginButton" src="<c:url value="/components/calendar/skins/aqua/cal.gif"/>" border="0"
                     alt="选择日期" style="cursor: pointer;">
                到:<input type="text" name="endDate" value="<%=DateUtil.format(new Date(),"yyyy-M-d")%>" id="endDate">
                <img id="calEndButton" src="<c:url value="/components/calendar/skins/aqua/cal.gif"/>" border="0"
                     alt="选择日期" style="cursor: pointer;">
                <script type="text/javascript">
                    var cal1 = calendar("beginDate", "calBeginButton", "%Y-%m-%d");
                    var cal2 = calendar("endDate", "calEndButton", "%Y-%m-%d");
                </script>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="bottom">
                <input type="submit" class="submitButton" value="确定" style="margin-right:60px"/>
                <BUTTON onclick="history.back();">返回</BUTTON>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
