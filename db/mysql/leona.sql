-- MySQL dump 10.11
--
-- Host: localhost    Database: leona
-- ------------------------------------------------------
-- Server version   5.0.41-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acl_class`
--

DROP TABLE IF EXISTS `acl_class`;
CREATE TABLE `acl_class` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SID` varchar(100) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_class`
--

LOCK TABLES `acl_class` WRITE;
/*!40000 ALTER TABLE `acl_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_entry`
--

DROP TABLE IF EXISTS `acl_entry`;
CREATE TABLE `acl_entry` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ACE_ORDER` int(11) default NULL,
  `AUDIT_FAILURE` int(11) default NULL,
  `AUDIT_SUCCESS` int(11) default NULL,
  `GRANTING` int(11) default NULL,
  `MASK` int(11) default NULL,
  `ACL_OBJECT_IDENTITY` bigint(20) default NULL,
  `SID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK2FB5F83D2AE8CE28` (`ACL_OBJECT_IDENTITY`),
  KEY `FK2FB5F83DB75131DA` (`SID`),
  CONSTRAINT `FK2FB5F83DB75131DA` FOREIGN KEY (`SID`) REFERENCES `acl_sid` (`ID`),
  CONSTRAINT `FK2FB5F83D2AE8CE28` FOREIGN KEY (`ACL_OBJECT_IDENTITY`) REFERENCES `acl_object_identity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_entry`
--

LOCK TABLES `acl_entry` WRITE;
/*!40000 ALTER TABLE `acl_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_object_identity`
--

DROP TABLE IF EXISTS `acl_object_identity`;
CREATE TABLE `acl_object_identity` (
  `ID` bigint(20) NOT NULL auto_increment,
  `entries_inheriting` int(11) default NULL,
  `OBJECT_ID_IDENTITY` bigint(20) default NULL,
  `OBJECT_ID_CLASS` bigint(20) default NULL,
  `OWNER_SID` bigint(20) default NULL,
  `PARENT_OBJECT` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK988CEFE9B861C24E` (`OWNER_SID`),
  KEY `FK988CEFE91F118293` (`PARENT_OBJECT`),
  KEY `FK988CEFE927DAD360` (`OBJECT_ID_CLASS`),
  KEY `FK988CEFE990F1A88A` (`OBJECT_ID_CLASS`),
  CONSTRAINT `FK988CEFE9B861C24E` FOREIGN KEY (`OWNER_SID`) REFERENCES `acl_sid` (`ID`),
  CONSTRAINT `FK988CEFE91F118293` FOREIGN KEY (`PARENT_OBJECT`) REFERENCES `acl_object_identity` (`ID`),
  CONSTRAINT `FK988CEFE927DAD360` FOREIGN KEY (`OBJECT_ID_CLASS`) REFERENCES `acl_sid` (`ID`),
  CONSTRAINT `FK988CEFE990F1A88A` FOREIGN KEY (`OBJECT_ID_CLASS`) REFERENCES `acl_class` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_object_identity`
--

LOCK TABLES `acl_object_identity` WRITE;
/*!40000 ALTER TABLE `acl_object_identity` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_object_identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_sid`
--

DROP TABLE IF EXISTS `acl_sid`;
CREATE TABLE `acl_sid` (
  `ID` bigint(20) NOT NULL auto_increment,
  `PRINCIPAL` int(11) default NULL,
  `SID` varchar(100) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_sid`
--

LOCK TABLES `acl_sid` WRITE;
/*!40000 ALTER TABLE `acl_sid` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_sid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `affice`
--

DROP TABLE IF EXISTS `affice`;
CREATE TABLE `affice` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTENT` varchar(2000) default NULL,
  `TIME` datetime default NULL,
  `TITLE` varchar(40) default NULL,
  `EMPLOYEE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK72E65A0A51093B13` (`EMPLOYEE_ID`),
  CONSTRAINT `FK72E65A0A51093B13` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `affice`
--

LOCK TABLES `affice` WRITE;
/*!40000 ALTER TABLE `affice` DISABLE KEYS */;
INSERT INTO `affice` VALUES (2,'内容','2009-04-18 00:00:00','标题',1);
/*!40000 ALTER TABLE `affice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bumf`
--

DROP TABLE IF EXISTS `bumf`;
CREATE TABLE `bumf` (
  `ID` bigint(20) NOT NULL auto_increment,
  `AFFIX` varchar(40) default NULL,
  `CONTENT` varchar(2000) default NULL,
  `EXAMINE` int(11) default NULL,
  `SIGN` int(11) default NULL,
  `TIME` datetime default NULL,
  `TITLE` varchar(40) default NULL,
  `ACCEPTER` bigint(20) default NULL,
  `SENDER` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK1F492CE48D70FC` (`SENDER`),
  KEY `FK1F492C9A9BF67C` (`ACCEPTER`),
  CONSTRAINT `FK1F492CE48D70FC` FOREIGN KEY (`SENDER`) REFERENCES `employee` (`ID`),
  CONSTRAINT `FK1F492C9A9BF67C` FOREIGN KEY (`ACCEPTER`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bumf`
--

LOCK TABLES `bumf` WRITE;
/*!40000 ALTER TABLE `bumf` DISABLE KEYS */;
INSERT INTO `bumf` VALUES (1,'1','1',1,1,'2009-04-18 00:00:00','1',1,1),(2,'12','12',12,2,'2009-04-18 00:00:00','12',1,1);
/*!40000 ALTER TABLE `bumf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'1','部门1'),(2,'2','部门2');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) default NULL,
  `THE_SORT` int(11) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK1FF64516A2C63D` (`PARENT_ID`),
  CONSTRAINT `FK1FF64516A2C63D` FOREIGN KEY (`PARENT_ID`) REFERENCES `dept` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'default','default',NULL,NULL);
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ADDR` varchar(200) default NULL,
  `BIRTHDAY` datetime default NULL,
  `LEARN` varchar(10) default NULL,
  `NAME` varchar(20) default NULL,
  `POST` varchar(10) default NULL,
  `SEX` int(11) default NULL,
  `TEL` varchar(20) default NULL,
  `DEPARTMENT_ID` bigint(20) default NULL,
  `JOB_ID` bigint(20) default NULL,
  `STATE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK75C8D6AEE6AA653` (`DEPARTMENT_ID`),
  KEY `FK75C8D6AEDD590AA1` (`STATE_ID`),
  KEY `FK75C8D6AE9FE1A0E1` (`JOB_ID`),
  CONSTRAINT `FK75C8D6AEE6AA653` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `department` (`ID`),
  CONSTRAINT `FK75C8D6AE9FE1A0E1` FOREIGN KEY (`JOB_ID`) REFERENCES `job` (`ID`),
  CONSTRAINT `FK75C8D6AEDD590AA1` FOREIGN KEY (`STATE_ID`) REFERENCES `state` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'1','2009-04-07 00:00:00','1','小员工','1',1,'1',2,1,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'2','职位1');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `ICON_CLS` varchar(50) default NULL,
  `NAME` varchar(50) default NULL,
  `QTIP` varchar(50) default NULL,
  `THE_SORT` int(11) default NULL,
  `URL` varchar(50) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK240D5F16A6DD57` (`PARENT_ID`),
  CONSTRAINT `FK240D5F16A6DD57` FOREIGN KEY (`PARENT_ID`) REFERENCES `menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'index','config','部门管理',NULL,14,NULL,NULL),(2,'user','config','员工管理',NULL,12,NULL,NULL),(3,'role','config','公文管理',NULL,10,NULL,NULL),(4,'resc','config','公告管理',NULL,8,NULL,NULL),(5,'menu','config','组织机构',NULL,5,NULL,NULL),(6,'','add','权限管理','',0,'security',NULL),(7,NULL,'config','收发信息',NULL,18,NULL,NULL),(8,NULL,'config','优秀员工管理',NULL,20,NULL,NULL),(9,'','wrench','资源','',1,'resc',6),(10,'','user_delete','角色','',2,'role',6),(11,'','config','用户','',3,'user',6),(12,'','config','菜单','',4,'menu',6),(13,'','config','组织机构部门','',6,'dept',5),(14,'','config','组织机构','',7,'org',5),(16,'','config','查看公告','',9,'view-affice',4),(17,'','config','查看公文','',11,'send-bumf',3),(19,'','config','查看员工','',13,'view-emp',2),(22,'','config','查看部门','',15,'view-dept',1),(23,'','config','查看职位','',16,'view-job',1),(24,'','config','查看状态','',17,'view-state',1),(25,'','config','查看信息','',19,'view-msg',7),(27,'','config','上下班时间管理','',21,'add-best',8),(28,'','config','考勤记录','',22,'view-best',8);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_role`
--

DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `ROLE_ID` bigint(20) NOT NULL,
  `MENU_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`MENU_ID`,`ROLE_ID`),
  KEY `FK4CD9A756ED542D22` (`ROLE_ID`),
  KEY `FK4CD9A756D3E59882` (`MENU_ID`),
  CONSTRAINT `FK4CD9A756ED542D22` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK4CD9A756D3E59882` FOREIGN KEY (`MENU_ID`) REFERENCES `menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menu_role`
--

LOCK TABLES `menu_role` WRITE;
/*!40000 ALTER TABLE `menu_role` DISABLE KEYS */;
INSERT INTO `menu_role` VALUES (4,1),(4,2),(4,3),(4,4),(4,5),(4,6),(4,7),(4,8),(4,9),(4,10),(4,11),(4,12),(4,13),(4,14),(4,16),(4,17),(4,19),(4,22),(4,23),(4,24),(4,25),(4,27),(4,28),(6,1),(6,3),(6,4),(6,16),(6,17);
/*!40000 ALTER TABLE `menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTENT` varchar(2000) default NULL,
  `STATUS` int(11) default NULL,
  `TIME` datetime default NULL,
  `TITLE` varchar(40) default NULL,
  `ACCEPTER` bigint(20) default NULL,
  `SENDER` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK63B68BE7E48D70FC` (`SENDER`),
  KEY `FK63B68BE79A9BF67C` (`ACCEPTER`),
  CONSTRAINT `FK63B68BE7E48D70FC` FOREIGN KEY (`SENDER`) REFERENCES `employee` (`ID`),
  CONSTRAINT `FK63B68BE79A9BF67C` FOREIGN KEY (`ACCEPTER`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (2,'1',1,'2009-04-18 00:00:00','1111',NULL,NULL),(3,'1',1,'2009-04-18 00:00:00','1',NULL,NULL),(4,'1',1,'2009-04-18 00:00:00','1',NULL,1),(5,'2',2,'2009-04-09 00:00:00','1',1,1);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resc`
--

DROP TABLE IF EXISTS `resc`;
CREATE TABLE `resc` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) default NULL,
  `RES_STRING` varchar(200) default NULL,
  `RES_TYPE` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `resc`
--

LOCK TABLES `resc` WRITE;
/*!40000 ALTER TABLE `resc` DISABLE KEYS */;
INSERT INTO `resc` VALUES (1,'index','index','/security/index.jsp','URL'),(2,'user','user','/security/user*','URL'),(3,'role','role','/security/role*','URL'),(4,'resc','resc','/security/resc*','URL'),(5,'menu','menu','/security/menu*','URL'),(6,'dept','dept','/security/dept*','URL'),(7,'login','login','/islogin.jsp','URL');
/*!40000 ALTER TABLE `resc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resc_role`
--

DROP TABLE IF EXISTS `resc_role`;
CREATE TABLE `resc_role` (
  `ROLE_ID` bigint(20) NOT NULL,
  `RESC_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`RESC_ID`,`ROLE_ID`),
  KEY `FK1BF7BF72ED542D22` (`ROLE_ID`),
  KEY `FK1BF7BF72DCA54E02` (`RESC_ID`),
  CONSTRAINT `FK1BF7BF72ED542D22` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK1BF7BF72DCA54E02` FOREIGN KEY (`RESC_ID`) REFERENCES `resc` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `resc_role`
--

LOCK TABLES `resc_role` WRITE;
/*!40000 ALTER TABLE `resc_role` DISABLE KEYS */;
INSERT INTO `resc_role` VALUES (4,1),(4,7),(5,2),(5,3),(5,4),(5,5),(5,6),(5,7),(6,1),(6,2);
/*!40000 ALTER TABLE `resc_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (4,'user','ROLE_USER'),(5,'admin','ROLE_ADMIN'),(6,'no','no'),(7,'acl user create','ACL_USER_CREATE'),(8,'acl user admin','ACL_USER_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign`
--

DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `ID` bigint(20) NOT NULL auto_increment,
  `LATE` int(11) default NULL,
  `LEAVE_STATUS` int(11) default NULL,
  `QUIT` int(11) default NULL,
  `TIME` datetime default NULL,
  `WORK` int(11) default NULL,
  `EMPLOYEE_ID` bigint(20) default NULL,
  `SIGN_STATE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK26D5BD51093B13` (`EMPLOYEE_ID`),
  KEY `FK26D5BD595E27A6` (`SIGN_STATE_ID`),
  CONSTRAINT `FK26D5BD595E27A6` FOREIGN KEY (`SIGN_STATE_ID`) REFERENCES `sign_state` (`ID`),
  CONSTRAINT `FK26D5BD51093B13` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sign`
--

LOCK TABLES `sign` WRITE;
/*!40000 ALTER TABLE `sign` DISABLE KEYS */;
INSERT INTO `sign` VALUES (3,2,2,2,'2009-04-08 00:00:00',2,1,1);
/*!40000 ALTER TABLE `sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign_state`
--

DROP TABLE IF EXISTS `sign_state`;
CREATE TABLE `sign_state` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(20) default NULL,
  `TIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sign_state`
--

LOCK TABLES `sign_state` WRITE;
/*!40000 ALTER TABLE `sign_state` DISABLE KEYS */;
INSERT INTO `sign_state` VALUES (1,'上班','2009-04-07 00:00:00');
/*!40000 ALTER TABLE `sign_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(10) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,'3','状态1');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCN` varchar(200) default NULL,
  `PASSWORD` varchar(50) default NULL,
  `STATUS` int(11) default NULL,
  `USERNAME` varchar(50) default NULL,
  `DEPT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK27E3CBF7E526C2` (`DEPT_ID`),
  CONSTRAINT `FK27E3CBF7E526C2` FOREIGN KEY (`DEPT_ID`) REFERENCES `dept` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','098f6bcd4621d373cade4e832627b4f6',1,'test',NULL),(2,'user','ee11cbb19052e40b07aac0ca060c23ee',1,'user',NULL),(3,'no','7fa3b767c460b54a2be4d49030b349c7',1,'no',NULL),(4,'12','12',12,'12',1),(5,'1','c4ca4238a0b923820dcc509a6f75849b',1,'1',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ROLE_ID`,`USER_ID`),
  KEY `FKBC16F46A927EF102` (`USER_ID`),
  KEY `FKBC16F46AED542D22` (`ROLE_ID`),
  CONSTRAINT `FKBC16F46AED542D22` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FKBC16F46A927EF102` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,4),(1,5),(1,7),(1,8),(2,6),(2,7),(3,8),(4,4),(4,5),(4,6),(4,7),(4,8);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2009-05-07  4:46:16
