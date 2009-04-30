

webim = new Ext.Window({
    id: 'webim',
    closeAction: 'hide',
    width: 200,
    height: 400,
    title: '即时交流',
    layout: 'accordion',
    tbar: new Ext.Toolbar([{
        text: '查找'
    }, {
        text: '私聊',
        handler: function() {
            webimWindow.show();
        }
    }, {
        text: '群组',
        handler: function() {
            webimChat.show();
        }
    }]),
    items: [{
        title: '好友',
        xtype: 'treepanel',
        rootVisible: false,
        lines: false,
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            text:'root',
            children: [
                {text: 'student',leaf: true,iconCls:'student'},
                {text: 'user',leaf: true,iconCls:'user'}
            ]
        })
    }, {
        title: '陌生人'
    }]
});

webimWindow = new Ext.Window({
    id: 'webimWindow',
    closeAction: 'hide',
    width: 520,
    height: 400,
    title: '与某交流',
    layout: 'border',
    tbar: new Ext.Toolbar([{
        text: '查看资料'
    }, {
        text: '加为好友'
    }]),
    items: [{
        region: 'center'
    }, {
        region: 'south',
        height: 150,
        layout: 'fit',
        items: [{
            xtype: 'htmleditor'
        }],
        tbar: new Ext.Toolbar([{
            text: '聊天记录'
        }, {
            text: '发送附件'
        }, '-', '是否自动关闭', new Ext.form.Checkbox()])
    }],
    buttons: [{
        text: '关闭',
        handler: function() {
            webimWindow.hide();
        }
    }, {
        text: '发送'
    }]
});

webimChat = new Ext.Window({
    id: 'webimChat',
    closeAction: 'hide',
    width: 520,
    height: 400,
    title: '去租',
    layout: 'border',
    items: [{
        region: 'center',
        layout: 'border',
        tbar: new Ext.Toolbar([{
            text: '查看资料'
        }]),
        items: [{
            region: 'center'
        }, {
            region: 'south',
            height: 150,
            layout: 'fit',
            items: [{
                xtype: 'htmleditor'
            }],
            tbar: new Ext.Toolbar([{
                text: '聊天记录'
            }, {
                text: '发送附件'
            }, '-', '是否自动关闭', new Ext.form.Checkbox()])
        }],
        buttons: [{
            text: '关闭',
            handler: function() {
                webimWindow.hide();
            }
        }, {
            text: '发送'
        }]
    }, {
        region: 'east',
        width: 150,
        layout: 'border',
        items: [{
            region: 'north',
            height: 150,
            title: '公告'
        }, {
            region: 'center',
            title: '信息'
        }]
    }]
});


