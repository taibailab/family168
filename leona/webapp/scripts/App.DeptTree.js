App.DeptTree = Ext.extend(Ext.lingo.JsonTree, {
    id: 'dept',
    rootName: '部门',
    urlGetAllTree: "./security/dept!getAllTree.do",
    urlInsertTree: "./security/dept!insertTree.do",
    urlRemoveTree: "./security/dept!removeTree.do",
    urlSortTree: "./security/dept!sortTree.do",
    urlLoadData: "./security/dept!loadData.do",
    urlUpdateTree: "./security/dept!updateTree.do",
    formConfig: [
        {name : 'id',      fieldLabel : "ID",       vType : "integer",  allowBlank : true,  readOnly: true},
        {name : 'name',    fieldLabel : "部门名称", vType : "chn",      allowBlank : false},
        {name : 'descn',   fieldLabel : "描述",     vType : "chn",      allowBlank : true}
    ]
});
