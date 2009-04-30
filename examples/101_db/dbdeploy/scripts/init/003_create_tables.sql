DROP TABLE DBDEPLOY_DEMO.CUSTOMER IF EXISTS;

CREATE TABLE DBDEPLOY_DEMO.CUSTOMER (
CUSTOMER_ID INTEGER
,FIRST_NAME VARCHAR(50)
,MIDDLE_NAMES VARCHAR(100)
,LAST_NAME VARCHAR(50)
,DOB DATE
);

ALTER TABLE DBDEPLOY_DEMO.CUSTOMER ADD CONSTRAINT PK_CUSTOMER PRIMARY KEY (CUSTOMER_ID);
