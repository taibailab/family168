
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


App.addContent = function(name, title, url) {
    var tabs = App.centerTabPanel;
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


