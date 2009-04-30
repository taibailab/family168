
[说明] [2009-01-28]

* 基础开发架构
  采用SpringSide-2.0中推荐的模式，将不知道谁提出来的action : manager interface : manager : dao interface : dao
  这种五层结构简化成action : manager两层结构。
  首先我们省略了manager interface和dao interface两层接口。
  然后让manager继承已经封装好的基类，直接获得CRUD和各种查询功能。

* IOC Container: Spring, Guice
  这么多人都在使用Spring，实际使用中可以获得巨大的便利，如果选择了其他平台就要面临在关键时刻自己造轮子的窘境。
  使用Spring的实际经验更长，方便与其他组件整合。
  Guice是由Google出品的，又使用了JDK5中的注解功能，问题是以后是否能得到很好的支持。

* MVC: struts2, struts, JSF, RESTful Framework, SpringMVC
  struts2比struts好用，在了解了OGNL的用法后，还是比较方便的。
  struts毕竟还是老掉牙了。
  JSF好复杂，没有IDE的支持基本没法用，看不清方向。
  RESTfull Framework感觉太草率，有前途吗。
  springmvc现在默认不包含在spring包中了，它的功能太简陋，有些新功能华而不实。

* ORM: Hibernate, JPA, iBatis, JDBC
  Hibernate用起来比较熟，支持多数据库，而且简化数据库的操作，目前把所有的希望都放在二级缓存上了。
  JPA对于规范不太了解。
  iBatis据称是对JDBC的最简封装，但是与hibernate有太多的区别。
  JDBC根本没进行封装，用起来风险太高。使用封装的框架更容易统一开发。

* View: JSP-2.0, Freemarker, Velocity
  严重偏向Freemarker，易用性，扩展性，自定义宏，还可以用在不支持JSP-2.0的服务器上，而且还可以与struts2配合。
  可是Freemarker并没有合适IDE配合，而且能够熟练使用的程序员也不多。
  JSP-2.0功能明显不足，限制太多，不容易扩展，必须和taglib配合才能对付实际中的工作，taglib又太难写。
  Velocity功能不如Freemarker。

* Ajax Widgets: ExtJS, Dojo, YUI, JQuery-UI, jsCalendar, FckEditor, Tiny_mce, JavaScriptTemplate
  ExtJS轻便，好用，漂亮，功能强大。只是协议有问题。
  Dojo笨重，难看，都不知道怎么用。
  YUI是ExtJS的原型，但是封装没有ExtJS好。
  JQuery-UI还很青涩。
  如果不使用上述这类统一组件库，还可以选择jsCalendar这个日期选择组件，FckEditor和Tiny_mce都是富文本编辑器
  据说专业人士喜欢用FckEditor，喜欢漂亮的都喜欢用Tiny_mce。JavaScriptTemplate可以批量生成HTML。
  和dwr结合实现上传进度条等功能。

* JS Function Lib: Prototypejs, JQuery, Mootools
  Prototypejs最老。对应的扩展主要有Script.Aculo.us。
  JQuery有强力的查询能力。对应的扩展容易找到。
  Mootools被fin强烈推荐，说OO封装做得很好，但感觉扩展组件不丰富。

* Security: Spring Security, jsecurity, struts menu
  Spring Security作为acegi的升级版本，更易用了，而且跟spring整合在一起。
  jsecurity作为apache孵化器里的项目，有人对它进行了强烈推荐。
  是否还可虑基于struts menu写一套自定义的菜单？

* WebService: CXF, XFire, Axis2
  Axis2总感觉很笨重，而且不容易和spring结合。
  XFire已经停止开发，和Celtix合并为apache中的新项目CXF。
  CXF可以和spring结合，而且据说支持JAX-WS。不过不知道JAX-WS是干啥的。

* 工作流: jbpm-3, jbpm-4, osworkflow
  jbpm-3是开源中比较火的框架了。但是spring-module-jbpm31封装的有问题，单元测试会报异常。
  jbpm-4太新，还是alpha版，而且改动太大了。
  osworkflow据说所有实现都要自己写代码。

* 规则引擎: drools
  据说是很轻量级的，拥有平民语法的规则引擎。

* Search Engine: Hiberate-Search, Compass, Lucene
  Compass和Hibernate-Search都是基于Lucene对数据库进行全文搜索的工具。
  Compass开发的时间更长一些。
  Hibernate-Search出现的时间还比较短，会用的人少，不知道前途如何。
  如果两者都不符合，还要考虑自己直接操作Lucene。

* 报表: JasperReport, BIRT, 自定义
  虽然没使用过JasperReport，但已经听到过太多其他人对它的怨言了。
  BIRT没有想像中那样好用，依赖那些eclipse中的包，显得太大了。
  因为东西方习惯差异，自定义属于自己的一套报表应该可以解决国内很多问题。
  首选答案竟然是手写PDF和JFreeChart结合生成图表。

* JMS: ActiveMQ, Jencks, Lingo
  ActiveMQ是很多人推荐使用的JMS框架。
  Jencks是一个JCA平台，可以提供J2EE里的资源容器。
  Lingo据说可以把JMS封装成POJO。

* 定时任务: Quartz, java.util.Timer
  据说Quartz是唯一免费的解决方式。
  这个组件放到web.xml里，可能造成内存泄露。
  robbin认为这种定时任务不应该和项目绑定在一起，而是应该建立一个单独的任务服务器。
  如果是简单功能，也可以用JDK自带的Timer。

* Cluster Cache: Open Terracotta
  被许多公司使用，据说是很强大的分布集群策略。

* JMX: MX4j
  管理扩展，没用过，不知道干啥用的。

* OSGI: equinox, felix
  有点儿反感equinox的笨重，而且equinox是否能和eclipse分离呢？能否独立使用呢？能否能嵌入自己的项目呢？
  felix是apache的项目，而且有maven插件，但是看了源代码之后，感觉太不成熟了。

* 动态语言: bsh, groovy, jruby, jython, rhino
  bsh最简单，而且都是java语法，功能也不多。
  rhino只是解析js而且，也没什么特别功能。
  groovy被期许了很大的希望，受到多方支持，有为数不少的组件，值得考虑一下。
  jruby和jython的话，语法和java相差太大了，基本等于再学一门语言。

* JDK: 5.0, 1.4, 6.0
  5.0的那些特性还是很好用的，而且可以使用Retrotranslator把5.0编译的class转换成可以在1.4下运行的class。
  推荐使用JRockit-27.2，据说可以解决sun jdk的perm gen内存不足问题。
  6.0有点儿新了，怕出问题。

* Servlet Container: Tomcat-5.5, Weblogic-9/10, Jboss, Geromino, Tomcat-6.0, Glassfish, Jetty-6.1, Resin
  一般人还是用Tomcat-5.5，据说Tomcat-6.0支持OSGI了而且运行方式跟之前都不同了。
  Jetty还是用在集成测试中。
  之后的迭代里，应该兼容Weblogic, Jboss, Geromino, Glassfish。

* 数据库: Hsqldb, Mysql, h2database, derby, oracle, sqlserver
  默认使用hsqldb作为嵌入数据库，考虑h2database和derby，似乎h2功能更强力，derby出身名门但是不支持commit是个问题。
  mysql是必须要支持的，因为是免费中使用特别广的。
  sqlserver也是国内很多地方使用的中型数据库。
  oracle有很多人迷信，的确也是非常常用的大型数据库。

* UML: Enterprise Architecture, Rose 2003, Visio, StarUML
  EA不是免费的。
  Rose是名门。
  Visio是画图挺方便。
  StarUML是免费开源方案。

* 单元测试: JUnit-3, JUnit-4, TestNG
  JUnit-3应用很广。
  JUnit-4有点儿新，新功能不太清楚。
  TestNG据说很厉害，但不太熟悉。

* Mock: EasyMock, JMock, sprng-test
  EasyMock是老牌了，不需要继承特定的超类。
  JMock需要继承特定超类，属于新框架。
  spring-test里提供了很多对付servlet里接口的测试类。它还提供了许多常用的测试基类，可以缓存xml配置并支持事务。

* 测试覆盖率: Cobertura, Clover
  Cobertura发展至今，界面和功能已经接近Clover了，开源并且没有古怪的协议约束，maven2上的配置也比Clover更简便。
  但是Clover毕竟是商业支持的功能，最近又提出的优化的分支测试检测方案，着实值得期待。

* Web集成测试: Selenium
  使用Firefox的插件录制测试，然后可以翻译成多种平台语言进行自动化测试。
  可以使用maven插件进行测试，但是问题是一直没有发布1.0，一直只能使用1.0-SNAPSHOT。

* WebService测试: SOAPUI
  据说是唯一的WebService测试工具。

* 压力测试: Jmeta
  听说过，但是没用过。

* Web服务器控制: Cargo
  据说可以统一控制各种Web服务器，比如Tomcat, Jboss, Weblogic

* 数据库版本控制: dbdeploy
  咱们基于dbdeploy扩展的dbside，可以提供db:migrate, db:rollback, db:view等功能。

* 项目部署工具: Maven2, Ant
  ant更灵活，但是对于大规模项目，自己写build.xml再配置所有的插件和各种流程太麻烦了。而且要借助ivy才好管理依赖。
  maven拥有规范的目录结构和流程体系，拥有更易用的插件，还可以统一管理jar依赖。
  问题是从外网下载依赖的时候受网速影响太大，一旦遇到外网缺失的依赖更是无可奈何，必须搭建内部repo。
  maven的一个问题，在于不知道如何把这么巨大的一个xml文件拆分成多个可以复用的模块。
  所以也只好采用这种parent的继承关系对所有的依赖和插件进行管理。

* 持续集成: CruiseControl, Hudson
  cc是老牌劲旅，但是界面也太难看了。
  hudson是日本人写的，界面漂亮，所有配置都可以直接通过界面配置。

* 代码规范: jalopy, checkstyle
  jalopy用来美化代码，sf.net上的开源项目已经好多年没有更新了，最新的jalopy已经变成了收费的商业项目。
  不过我们使用几年前的免费项目就足够了。
  把checkstyle的规范与jalopy的美化规范调节一致，就可以放心编写代码了。

* 代码检测: pmd, findbugs, javancss, taglist, jdepend
  pmd, cpd帮助我们检测无用的代码和重复的代码。
  findbugs检测代码中的缺陷。
  javancss计算有效代码行数。
  taglist统计代码中的特定标记。
  jdepend用于统计包之间的依赖性。

* 版本控制，缺陷跟踪和wiki: SVN, JIRA, Confluence
  Subversion作为版本控制工具，JIRA作为缺陷管理，Confluence提供资源库平台。
