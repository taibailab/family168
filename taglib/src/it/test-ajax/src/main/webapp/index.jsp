<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/pager" prefix="pager"%>
<%
    request.setCharacterEncoding("UTF-8");
    pageContext.setAttribute("totalCount", 500);
    pageContext.setAttribute("pageSize", 15);
    pageContext.setAttribute("params", "name,code");
%>
<html>
    <head>
        <title></title>
        <script type="text/javascript">
function jumpFunction(pageNo) {
    alert(pageNo);
}
        </script>
    </head>
    <body>
        <form action="index.jsp" method="post">
            <input type="text" name="name" value="中"/>
            <input type="text" name="code" value=""/>
            <input type="submit">
        </form>
        <pager:pager totalCount="${totalCount}" pageSize="${pageSize}" params="${params}" jumpFunction="jumpFunction">
        <table border="1" width="100%">
            <tr>
                <td><pager:first>[首页]</pager:first></td>
                <td><pager:backward step="5">[跳后]</pager:backward></td>
                <td><pager:prev>[后退]</pager:prev></td>
                <td>
                    <pager:index>
                        <pager:isCurrentPage><span style="background-color:red">${pageNumber}</span></pager:isCurrentPage>
                        <pager:notCurrentPage><a href="${pageUrl}">${pageNumber}</a></pager:notCurrentPage>
                    </pager:index>
                </td>
                <td><pager:next>[前进]</pager:next></td>
                <td><pager:forward step="5">[跳前]</pager:forward></td>
                <td><pager:last>[尾页]</pager:last></td>
                <td>第${currentPageNo}页 / 总${pageCount}页</td>
            </tr>
        </table>
        </pager:pager>
    </body>${param.name}
</html>
