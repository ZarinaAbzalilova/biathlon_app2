@ECHO OFF
SETLOCAL

SET DIR=%~dp0
SET GRADLE_WRAPPER_JAR=%DIR%gradle\wrapper\gradle-wrapper.jar

IF NOT EXIST "%GRADLE_WRAPPER_JAR%" (
  ECHO Gradle wrapper jar not found. Please ensure gradle wrapper is set up.
  EXIT /B 1
)

IF NOT "%JAVA_HOME%"=="" (
  SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVA_EXE=java.exe
)

"%JAVA_EXE%" -jar "%GRADLE_WRAPPER_JAR%" %*

ENDLOCAL

