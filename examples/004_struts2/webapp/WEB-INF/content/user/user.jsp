<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<s:actionmessage/>
<table border="1">
    <tr>
        <td>1</td>
        <td><a href="user!show.do?id=1" target="_blank">name 1</a></td>
        <td><a href="user!input.do?id=1">update</a> / <a href="user!remove.do?id=1">remove</a></td>
    </tr>
    <tr>
        <td>2</td>
        <td><a href="user!show.do?id=2" target="_blank">name 2</a></td>
        <td><a href="user!input.do?id=2">update</a> / <a href="user!remove.do?id=2">remove</a></td>
    </tr>
</table>
<a href="user!input.do">create user</a>
