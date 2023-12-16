@echo off

:FindLibFolder
REM Check if the "lib" folder exists in the current directory
if exist "%CD%\lib\" (
    echo Encontrado el directorio con la carpeta "lib": %CD%
    goto :ContinueScript
) else (
    REM Move up one level and check again
    cd /d "%CD%\.."
    goto :FindLibFolder
)

:ContinueScript
REM Resto del script...
echo Ahora estás en la carpeta "spotify-song-searcher"
echo Directorio actual: %CD%

pause
