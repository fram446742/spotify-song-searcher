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
java -jar %~dp0..\resources\java-cup-11b.jar -parser Parser -symbols Simbolos Sym.cup

REM Regresar al directorio original
cd %~dp0

pause
