
Ext.ns('App.security');

App.security = {
    createLoginWindow: function() {
        // 登录窗口
        App.loginWindow = new SpringSecurityLoginWindow({
            url: './j_spring_security_check',
            callback: function(result) {
                if (result.success) {
                    App.security.rebuildMenu(result.info);
                    App.loginWindow.hide();
                }
            }
        });
    },

    checkLogin: function() {
        // 检测用户是否已登录
        Ext.Ajax.request({
            url: './security/security!isLogin.do',
            success: function(response) {
                var result = Ext.decode(response.responseText);
                if (result.success) {
                    App.security.rebuildMenu(result.info);
                } else {
                    App.loginWindow.show();
                }
            },
            failure: function(response) {
                Ext.Msg.alert('错误', '无法访问服务器。');
            }
        });
    },

    rebuildMenu: function(info) {
        var mainAccordion = Ext.getCmp('mainAccordion');
        for (var i = 0; i < info.length; i++) {
            var p = new Ext.tree.TreePanel({
                title: info[i].text,
                iconCls: info[i].iconCls,
                rootVisible: false,
                loader: new Ext.tree.TreeLoader(),
                root: new Ext.tree.AsyncTreeNode({
                    text:'功能菜单',
                    children: info[i].children
                })
            });
            p.on('click', function(node) {
                if (node.attributes.url != '') {
                    App.addContent(node.attributes.url, node.attributes.text);
                }
            });
            mainAccordion.add(p);
        }
        mainAccordion.doLayout();
    },

    logout: function() {
        Ext.Ajax.request({
            url: './j_spring_security_logout',
            success: function(response) {
                var tabs = App.centerTabPanel;
                for (var i = tabs.items.length; i > 0; i--) {
                    var tab = tabs.getComponent(i);
                    tabs.remove(tab, true);
                }
                var mainAccordion = Ext.getCmp('mainAccordion');
                mainAccordion.removeAll();
                App.loginWindow.formPanel.getForm().reset();
                App.loginWindow.show();
            },
            failure: function(response) {
                Ext.Msg.alert('错误', '无法访问服务器。');
            }
        });
    }
};

