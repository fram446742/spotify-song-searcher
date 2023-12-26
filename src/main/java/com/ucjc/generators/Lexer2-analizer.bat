@echo off

REM Set the JAVA_HOME and PATH variables
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Set the CLASSPATH variable
set "CLASSPATH=%JAVA_HOME%"

REM Search for the "lib" folder by traversing up the directories
set "LIB_DIR="
:FindLib
if exist "%CD%\lib" (
    set "LIB_DIR=%CD%\lib"
) else (
    cd..
    if "%CD%\"==":\" goto :NotFound
    goto FindLib
)

:NotFound
if not defined LIB_DIR (
    echo "lib" folder not found.
    exit /b 1
)

REM Print the "lib" directory before executing the JFlex command
echo Library in use: %LIB_DIR%

REM Change to the "analizers" directory
cd %~dp0\..\analizers

REM Create the "\generated" folder if it does not exist
if not exist "..\generated" mkdir "..\generated"

echo Current directory: %CD%

REM Execute the JFlex command in the current location
java -jar "%LIB_DIR%\jflex\jflex-full-1.9.1.jar" lexer2.jflex

REM Verify if the file Lexer2.java was created before attempting to move it
if exist "Lexer2.java" (
    REM Verify if the destination file already exists
    if exist "..\generated\Lexer2.java" (
        choice /C YN /M "Destination file already exists. Do you want to replace it? (Y/N) "
        if errorlevel 2 (
            echo File not replaced.
        ) else (
            move /Y "Lexer2.java" "..\generated\" > nul
            echo File replaced and moved to: ..\generated
        )
    ) else (
        REM If the destination file doesn't exist, simply move Lexer2.java
        move "Lexer2.java" "..\generated\" > nul
        echo File moved to: ..\generated
    )
) else (
    echo Error: Lexer2.java was not created.
    echo The generation process may have failed.
)


REM Return to the original directory
cd %~dp0

pause
