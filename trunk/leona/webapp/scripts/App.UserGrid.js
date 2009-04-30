
App.UserGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'user',
    urlPagedQuery: "./security/user!pagedQuery.do",
    urlLoadData: "./security/user!loadData.do",
    urlSave: "./security/user!save.do",
    urlRemove: "./security/user!remove.do",
    dlgWidth: 300,
    dlgHeight: 300,
    formConfig: [
        {fieldLabel: '编号', name: 'id'},
        {fieldLabel: '用户名', name: 'username'},
        {fieldLabel: '密码', name: 'password', inputType: 'password'},
        {fieldLabel: '部门', name: 'deptName', mapping: 'dept.name', xtype: 'deptfield'},
        {fieldLabel: '状态', name: 'status'},
        {fieldLabel: '备注', name: 'descn'}
    ],
    createDialog : function() {
        var readerConfig = [
            {name: 'id'},
            {name: 'username'},
            {name: 'password'},
            {name: 'deptId', mapping: 'dept.id'},
            {name: 'deptName', mapping: 'dept.name'},
            {name: 'status'},
            {name: 'descn'}
        ];
        var reader = new Ext.data.JsonReader({}, readerConfig);
        this.formPanel = new Ext.form.FormPanel({
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 70,
            frame: true,
            autoScroll: true,
            title: '详细信息',
            reader: reader,
            url: this.urlSave,
            items: this.formConfig,
            buttons: [{
                text: '确定',
                handler: function() {
                    if (this.formPanel.getForm().isValid()) {
                        this.formPanel.getForm().submit({
                            waitTitle: "请稍候",
                            waitMsg : '提交数据，请稍候...',
                            success: function() {
                                this.dialog.hide();
                                this.refresh();
                            },
                            failure: function() {
                            },
                            scope: this
                        });
                    }
                }.createDelegate(this)
            },{
                text: '取消',
                handler: function() {
                    this.dialog.hide();
                }.createDelegate(this)
            }]
        });
        this.dialog = new Ext.Window({
            layout: 'fit',
            width: this.dlgWidth ? this.dlgWidth : 400,
            height: this.dlgHeight ? this.dlgHeight : 300,
            closeAction: 'hide',
            items: [this.formPanel]
        });
    },
    buildToolbar: function() {
        //
        var checkItems = new Array();
        for (var i = 0; i < this.formConfig.length; i++) {
            var meta = this.formConfig[i];
            if (meta.showInGrid === false) {
                continue;
            }
            var item = new Ext.menu.CheckItem({
                text         : meta.fieldLabel,
                value        : meta.name,
                checked      : true,
                group        : "filter",
                checkHandler : this.onItemCheck.createDelegate(this)
            });
            checkItems[checkItems.length] = item;
        }

        this.filterButton = new Ext.Toolbar.MenuButton({
            iconCls  : "refresh",
            text     : this.formConfig[0].fieldLabel,
            tooltip  : "选择搜索的字段",
            menu     : checkItems,
            minWidth : 105
        });
        // 输入框
        this.filter = new Ext.form.TextField({
            'name': 'filter'
        });

        this.tbar = new Ext.Toolbar([{
            id      : 'add',
            text    : '新增',
            iconCls : 'add',
            tooltip : '新增',
            handler : this.add.createDelegate(this)
        }, {
            id      : 'edit',
            text    : '修改',
            iconCls : 'edit',
            tooltip : '修改',
            handler : this.edit.createDelegate(this)
        }, {
            id      : 'del',
            text    : '删除',
            iconCls : 'delete',
            tooltip : '删除',
            handler : this.del.createDelegate(this)
        }, {
            id      : 'selectRole',
            text    : '选择角色',
            iconCls : 'student',
            handler : this.selectRole.createDelegate(this)
        }, '->', this.filterButton, this.filter]);

        this.filter.on('specialkey', this.onFilterKey.createDelegate(this));

        // 把分页工具条，放在页脚
        var paging = new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.store,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录",
            plugins: [new Ext.ux.PageSizePlugin()]
        });

        this.store.load({
            params:{start:0, limit:paging.pageSize}
        });
        this.bbar = paging;
    },
    selectRole: function() {

        if (this.getSelections().length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要授权的用户！');
            return;
        }

        if (!this.selectRoleWin) {
            // 建一个角色数据映射数组
            var recordTypeRole = Ext.data.Record.create([
                {name: "id", mapping:"id", type: "int"},
                {name: "name", mapping:"name", type: "string"},
                {name: "descn", mapping:"descn", type: "string"},
                {name: "authorized", mapping:"authorized", type: "boolean"}
            ]);
            //设置数据仓库，使用DWRProxy，ListRangeReader，recordType
            var dsRole = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'./security/user!getRoles.do'}),
                reader: new Ext.data.JsonReader({
                    root: '',
                    totalProperty: 'totalCount'
                }, recordTypeRole),
                // 远端排序开关
                remoteSort: false
            });
            //创建Role表格头格式
            var cmRole = new Ext.grid.ColumnModel([{
                // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
                id: 'id',
                header: "编号",
                dataIndex: "id",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'name',
                header: "角色",
                dataIndex: "name",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'descn',
                header: "描述",
                dataIndex: "descn",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'authorized',
                header: "是否授权",
                dataIndex: "authorized",
                sortable: true,
                renderer:this.renderAuthorized
            }]);
            var userGrid = this;
            var grid = new Ext.grid.GridPanel( {
                ds: dsRole,
                cm: cmRole,
                selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
                enableColLock:false,
                loadMask: false,
                viewConfig: {
                    forceFit: true
                },
                bbar: new Ext.Toolbar([{
                    pressed: true,
                    enableToggle:true,
                    text: '授权',
                    toggleHandler: function(){
                        //授权事件
                        var mRole = grid.getSelections();
                        var mUser = userGrid.getSelections();
                        if (mRole.length <= 0) {
                            Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                            return;
                        }else if(mRole.length == 1) {
                            userId = mUser[0].get('id');
                            roleId = mRole[0].get('id');
                            Ext.Ajax.request({
                                url: './security/user!authRole.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '授权成功！');
                                    dsRole.reload();
                                },
                                params: 'auth=true&userId=' + userId + '&roleId=' + roleId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                userId=mUser[0].get('id');
                                roleId=mRole[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/user!authRole.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '授权成功！');
                                        dsRole.reload();
                                    },
                                    params: 'auth=true&userId=' + userId + '&roleId=' + roleId
                                });
                            }
                        }
                    }
                }, '-', {
                    pressed: true,
                    enableToggle:true,
                    text: '取消授权',
                    toggleHandler: function(){
                        //授权事件
                        var mRole = grid.getSelections();
                        var mUser = userGrid.getSelections();
                        if(mRole.length<=0){
                            Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                            return;
                        }else if(mRole.length==1){
                            userId = mUser[0].get('id');
                            roleId = mRole[0].get('id');
                            Ext.Ajax.request({
                                url: './security/user!authRole.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '取消授权成功！');
                                    dsRole.reload();
                                },
                                params: 'auth=false&userId=' + userId + '&roleId=' + roleId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                userId = mUser[0].get('id');
                                roleId = mRole[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/user!authRole.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '取消授权成功！');
                                        dsRole.reload();
                                    },
                                    params: 'auth=false&userId=' + userId + '&roleId=' + roleId
                                });
                            }
                        }
                    }
                }])
            });
            this.roleGrid = grid;
            this.selectRoleWin = new Ext.Window({
                layout: 'fit',
                height: 300,
                width: 400,
                closeAction: 'hide',
                items: [grid]
            });
        }
        this.selectRoleWin.show();
        this.roleGrid.getStore().baseParams.userId = this.getSelections()[0].get("id");
        this.roleGrid.getStore().reload();
    },
    renderAuthorized: function(value, p, record){
        if(record.data['authorized']==true){
            return String.format("<b><font color=green>已分配</font></b>");
        }else{
            return String.format("<b><font color=red>未分配</font></b>");
        }
    }
});

