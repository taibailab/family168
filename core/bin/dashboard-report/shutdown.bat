del *.bak

set classpath=hsqldb-1.8.0.7.jar
java org.hsqldb.util.SqlTool --noAutoFile --autoCommit --rcFile sqltool.rc test import.sql

if errorlevel 1 pause
exit
