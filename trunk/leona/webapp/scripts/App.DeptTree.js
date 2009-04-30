App.DeptTree = Ext.extend(Ext.lingo.JsonTree, {
    id: 'dept',
    rootName: '部门',
    urlGetAllTree: "./security/dept!getAllTree.do",
    urlInsertTree: "./security/dept!insertTree.do",
    urlRemoveTree: "./security/dept!removeTree.do",
    urlSortTree: "./security/dept!sortTree.do",
    urlLoadData: "./security/dept!loadData.do",
    urlUpdateTree: "./security/dept!updateTree.do"
});
