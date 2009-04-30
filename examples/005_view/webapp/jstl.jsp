<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%!
    public static class User {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
${ctx}
<hr>
<%
    List list = new ArrayList();
    list.add("1");
    list.add("2");
    pageContext.setAttribute("list", list);

    User user = new User();
    user.setName("name");
    pageContext.setAttribute("user", user);

    Map map = new HashMap();
    map.put("1", "name1");
    map.put("2", "name2");
    pageContext.setAttribute("map", map);
%>
${fn:length(list)}
<hr>
${not empty list}
<hr>
${user.name} ${user["name"]}
<hr>
${list[0]}
<hr>
<c:forEach var="entry" items="${map}">
  ${entry.key} ${entry.value}
</c:forEach>
