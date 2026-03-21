@echo off
setlocal

set SRC_DIR=com\dare
set OUT_DIR=out\production\Projeto.Dare

if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"
javac -d "%OUT_DIR%" %SRC_DIR%\*.java
if errorlevel 1 exit /b 1

echo Build succeeded.
