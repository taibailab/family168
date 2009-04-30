
App.DeptField = Ext.extend(Ext.lingo.TreeField, {
    allowBlank: false,
    treeConfig: {
        hiddenName: 'deptId',
        title: '部门',
        rootId: 0,
        height: 200,
        dataTag: './security/dept!getAllTree.do',
        treeHeight: 150,
        beforeSelect: function() {
        }
    }
});
Ext.reg('deptfield', App.DeptField);


