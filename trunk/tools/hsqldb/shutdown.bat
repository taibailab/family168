del *.bak
set classpath=hsqldb.jar
java org.hsqldb.util.SqlTool --noAutoFile --autoCommit --rcFile sqltool.rc test import.sql

if errorlevel 1 pause
exit
