@echo off
setlocal
cd /d "%~dp0"
title FaithLink Console Game

if not exist out mkdir out
javac --release 17 -d out src\*.java
if errorlevel 1 (
    echo.
    echo Compilation failed. Fix the error shown above, then run this file again.
    pause
    exit /b 1
)

cls
java -cp out Main

echo.
pause
endlocal
