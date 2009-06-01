
Ext.namespace("App");

App.centerTabPanel = new Ext.TabPanel({
    deferredRender: false,
    enableTabScroll: true,
    monitorResize: true,
    activeTab: 0,
    border: false,
    defaults: {
        closable: true,
        border: true,
        viewConfig: {
            forceFit: true
        }
    },
    items: [{
        id: 'Home',
        title: '欢迎您',
        closable: false,
        autoScroll: true,
        iconCls: 'welcome',
        html: '<table width="100%" height="100%"><tr><td align="center"><a href="http://www.china-pub.com/195152" target="_blank"><img src="images/extjs/cover.jpg" width="300" height="380" border="0" /></a></td></tr></table>'
    }]
});

App.centerTabPanel.on('remove', function(){
    if (this.centerTabPanel.items.getCount() == 0) {
        this.centerTabPanel.hide();
    }
}, App);

App.centerPanel = new Ext.Panel({
    border: false,
    region: 'center',
    layout: 'fit',
    items: App.centerTabPanel
});

App.formConfig = {
    'add-affice': {
        id: 'add-affice',
        title: '发布最新公告',
        formConfig: [
            {name: 'id',            fieldLabel: 'ID',           allowBlank: true,   readOnly: true},
            {name: 'title',         fieldLabel: '公告标题',     allowBlank: false},
            {name: 'time',          fieldLabel: '公告发布时间', allowBlank: false,  xtype:'datefield'},
            {name: 'employeeName',  fieldLabel: '发布公告员工', xtype:'hidden',     mapping:'employee.name'},
            {name: 'employeeId',    fieldLabel: '发布公告员工', allowBlank: false,  mapping:'employee.id', xtype:'employeecombo',hideGrid:true},
            {name: 'content',       fieldLabel: '公告内容',     allowBlank: false}
        ],
        urlPagedQuery: "./affice/affice!pagedQuery.do",
        urlSave: "./affice/affice!save.do",
        urlLoadData: "./affice/affice!loadData.do",
        urlRemove: "./affice/affice!remove.do"
    },

    'view-affice': {
        id: 'view-affice',
        title: '查看以往公告',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'title',fieldLabel: '公告标题',allowBlank: false},
            {name: 'time',fieldLabel: '公告发布时间',allowBlank: false,xtype:'datefield'},
            {name: 'employeeName',fieldLabel: '发布公告员工',xtype:'hidden',mapping:'employee.name'},
            {name: 'employeeId',fieldLabel: '发布公告员工',allowBlank: false,xtype:'employeecombo',hideGrid:true,mapping:'employee.id'},
            {name: 'content',fieldLabel: '公告内容',allowBlank: false}
        ],
        urlPagedQuery: "./affice/affice!pagedQuery.do",
        urlSave: "./affice/affice!save.do",
        urlLoadData: "./affice/affice!loadData.do",
        urlRemove: "./affice/affice!remove.do"
    },

    'send-bumf': {
        id: 'send-bumf',
        title: '发送公文',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'senderName',fieldLabel: '发送者',xtype:'hidden',mapping:'sender.name'},
            {name: 'senderId',fieldLabel: '发送者',allowBlank: false,xtype:'sendercombo',hideGrid:true,mapping:'sender.id'},
            {name: 'accepterName',fieldLabel: '接受者',xtype:'hidden',mapping:'accepter.name'},
            {name: 'accepterId',fieldLabel: '接受者',allowBlank: false,xtype:'acceptercombo',hideGrid:true,mapping:'accepter.id'},
            {name: 'title',fieldLabel: '题目',allowBlank: false},
            {name: 'time',fieldLabel: '时间',allowBlank: false,xtype:'datefield'},
            {name: 'content',fieldLabel: '内容',allowBlank: false},
            {name: 'affix',fieldLabel: '附件名称',allowBlank: false},
            {name: 'examine',fieldLabel: '处理结果',allowBlank: false},
            {name: 'sign',fieldLabel: '标志',allowBlank: false}
        ],
        urlPagedQuery: "./bumf/bumf!pagedQuery.do",
        urlSave: "./bumf/bumf!save.do",
        urlLoadData: "./bumf/bumf!loadData.do",
        urlRemove: "./bumf/bumf!remove.do"
    },

    'accept-bumf': {
        id: 'accept-bumf',
        title: '接收公文',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'senderName',fieldLabel: '发送者',xtype:'hidden',mapping:'sender.name'},
            {name: 'senderId',fieldLabel: '发送者',allowBlank: false,xtype:'sendercombo',hideGrid:true,mapping:'sender.id'},
            {name: 'accepterName',fieldLabel: '接受者',xtype:'hidden',mapping:'accepter.name'},
            {name: 'accepterId',fieldLabel: '接受者',allowBlank: false,xtype:'acceptercombo',hideGrid:true,mapping:'accepter.id'},
            {name: 'title',fieldLabel: '题目',allowBlank: false},
            {name: 'time',fieldLabel: '时间',allowBlank: false,xtype:'datefield'},
            {name: 'content',fieldLabel: '内容',allowBlank: false},
            {name: 'affix',fieldLabel: '附件名称',allowBlank: false},
            {name: 'examine',fieldLabel: '处理结果',allowBlank: false},
            {name: 'sign',fieldLabel: '标志',allowBlank: false}
        ],
        urlPagedQuery: "./bumf/bumf!pagedQuery.do",
        urlSave: "./bumf/bumf!save.do",
        urlLoadData: "./bumf/bumf!loadData.do",
        urlRemove: "./bumf/bumf!remove.do"
    },

    'view-emp': {
        id: 'view-emp',
        title: '查看员工',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '姓名',allowBlank: false},
            {name: 'sex',fieldLabel: '性别',allowBlank: false},
            {name: 'birthday',fieldLabel: '出生日期',allowBlank: false,xtype:'datefield'},
            {name: 'learn',fieldLabel: '学历',allowBlank: false},
            {name: 'post',fieldLabel: '职称',allowBlank: false},
            {name: 'departmentName',fieldLabel: '部门',xtype:'hidden',mapping:'department.name'},
            {name: 'departmentId',fieldLabel: '部门',allowBlank: false,xtype:'departmentcombo',hideGrid:true,mapping:'department.id'},
            {name: 'jobName',fieldLabel: '职位',xtype:'hidden',mapping:'job.name'},
            {name: 'jobId',fieldLabel: '职位',allowBlank: false,xtype:'jobcombo',hideGrid:true,mapping:'job.id'},
            {name: 'tel',fieldLabel: '电话',allowBlank: false},
            {name: 'addr',fieldLabel: '地址',allowBlank: false},
            {name: 'stateName',fieldLabel: '状态',allowBlank: false,xtype:'hidden',mapping:'state.name'},
            {name: 'stateId',fieldLabel: '状态',allowBlank: false,xtype:'statecombo',hideGrid:true,mapping:'state.id'}
        ],
        urlPagedQuery: "./employee/employee!pagedQuery.do",
        urlSave: "./employee/employee!save.do",
        urlLoadData: "./employee/employee!loadData.do",
        urlRemove: "./employee/employee!remove.do"
    },

    'list-emp': {
        id: 'list-emp',
        title: '浏览员工',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '姓名',allowBlank: false},
            {name: 'sex',fieldLabel: '性别',allowBlank: false},
            {name: 'birthday',fieldLabel: '出生日期',allowBlank: false,xtype:'datefield'},
            {name: 'learn',fieldLabel: '学历',allowBlank: false},
            {name: 'post',fieldLabel: '职称',allowBlank: false},
            {name: 'departmentName',fieldLabel: '部门',xtype:'hidden',mapping:'department.name'},
            {name: 'departmentId',fieldLabel: '部门',allowBlank: false,xtype:'departmentcombo',hideGrid:true,mapping:'department.id'},
            {name: 'jobName',fieldLabel: '职位',xtype:'hidden',mapping:'job.name'},
            {name: 'jobId',fieldLabel: '职位',allowBlank: false,xtype:'jobcombo',hideGrid:true,mapping:'job.id'},
            {name: 'tel',fieldLabel: '电话',allowBlank: false},
            {name: 'addr',fieldLabel: '地址',allowBlank: false},
            {name: 'stateName',fieldLabel: '状态',allowBlank: false,xtype:'hidden',mapping:'state.name'},
            {name: 'stateId',fieldLabel: '状态',allowBlank: false,xtype:'statecombo',hideGrid:true,mapping:'state.id'}
        ],
        urlPagedQuery: "./employee/employee!pagedQuery.do",
        urlSave: "./employee/employee!save.do",
        urlLoadData: "./employee/employee!loadData.do",
        urlRemove: "./employee/employee!remove.do"
    },

    'add-emp': {
        id: 'add-emp',
        title: '添加员工',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '姓名',allowBlank: false},
            {name: 'sex',fieldLabel: '性别',allowBlank: false},
            {name: 'birthday',fieldLabel: '出生日期',allowBlank: false,xtype:'datefield'},
            {name: 'learn',fieldLabel: '学历',allowBlank: false},
            {name: 'post',fieldLabel: '职称',allowBlank: false},
            {name: 'departmentName',fieldLabel: '部门',xtype:'hidden',mapping:'department.name'},
            {name: 'departmentId',fieldLabel: '部门',allowBlank: false,xtype:'departmentcombo',hideGrid:true,mapping:'department.id'},
            {name: 'jobName',fieldLabel: '职位',xtype:'hidden',mapping:'job.name'},
            {name: 'jobId',fieldLabel: '职位',allowBlank: false,xtype:'jobcombo',hideGrid:true,mapping:'job.id'},
            {name: 'tel',fieldLabel: '电话',allowBlank: false},
            {name: 'addr',fieldLabel: '地址',allowBlank: false},
            {name: 'stateName',fieldLabel: '状态',allowBlank: false,xtype:'hidden',mapping:'state.name'},
            {name: 'stateId',fieldLabel: '状态',allowBlank: false,xtype:'statecombo',hideGrid:true,mapping:'state.id'}
        ],
        urlPagedQuery: "./employee/employee!pagedQuery.do",
        urlSave: "./employee/employee!save.do",
        urlLoadData: "./employee/employee!loadData.do",
        urlRemove: "./employee/employee!remove.do"
    },

    'view-dept': {
        id: 'view-dept',
        title: '查看部门',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '部门名称',allowBlank: false},
            {name: 'descn',fieldLabel: '部门描述',allowBlank: false}
        ],
        urlPagedQuery: "./department/department!pagedQuery.do",
        urlSave: "./department/department!save.do",
        urlLoadData: "./department/department!loadData.do",
        urlRemove: "./department/department!remove.do"
    },

    'view-job': {
        id: 'view-job',
        title: '查看职位',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '职位名称',allowBlank: false},
            {name: 'descn',fieldLabel: '职位描述',allowBlank: false}
        ],
        urlPagedQuery: "./job/job!pagedQuery.do",
        urlSave: "./job/job!save.do",
        urlLoadData: "./job/job!loadData.do",
        urlRemove: "./job/job!remove.do"
    },

    'view-state': {
        id: 'view-state',
        title: '状态',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '状态名称',allowBlank: false},
            {name: 'descn',fieldLabel: '状态描述',allowBlank: false}
        ],
        urlPagedQuery: "./state/state!pagedQuery.do",
        urlSave: "./state/state!save.do",
        urlLoadData: "./state/state!loadData.do",
        urlRemove: "./state/state!remove.do"
    },

    'view-msg': {
        id: 'view-msg',
        title: '查看信息',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'title',fieldLabel: '题目',allowBlank: false},
            {name: 'time',fieldLabel: '时间',allowBlank: false,xtype:'datefield'},
            {name: 'senderName',fieldLabel: '发送者',mapping:'sender.name',xtype:'hidden'},
            {name: 'senderId',fieldLabel: '发送者',allowBlank: false,xtype:'sendercombo',hideGrid:true,mapping:'sender.id'},
            {name: 'accepterName',fieldLabel: '接受者',mapping:'accepter.name',xtype:'hidden'},
            {name: 'accepterId',fieldLabel: '接受者',allowBlank: false,xtype:'acceptercombo',hideGrid:true,mapping:'accepter.id'},
            {name: 'content',fieldLabel: '内容',allowBlank: false},
            {name: 'status',fieldLabel: '标示短信是否被查看',allowBlank: false}
        ],
        urlPagedQuery: "./message/message!pagedQuery.do",
        urlSave: "./message/message!save.do",
        urlLoadData: "./message/message!loadData.do",
        urlRemove: "./message/message!remove.do"
    },

    'send-msg': {
        id: 'send-msg',
        title: '发送短信',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'title',fieldLabel: '题目',allowBlank: false},
            {name: 'time',fieldLabel: '时间',allowBlank: false,xtype:'datefield'},
            {name: 'senderName',fieldLabel: '发送者',mapping:'sender.name',xtype:'hidden'},
            {name: 'senderId',fieldLabel: '发送者',allowBlank: false,xtype:'sendercombo',hideGrid:true,mapping:'sender.id'},
            {name: 'accepterName',fieldLabel: '接受者',mapping:'accepter.name',xtype:'hidden'},
            {name: 'accepterId',fieldLabel: '接受者',allowBlank: false,xtype:'acceptercombo',hideGrid:true,mapping:'accepter.id'},
            {name: 'content',fieldLabel: '内容',allowBlank: false},
            {name: 'status',fieldLabel: '标示短信是否被查看',allowBlank: false}
        ],
        urlPagedQuery: "./message/message!pagedQuery.do",
        urlSave: "./message/message!save.do",
        urlLoadData: "./message/message!loadData.do",
        urlRemove: "./message/message!remove.do"
    },

    'add-best': {
        id: 'add-best',
        title: '优秀员工提名',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'name',fieldLabel: '上下班名称',allowBlank: false},
            {name: 'time',fieldLabel: '系统时间',allowBlank: false,xtype:'datefield'}
        ],
        urlPagedQuery: "./signState/signState!pagedQuery.do",
        urlSave: "./signState/signState!save.do",
        urlLoadData: "./signState/signState!loadData.do",
        urlRemove: "./signState/signState!remove.do"
    },

    'view-best': {
        id: 'view-best',
        title: '查看优秀员工',
        formConfig: [
            {name: 'id',fieldLabel: 'ID',allowBlank: true,readOnly: true},
            {name: 'time',fieldLabel: '系统时间',allowBlank: false,xtype:'datefield'},
            {name: 'employeeName',fieldLabel: '员工编号',mapping:'employee.name',xtype:'hidden'},
            {name: 'employeeId',fieldLabel: '员工编号',allowBlank: false,xtype:'employeecombo',hideGrid:true,mapping:'employee.id'},
            {name: 'late',fieldLabel: '是否迟到',allowBlank: false},
            {name: 'quit',fieldLabel: '是否下班',allowBlank: false},
            {name: 'leaveStatus',fieldLabel: '是否早退',allowBlank: false},
            {name: 'work',fieldLabel: '工作状态',allowBlank: false},
            {name: 'signStateName',fieldLabel: '上下班描述',mapping:'signState.name',xtype:'hidden'},
            {name: 'signStateId',fieldLabel: '上下班描述',allowBlank: false,xtype:'signstatecombo',hideGrid:true,mapping:'signState.id',raw:'signState.name'}
        ],
        urlPagedQuery: "./sign/sign!pagedQuery.do",
        urlSave: "./sign/sign!save.do",
        urlLoadData: "./sign/sign!loadData.do",
        urlRemove: "./sign/sign!remove.do"
    }
};

