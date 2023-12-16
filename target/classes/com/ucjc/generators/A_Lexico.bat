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

REM Create the "\compiled\generated" folder if it does not exist
if not exist "..\compiled\generated" mkdir "..\compiled\generated"

echo Current directory: %CD%

REM Execute the JFlex command in the current location
java -jar "%LIB_DIR%\jflex\jflex-full-1.9.1.jar" lexer.jflex

setlocal

REM Verify if the file Lexer.java was created before attempting to move it
if exist "Lexer.java" (
    REM Verify if the destination file already exists
    if exist "..\compiled\generated\Lexer.java" (
        REM Rename existing Lexer.java in the destination folder, adding a number
        set "counter="
        :CheckCounterLexer
        if exist "..\compiled\generated\Lexer%counter%.java" (
            set /a "counter+=1"
            goto CheckCounterLexer
        )

        REM Rename existing Lexer.java before moving the new file
        ren "..\compiled\generated\Lexer.java" "Lexer%counter%.java"

        move "Lexer.java" "..\compiled\generated\" > nul

        echo File moved to: ..\compiled\generated
    ) else (
        REM If the destination file doesn't exist, simply move Lexer.java
        move "Lexer.java" "..\compiled\generated\" > nul
        echo File moved to: ..\compiled\generated
    )
) else (
    echo Error: Lexer.java was not created.
    echo The generation process may have failed.
)

endlocal

REM Return to the original directory
cd %~dp0

pause
