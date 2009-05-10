<%@page contentType="text/html;charset=UTF-8"%>
<%pageContext.setAttribute("ext", "scripts/ext-2.0.2");%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="styles/ext-patch.css" />
    <link rel="stylesheet" type="text/css" href="styles/ext-customer.css" />
    <link rel="stylesheet" type="text/css" href="styles/menu.css" />
    <script type="text/javascript" src="${ext}/ext-base.js"></script>
    <script type="text/javascript" src="${ext}/ext-all.js"></script>
    <script type="text/javascript" src="${ext}/ext-lang-zh_CN.js"></script>

    <script type="text/javascript" src="./scripts/ux/Autogrid.js"></script>
    <script type="text/javascript" src="./scripts/ux/cvi_glossy_lib.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.JsonGrid.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.PlainGrid.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.EditGrid.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.MetaGrid.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.JsonTree.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.lingo.GlossyPanel.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.ux.LoginWindow.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.ux.PageSizePlugin.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.ux.ClearableCombo.js"></script>
    <script type="text/javascript" src="./scripts/ux/Ext.ux.SpringSecurityLoginWindow.js"></script>
    <script type="text/javascript" src="./scripts/ux/patcher.js"></script>
    <script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ext}/resources/images/default/s.gif';
    </script>
    <title>OA</title>
  </head>
  <body>
    <div id="loading-mask" style=""></div>
    <div id="loading">
        <div class="loading-indicator"><img src="styles/login/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>正在加载数据...</div>
    </div>
    <div id="nav_area" class="x-date-bottom">
        <table cellspacing="0" border="0" cellpadding="0" width="100%">
            <tr>
                <td><div style="font-size:18px;font-weight:bold;"><img src="images/logo.gif"></div></td>
                <td align="right">OA</td>
            </tr>
        </table>
    </div>
    <div id="state_area" style="color:white;font-size:12px;font-weight:bold;">
        <script type="text/javascript">
            document.write(new Date());
        </script>
    </div>
    <link rel="stylesheet" type="text/css" href="scripts/checkboxtree/Ext.lingo.JsonCheckBoxTree.css" />
    <script type="text/javascript" src="./scripts/checkboxtree/Ext.lingo.JsonCheckBoxTree.js"></script>
    <script type="text/javascript" src="./scripts/treefield/Ext.lingo.TreeField.js"></script>
    <script type="text/javascript" src="./scripts/webim/webim.js"></script>
    <script type="text/javascript" src="./scripts/miframe/miframe-min.js"></script>
    <script type="text/javascript" src="./scripts/App.js"></script>
    <script type="text/javascript" src="./scripts/App.Sex.js"></script>
    <script type="text/javascript" src="./scripts/App.Combo.js"></script>
    <script type="text/javascript" src="./scripts/App.DeptField.js"></script>
    <script type="text/javascript" src="./scripts/App.RescGrid.js"></script>
    <script type="text/javascript" src="./scripts/App.RoleGrid.js"></script>
    <script type="text/javascript" src="./scripts/App.UserGrid.js"></script>
    <script type="text/javascript" src="./scripts/App.MenuTree.js"></script>
    <script type="text/javascript" src="./scripts/App.DeptTree.js"></script>
    <script type="text/javascript" src="./scripts/App.Grid.js"></script>
    <script type="text/javascript" src="./scripts/App.Accordion.js"></script>

    <script type="text/javascript" src="./scripts/App.security.js"></script>
    <script type="text/javascript" src="./scripts/App.view.js"></script>
<%--
--%>
  </body>
</html>
