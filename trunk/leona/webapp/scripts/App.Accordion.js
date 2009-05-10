
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
};


App.addContent = function(name, title, iconCls) {
    var tabs = App.centerTabPanel;
    var tabItem = tabs.getItem(name);
    if (tabItem == null) {
        var p = null;
        if (name == 'dept') {
            p = new App.DeptTree();
        } else if (name == 'menu') {
            p = new App.MenuTree();
        } else if (name == 'resc') {
            p = new App.RescGrid();
        } else if (name == 'role') {
            p = new App.RoleGrid();
        } else if (name == 'user') {
            p = new App.UserGrid();
        } else if (name == 'org') {
            p = new Ext.ux.ManagedIframePanel({
                id: 'org',
                defaultSrc: 'security/dept!orgMap.do'
            });
        } else {
            p = new Ext.lingo.JsonGrid(this.formConfig[name]);
        }
        p.setTitle(title, iconCls);
        tabItem = tabs.add(p);
    }
    tabs.activate(tabItem);
};


