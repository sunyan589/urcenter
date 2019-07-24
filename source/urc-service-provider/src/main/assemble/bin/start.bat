@echo off

rem check JAVA_HOME & java

rem BASE_HOME=/usr/local/service/urc-service-provider
SET BASE_HOME=../
SET JAVA_OPTS=-server -Xms2048m -Xmx2048m -Xmn1024m -Xss256k

rem performance Options
SET JAVA_OPTS=%JAVA_OPTS% -XX:+AggressiveOpts
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseBiasedLocking
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseFastAccessorMethods
SET JAVA_OPTS=%JAVA_OPTS% -XX:+DisableExplicitGC
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseParNewGC
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseConcMarkSweepGC
SET JAVA_OPTS=%JAVA_OPTS% -XX:+CMSParallelRemarkEnabled
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseCMSCompactAtFullCollection
SET JAVA_OPTS=%JAVA_OPTS% -XX:+UseCMSInitiatingOccupancyOnly
SET JAVA_OPTS=%JAVA_OPTS% -XX:CMSInitiatingOccupancyFraction=75

rem GC Log Options
rem JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
rem JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"
rem JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
rem debug Options
SET JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=8065,server=y,suspend=n
rem ==============================================================================

SET TEMP_CLASSPATH=%BASE_HOME%\conf
for /f "delims=\" %%a in ('dir /b /a-d /o-d "%BASE_HOME%\lib\*.jar"') do (
  rem echo %%a
  SET TEMP_CLASSPATH=%TEMP_CLASSPATH%;%%a
)

rem ==============================================================================
rem startup server
rem SET RUN_CMD=%JAVA_HOME%/bin/java
SET RUN_CMD=-classpath %TEMP_CLASSPATH%
SET RUN_CMD=%RUN_CMD% %JAVA_OPTS%
SET RUN_CMD="%RUN_CMD% com.yks.urc.main.UrcServiceProviderStarter"

rem echo %JAVA_HOME%

echo %RUN_CMD%

java %RUN_CMD%

echo "urc-service-provider start !"
rem ==============================================================================