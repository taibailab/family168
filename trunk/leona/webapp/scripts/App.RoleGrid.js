
App.RoleGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'role',
    urlPagedQuery: "./security/role!pagedQuery.do",
    urlLoadData: "./security/role!loadData.do",
    urlSave: "./security/role!save.do",
    urlRemove: "./security/role!remove.do",
    dlgWidth: 300,
    dlgHeight: 240,
    formConfig: [
        {fieldLabel: '编号',     name: 'id', readOnly: true},
        {fieldLabel: '角色名称', name: 'name'},
        {fieldLabel: '角色备注', name: 'descn'}
    ],
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
            id      : 'selectResc',
            text    : '配置资源',
            iconCls : 'config',
            handler : this.selectResc.createDelegate(this)
        }, {
            id      : 'selectMenu',
            text    : '配置菜单',
            iconCls : 'cog',
            handler : this.selectMenu.createDelegate(this)
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
    renderResource: function(value, p, record) {
        if(record.data['authorized'] == true) {
            return String.format("<b><font color=green>已分配</font></b>");
        } else {
            return String.format("<b><font color=red>未分配</font></b>");
        }
    },
    renderNamePlain: function(value) {
        return String.format('{0}', value);
    },
    selectResc: function() {
        if (this.getSelections().length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        if (this.getSelections().length > 1){
            Ext.MessageBox.alert('提示', '不能选择多行！');
            return;
        }

        if (!this.selectRescWin) {
            var r = Ext.data.Record.create([
                {name: "id",         mapping: "id",         type: "int"},
                {name: "resType",    mapping: "resType",    type: "string"},
                {name: "name",       mapping: "name",       type: "string"},
                {name: "resString",  mapping: "resString",  type: "string"},
                {name: "descn",      mapping: "descn",      type: "string"},
                {name: "authorized", mapping: "authorized", type: "boolean"}
            ]);
            var ds = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'./security/role!getRescs.do'}),
                reader: new Ext.data.JsonReader({
                    root: '',
                    totalProperty: 'totalCount'
                }, r),
                remoteSort: false
            });
            var cm = new Ext.grid.ColumnModel([{
                // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
                id        : 'id',
                header    : "编号",
                dataIndex : "id",
                width     : 80,
                sortable  : true,
                renderer  : this.renderNamePlain,
                css       : 'white-space:normal;'
            }, {
                id        : 'name',
                header    : "资源名称",
                dataIndex : "name",
                sortable  : true,
                width     : 150 ,
                css       : 'white-space:normal;'
            }, {
                id        : 'resType',
                header    : "资源类型",
                dataIndex : "resType",
                sortable  : true,
                width     : 80
            }, {
                id        : 'resString',
                header    : "资源地址",
                dataIndex : "resString",
                sortable  : true,
                width     : 150
            }, {
                id        : 'descn',
                header    : "资源描述",
                dataIndex : "descn",
                sortable  : true,
                width     : 80
            }, {
                id        : 'authorized',
                header    : "是否授权",
                dataIndex : "authorized",
                sortable  : true,
                width     : 80,
                renderer  : this.renderResource
            }]);
            var roleGrid = this;
            var grid = new Ext.grid.GridPanel( {
                ds: ds,
                cm: cm,
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
                        var mResc = grid.getSelections();
                        var mRole = roleGrid.getSelections();
                        if (mResc.length <= 0) {
                            Ext.MessageBox.alert('提示', '请选择至少一个资源操作！');
                            return;
                        }else if(mRole.length == 1) {
                            roleId = mRole[0].get('id');
                            rescId = mResc[0].get('id');
                            Ext.Ajax.request({
                                url: './security/role!authResc.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '授权成功！');
                                    ds.reload();
                                },
                                params: 'auth=true&roleId=' + roleId + '&rescId=' + rescId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                roleId = mRole[0].get('id');
                                rescId = mResc[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/role!authResc.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '授权成功！');
                                        ds.reload();
                                    },
                                    params: 'auth=true&roleId=' + userId + '&rescId=' + roleId
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
                        var mResc = grid.getSelections();
                        var mRole = roleGrid.getSelections();
                        if(mResc.length<=0){
                            Ext.MessageBox.alert('提示', '请选择至少一个资源操作！');
                            return;
                        }else if(mRole.length==1){
                            roleId = mRole[0].get('id');
                            rescId = mResc[0].get('id');
                            Ext.Ajax.request({
                                url: './security/role!authResc.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '取消授权成功！');
                                    ds.reload();
                                },
                                params: 'auth=false&roleId=' + roleId + '&rescId=' + rescId
                            });
                        }else{
                            for(var i = 0, len = mResc.length; i < len; i++){
                                roleId = mRole[0].get('id');
                                rescId = mResc[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/role!authResc.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '取消授权成功！');
                                        ds.reload();
                                    },
                                    params: 'auth=false&roleId=' + roleId + '&rescId=' + rescId
                                });
                            }
                        }
                    }
                }])
            });
            this.rescGrid = grid;
            this.selectRescWin = new Ext.Window({
                layout: 'fit',
                height: 300,
                width: 400,
                closeAction: 'hide',
                items: [grid]
            });
        }
        this.selectRescWin.show();
        this.rescGrid.getStore().baseParams.roleId = this.getSelections()[0].get("id");
        this.rescGrid.getStore().reload();
    },
    selectMenu: function() {
        if (this.getSelections().length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        if (this.getSelections().length > 1){
            Ext.MessageBox.alert('提示', '不能选择多行！');
            return;
        }

        var roleId = this.getSelections()[0].get("id");
        if (!this.selectMenuWin) {
            var menuTree = new Ext.tree.TreePanel({
                autoScroll: true,
                animate : true,
                loader  : new Ext.tree.CustomUITreeLoader({
                    dataUrl : './security/role!getMenus.do',
                    baseAttr : {
                        uiProvider : Ext.tree.CheckboxNodeUI
                    }
                }),
                enableDD        : false,
                containerScroll : true,
                rootUIProvider  : Ext.tree.CheckboxNodeUI,
                selModel        : new Ext.tree.CheckNodeMultiSelectionModel(),
                rootVisible     : false,
                bbar: new Ext.Toolbar([{
                    text: '保存',
                    handler: function() {
                        Ext.Ajax.request({
                            url: './security/role!selectMenu.do',
                            success: function() {
                                menuTree.getLoader().load(menuTree.getRootNode());
                            },
                            failure: function() {
                                //
                            },
                            params: 'ids=' + menuTree.getChecked().join(",") + "&roleId=" + roleId
                        });
                    }
                }])
            });
            menuTree.getLoader().on('load', function(o, node) {
                if (node.isRoot) {
                    menuTree.expandAll();
                }
            });
            // 设置根节点
            var root = new Ext.tree.AsyncTreeNode({
                text       : 'root',
                draggable  : false/*,
                uiProvider : Ext.tree.CheckboxNodeUI*/
            });
            menuTree.setRootNode(root);

            this.menuTree = menuTree;
            this.selectMenuWin = new Ext.Window({
                layout: 'fit',
                height: 300,
                width: 400,
                closeAction: 'hide',
                items: [menuTree]
            });
        }
        this.selectMenuWin.show();
        this.menuTree.getLoader().baseParams.roleId = roleId;
        this.menuTree.getLoader().load(this.menuTree.getRootNode());

    }
});
