@echo off
title Sistema DARE

echo Iniciando backend...

cd /d %~dp0

start cmd /k "%~dp0gradlew.bat bootRun"

echo Aguardando backend iniciar...
timeout /t 6 >nul

echo Abrindo sistema...
start "" "%~dp0src\main\frontend\index.html"

exit