set REPO=%USERPROFILE%\.m2\repository\
set classpath=%REPO%\hsqldb\hsqldb\1.8.0.7\hsqldb-1.8.0.7.jar

java org.hsqldb.util.SqlTool --noAutoFile --autoCommit --rcFile sqltool.rc test shutdown.sql

pause