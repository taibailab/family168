咱们这里的说的是如果从源代码编译出可以跑的项目来，编译完就直接运行，测试数据都已经准备好了。

第一步，安装svn，check out最新代码
    http://family168.googlecode.com/svn/trunk/

第二步，安装maven2
    http://maven.apache.org/

第三步，进入trunk目录下，使用maven2对项目进行配置
    进入trunk/目录下，执行mvn，这一步会把所有需要的依赖项都安装到本地资源库
    上一步没问题的话，进入trunk/leona/，执行mvn，这一步是启动OA。
    如果上一步没问题，leona启动成功后，就可以直接通过http://localhost:8080/leona访问OA了。
    测试用户test/test, user/user，这两个用户的权限不同，最明显的就是登陆之后看到的菜单不同。

也可以参考maven2教程中的相关章节：
http://family168.com/oa/maven2/html/ch04-oa.html
