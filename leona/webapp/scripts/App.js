
Ext.ns("App");


App.init = function() {
    // 开启提示功能
    Ext.QuickTips.init();
    // Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // 左侧功能菜单
    var accordion = createAccordion();
    App.accordion = accordion;

    // 登录窗口
    App.security.createLoginWindow();
    App.security.checkLogin();

    // 布局
    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [{
            region: 'north',
            height: 80,
            contentEl: 'nav_area',
            bbar: new Ext.Toolbar(['->', {
                text: 'WebIM',
                iconCls: 'cog',
                handler: function() {
                    webim.show();
                }
            }, {
                text: 'Setting',
                iconCls: 'config',
                handler: function() {
                }
            }, {
                text: 'Logout',
                iconCls: 'user_delete',
                handler: function() {
                    App.logout();
                }
            }])
        },{
            region: 'south',
            height: 18,
            bodyStyle: 'background-color:#99BBEE;',
            contentEl: 'state_area'
        },accordion,App.centerPanel]
    });

    setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 500);
};

Ext.onReady(App.init, App);
