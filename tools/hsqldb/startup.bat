del *.bak
set classpath=hsqldb.jar
java org.hsqldb.Server

if errorlevel 1 pause
exit
