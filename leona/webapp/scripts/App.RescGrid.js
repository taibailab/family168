
App.RescGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'resc',
    urlPagedQuery: "./security/resc!pagedQuery.do",
    urlLoadData: "./security/resc!loadData.do",
    urlSave: "./security/resc!save.do",
    urlRemove: "./security/resc!remove.do",
    dlgWidth: 300,
    dlgHeight: 240,
    formConfig: [
        {fieldLabel: '编号',     name: 'id'},
        {fieldLabel: '资源类型', name: 'resType'},
        {fieldLabel: '资源名称', name: 'name'},
        {fieldLabel: '资源内容', name: 'resString'},
        {fieldLabel: '资源备注', name: 'descn'}
    ]
});
