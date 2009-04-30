del *.bak

set classpath=hsqldb-1.8.0.7.jar
java org.hsqldb.Server

if errorlevel 1 pause
exit
