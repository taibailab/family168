set MAVEN_OPTS=-Xms256M -Xmx256M -XX:MaxNewSize=256m -XX:MaxPermSize=256m

cd bin\dashboard-report
start startup.bat
cd ..\..

call mvn clean site dashboard:persist dashboard:dashboard

cd bin\dashboard-report
start shutdown.bat
cd ..\..
