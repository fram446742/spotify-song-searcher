@echo off

REM Establecer la ubicación del archivo .bat como directorio de trabajo
cd %~dp0

REM Establecer la variable JAVA_HOME y PATH
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Establecer la variable CLASSPATH
set "CLASSPATH=%JAVA_HOME%"

REM Cambiar al directorio "analizers"
cd ..\analizers

REM Ejecutar el comando en la ubicación actual
java -jar ..\resources\jflex-1.9.1\jflex-1.9.1\lib\jflex-full-1.9.1.jar lexer.jflex

REM Regresar al directorio original
cd %~dp0

pause

