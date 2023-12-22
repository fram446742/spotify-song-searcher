@echo off

REM Set JAVA_HOME and PATH variables
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Set CLASSPATH variable
set "CLASSPATH=%JAVA_HOME%"

REM Search for the "lib" folder by moving up in the directories
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

REM Print the "lib" directory before executing the Java Cup command
echo Using Library: %LIB_DIR%

REM Change to the "analizers" directory
cd %~dp0\..\analizers

REM Create the "\compiled\generated" folder if it doesn't exist
if not exist "..\compiled\generated" mkdir "..\compiled\generated"

echo Current Directory: %CD%

REM Execute the Java Cup command in the current location
java -jar "%LIB_DIR%\java-cup-11b.jar" -parser Parser -symbols Sym "parser.cup"

REM Check if Parser.java file was created before attempting to move it
if exist "Parser.java" (
    if exist "..\compiled\generated\Parser.java"  (
        REM Check and move Parser.java to the new location with ~
        choice /C YN /M "Destination file 'Parser.java' already exists. Do you want to replace it? (Y/N) "
        if errorlevel 2 (
            echo File not replaced.
        ) else (
            move /Y "Parser.java" "..\compiled\generated\" > nul
            echo File replaced and moved to: ..\compiled\generated
        )
    ) else (
        REM If the destination file doesn't exist, simply move Parser.java
        move "Parser.java" "..\compiled\generated\" > nul
        echo File moved to: ..\compiled\generated
    )
) else (
    echo Error: Parser.java was not created.
    echo The generation process may have failed.
)

REM Check if Sym.java file was created before attempting to move it
if exist "Sym.java" (
    if exist "..\compiled\generated\Sym.java" (
        REM Check and move Sym.java to the new location with ~
        choice /C YN /M "Destination file 'Sym.java' already exists. Do you want to replace it? (Y/N) "
        if errorlevel 2 (
            echo File not replaced.
        ) else (
            move /Y "Sym.java" "..\compiled\generated\" > nul
            echo File replaced and moved to: ..\compiled\generated
        )
    ) else (
        REM If the destination file doesn't exist, simply move Sym.java
        move "Sym.java" "..\compiled\generated\" > nul
        echo File moved to: ..\compiled\generated
    )
) else (
    echo Error: Sym.java was not created.
    echo The generation process may have failed.
)



echo Files moved to: ..\compiled\generated

REM Return to the original directory
cd %~dp0

pause
