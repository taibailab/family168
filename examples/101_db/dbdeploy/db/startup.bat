del *.bak
set classpath=..\lib\hsqldb.jar
java org.hsqldb.Server

if errorlevel 1 pause
exit
