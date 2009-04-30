
var employeeStore = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url:'./employee/employee!pagedQuery.do'}),
    reader: new Ext.data.JsonReader({
        root: 'result',
        totalProperty: 'totalCount'
    },['id','name'])
});
employeeStore.load({params:{start:0,limit:15}});


var departmentStore = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url:'./department/department!pagedQuery.do'}),
    reader: new Ext.data.JsonReader({
        root: 'result',
        totalProperty: 'totalCount'
    },['id','name'])
});
departmentStore.load({params:{start:0,limit:15}});


var jobStore = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url:'./job/job!pagedQuery.do'}),
    reader: new Ext.data.JsonReader({
        root: 'result',
        totalProperty: 'totalCount'
    },['id','name'])
});
jobStore.load({params:{start:0,limit:15}});


var stateStore = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url:'./state/state!pagedQuery.do'}),
    reader: new Ext.data.JsonReader({
        root: 'result',
        totalProperty: 'totalCount'
    },['id','name'])
});
stateStore.load({params:{start:0,limit:15}});


var signStateStore = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url:'./signState/signState!pagedQuery.do'}),
    reader: new Ext.data.JsonReader({
        root: 'result',
        totalProperty: 'totalCount'
    },['id','name'])
});
signStateStore.load({params:{start:0,limit:15}});


App.EmployeeCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'employeeName',
    hiddenName: 'employeeId',
    readOnly: true,
    fieldLabel: '员工',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: employeeStore,
    pageSize: 15
});
Ext.reg('employeecombo', App.EmployeeCombo);


App.SenderCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'senderName',
    hiddenName: 'senderId',
    readOnly: true,
    fieldLabel: '员工',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: employeeStore,
    pageSize: 15
});
Ext.reg('sendercombo', App.SenderCombo);


App.AccepterCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'accepterName',
    hiddenName: 'accepterId',
    readOnly: true,
    fieldLabel: '员工',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: employeeStore,
    pageSize: 15
});
Ext.reg('acceptercombo', App.AccepterCombo);


App.DepartmentCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'departmentName',
    hiddenName: 'departmentId',
    readOnly: true,
    fieldLabel: '部门',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: departmentStore,
    pageSize: 15
});
Ext.reg('departmentcombo', App.DepartmentCombo);


App.JobCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'jobName',
    hiddenName: 'jobId',
    readOnly: true,
    fieldLabel: '职位',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: jobStore,
    pageSize: 15
});
Ext.reg('jobcombo', App.JobCombo);


App.StateCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'stateName',
    hiddenName: 'stateId',
    readOnly: true,
    fieldLabel: '状态',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: stateStore,
    pageSize: 15
});
Ext.reg('statecombo', App.StateCombo);


App.SignStateCombo = Ext.extend(Ext.form.ComboBox, {
    name: 'signStateName',
    hiddenName: 'signStateId',
    readOnly: true,
    fieldLabel: '上下班',
    valueField: 'id',
    displayField: 'name',
    typeAhead: true,
    mode: 'remote',
    triggerAction: 'all',
    emptyText: '请选择',
    selectOnFocus: true,
    allowBlank: false,
    store: signStateStore,
    pageSize: 15
});
Ext.reg('signstatecombo', App.SignStateCombo);

