App.MenuTree = Ext.extend(Ext.lingo.JsonTree, {
    id: 'menu',
    rootName: '菜单',
    enableDD: true,
    urlGetAllTree: "./security/menu!getAllTree.do",
    urlInsertTree: "./security/menu!insertTree.do",
    urlRemoveTree: "./security/menu!removeTree.do",
    urlSortTree: "./security/menu!sortTree.do",
    urlLoadData: "./security/menu!loadData.do",
    urlUpdateTree: "./security/menu!updateTree.do",
    formConfig: [
        {name : 'id',      fieldLabel : "ID",       vType : "integer",  allowBlank : true,  defValue : -1},
        {name : 'url',     fieldLabel : "链接地址", vType : "url",      allowBlank : false},
        {name : 'name',    fieldLabel : "菜单名称", vType : "chn",      allowBlank : false},
        {name : 'qtip',    fieldLabel : "提示信息", vType : "chn",      allowBlank : true},
        {name : 'iconCls', fieldLabel : "图标",     vType : "alphanum", allowBlank : true,  defValue : "user"},
        {name : 'descn',   fieldLabel : "描述",     vType : "chn",      allowBlank : true}
    ]
});
