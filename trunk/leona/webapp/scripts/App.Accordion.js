
createAccordion = function() {
    return {
        id: 'mainAccordion',
        region: 'west',
        title: '功能菜单',
        layout: 'accordion',
        width: 150,
        minSize: 120,
        maxSize: 200,
        split: true,
        collapsible: true,
        border: true,
        defaults: {
            lines: false,
            autoScroll: true,
            collapseFirst: true
        }
    };
/*
    return {
        id: 'mainAccordion',
        region: 'west',
        title: '功能菜单',
        width: 150,
        minSize: 120,
        maxSize: 200,
        split: true,
        collapsible: true,
        border: true,
        layout: 'accordion',
        defaults: {
            //bodyStyle: 'text-align:center;padding:15px',
            border: true,
            frame: true,
            autoScroll: true,
            collapseFirst: true
        },
        layoutConfig: {
            titleCollapse: true,
            hideCollapseTool: false,
            animate: true,
            activeOnTop: false,
            renderHidden: false,
            border: true
        },
        tbar: new Ext.Toolbar(['->',{
            text: '注销',
            iconCls: 'user_delete',
            handler: App.logout
        }]),
        items: [new Ext.lingo.GlossyPanel({
            title: '权限管理',
            glossy: [
                {id:'resc', name:'resc', url:'', img:'images/gnome-application-pdf.png', cls:'', title:'资源'},
                {id:'role', name:'role', url:'', img:'images/gnome-application-pgp-encrypted.png', cls:'', title:'角色'},
                {id:'user', name:'user', url:'', img:'images/gnome-application-pgp-keys.png', cls:'', title:'用户'},
                {id:'menu', name:'menu', url:'', img:'images/redhat-applications.png', cls:'', title:'菜单'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '部门管理',
            glossy: [
                {id:'dept', name:'dept', url:'', img:'images/gnome-application-msword.png', cls:'', title:'部门'},
                {id:'org', name:'org', url:'', img:'images/redhat-preferences.png', cls:'', title:'组织机构'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '公告管理',
            glossy: [
                {id:'add-affice',name:'add-affice',url:'', img:'images/gnome-application-msword.png',cls:'',title:'发布最新公告'},
                {id:'view-affice',name:'view-affice',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看以往公告'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '公文管理',
            glossy: [
                {id:'send-bumf',name:'send-bumf',url:'', img:'images/gnome-application-msword.png',cls:'',title:'发送公文'},
                {id:'accept-bumf',name:'accept-bumf',url:'', img:'images/gnome-application-msword.png',cls:'',title:'接收公文'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '员工管理',
            glossy: [
                {id:'view-emp',name:'view-emp',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看员工'},
                {id:'list-emp',name:'list-emp',url:'', img:'images/gnome-application-msword.png',cls:'',title:'浏览员工'},
                {id:'add-emp',name:'add-emp',url:'', img:'images/gnome-application-msword.png',cls:'',title:'添加员工'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '部门管理',
            glossy: [
                {id:'view-dept',name:'view-dept',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看部门'},
                {id:'view-job',name:'view-job',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看职位'},
                {id:'view-state',name:'view-state',url:'', img:'images/gnome-application-msword.png',cls:'',title:'状态'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '收发信息',
            glossy: [
                {id:'view-msg',name:'view-msg',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看信息'},
                {id:'send-msg',name:'send-msg',url:'', img:'images/gnome-application-msword.png',cls:'',title:'发送短信'}
            ]
        }), new Ext.lingo.GlossyPanel({
            title: '优秀员工管理',
            glossy: [
                {id:'add-best',name:'add-best',url:'', img:'images/gnome-application-msword.png',cls:'',title:'优秀员工提名'},
                {id:'view-best',name:'view-best',url:'', img:'images/gnome-application-msword.png',cls:'',title:'查看优秀员工'}
            ]
        })]
    };
*/
};

App.logout = function() {
    Ext.Ajax.request({
        url: './j_spring_security_logout',
        success: function(response) {
            App.loginWindow.formPanel.getForm().reset();
            App.loginWindow.show();
        },
        failure: function(response) {
            Ext.Msg.alert('错误', '无法访问服务器。');
        }
    });
};


App.addContent = function(name, title, url) {
    var tabs = App.centerTabPanel;
    //var tabItem = tabs.getItem('auto');
    //if (tabItem == null) {
    //    tabItem = tabs.add(new Ext.lingo.MetaGrid({
    //        id: 'auto'
    //    }));
    //}

    //tabItem.urlSubmit = name + '!submit.do'
    //tabItem.setTitle(title);
    //tabItem.store.proxy.conn.url = name + "!pagedQuery.do";
    //tabItem.store.load({params:{meta:true,start:0,limit:15}});
    //tabs.activate(tabItem);

    var tabItem = tabs.getItem(name);
    if (tabItem == null) {
        if (name == 'dept') {
            tabItem = tabs.add(new App.DeptTree());
        } else if (name == 'menu') {
            tabItem = tabs.add(new App.MenuTree());
        } else if (name == 'resc') {
            tabItem = tabs.add(new App.RescGrid());
        } else if (name == 'role') {
            tabItem = tabs.add(new App.RoleGrid());
        } else if (name == 'user') {
            tabItem = tabs.add(new App.UserGrid());
        } else {
            tabItem = tabs.add(new Ext.lingo.JsonGrid(this.formConfig[name]));
        }
    }
    tabItem.setTitle(title);
    tabs.activate(tabItem);
};


