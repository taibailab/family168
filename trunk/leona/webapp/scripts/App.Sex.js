
Ext.ns("App");

App.SexCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'sex.name',
    hiddenName: 'sex',
    readOnly: true,
    fieldLabel: '性别',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: new Ext.data.Store({
        proxy: new Ext.data.MemoryProxy([
            [0,'未知'],
            [1,'男'],
            [2,'女']
        ]),
        reader: new Ext.data.ArrayReader({
        },['id','name'])
    })
});
Ext.reg('sexcombo', App.SexCombo);

App.sexMap = [
    '未知', '男', '女'
];

App.SexRenderer = function(value) {
    return App.sexMap[value];
};

App.SexColumn = {
    name: 'sex',
    fieldLabel: '性别',
    allowBlank: false,
    xtype: 'sexcombo',
    renderer: App.SexRenderer,
    editor: new App.SexCombo
};
