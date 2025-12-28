@ECHO OFF
SETLOCAL

SET MAVEN_PROJECTBASEDIR=%~dp0
SET MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%
SET WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Maven Wrapper bulunamadı: %WRAPPER_JAR%
  ECHO Not: .mvn\wrapper\maven-wrapper.jar dosyası repoda olmalı.
  EXIT /B 1
)

SET JAVA_EXE=java
IF DEFINED JAVA_HOME (
  IF EXIST "%JAVA_HOME%\bin\java.exe" SET JAVA_EXE="%JAVA_HOME%\bin\java.exe"
)

%JAVA_EXE% -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*

ENDLOCAL
