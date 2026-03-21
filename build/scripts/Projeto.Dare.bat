@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Projeto.Dare startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and PROJETO_DARE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Projeto.Dare-1.0.0-plain.jar;%APP_HOME%\lib\spring-boot-starter-web-3.2.5.jar;%APP_HOME%\lib\selenium-java-4.21.0.jar;%APP_HOME%\lib\poi-ooxml-5.2.5.jar;%APP_HOME%\lib\poi-5.2.5.jar;%APP_HOME%\lib\spring-boot-starter-json-3.2.5.jar;%APP_HOME%\lib\spring-boot-starter-3.2.5.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-3.2.5.jar;%APP_HOME%\lib\spring-webmvc-6.1.6.jar;%APP_HOME%\lib\spring-web-6.1.6.jar;%APP_HOME%\lib\selenium-chrome-driver-4.14.1.jar;%APP_HOME%\lib\selenium-devtools-v123-4.21.0.jar;%APP_HOME%\lib\selenium-devtools-v124-4.21.0.jar;%APP_HOME%\lib\selenium-devtools-v125-4.21.0.jar;%APP_HOME%\lib\selenium-firefox-driver-4.14.1.jar;%APP_HOME%\lib\selenium-devtools-v85-4.14.1.jar;%APP_HOME%\lib\selenium-edge-driver-4.14.1.jar;%APP_HOME%\lib\selenium-ie-driver-4.14.1.jar;%APP_HOME%\lib\selenium-safari-driver-4.14.1.jar;%APP_HOME%\lib\selenium-support-4.14.1.jar;%APP_HOME%\lib\selenium-chromium-driver-4.14.1.jar;%APP_HOME%\lib\selenium-remote-driver-4.14.1.jar;%APP_HOME%\lib\selenium-manager-4.14.1.jar;%APP_HOME%\lib\selenium-http-4.14.1.jar;%APP_HOME%\lib\selenium-json-4.14.1.jar;%APP_HOME%\lib\selenium-os-4.14.1.jar;%APP_HOME%\lib\selenium-api-4.14.1.jar;%APP_HOME%\lib\commons-codec-1.16.1.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\commons-math3-3.6.1.jar;%APP_HOME%\lib\commons-io-2.15.0.jar;%APP_HOME%\lib\SparseBitSet-1.3.jar;%APP_HOME%\lib\poi-ooxml-lite-5.2.5.jar;%APP_HOME%\lib\xmlbeans-5.2.0.jar;%APP_HOME%\lib\spring-boot-starter-logging-3.2.5.jar;%APP_HOME%\lib\log4j-to-slf4j-2.21.1.jar;%APP_HOME%\lib\log4j-api-2.21.1.jar;%APP_HOME%\lib\commons-compress-1.25.0.jar;%APP_HOME%\lib\curvesapi-1.08.jar;%APP_HOME%\lib\spring-boot-autoconfigure-3.2.5.jar;%APP_HOME%\lib\spring-boot-3.2.5.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\spring-context-6.1.6.jar;%APP_HOME%\lib\spring-aop-6.1.6.jar;%APP_HOME%\lib\spring-beans-6.1.6.jar;%APP_HOME%\lib\spring-expression-6.1.6.jar;%APP_HOME%\lib\spring-core-6.1.6.jar;%APP_HOME%\lib\snakeyaml-2.2.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.15.4.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.15.4.jar;%APP_HOME%\lib\jackson-annotations-2.15.4.jar;%APP_HOME%\lib\jackson-core-2.15.4.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.15.4.jar;%APP_HOME%\lib\jackson-databind-2.15.4.jar;%APP_HOME%\lib\tomcat-embed-websocket-10.1.20.jar;%APP_HOME%\lib\tomcat-embed-core-10.1.20.jar;%APP_HOME%\lib\tomcat-embed-el-10.1.20.jar;%APP_HOME%\lib\micrometer-observation-1.12.5.jar;%APP_HOME%\lib\auto-service-annotations-1.1.1.jar;%APP_HOME%\lib\guava-32.1.2-jre.jar;%APP_HOME%\lib\opentelemetry-exporter-logging-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-extension-autoconfigure-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-extension-autoconfigure-spi-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-trace-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-metrics-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-logs-1.31.0.jar;%APP_HOME%\lib\opentelemetry-sdk-common-1.31.0.jar;%APP_HOME%\lib\opentelemetry-semconv-1.28.0-alpha.jar;%APP_HOME%\lib\opentelemetry-api-events-1.31.0-alpha.jar;%APP_HOME%\lib\opentelemetry-extension-incubator-1.31.0-alpha.jar;%APP_HOME%\lib\opentelemetry-api-1.31.0.jar;%APP_HOME%\lib\opentelemetry-context-1.31.0.jar;%APP_HOME%\lib\byte-buddy-1.14.13.jar;%APP_HOME%\lib\logback-classic-1.4.14.jar;%APP_HOME%\lib\jul-to-slf4j-2.0.13.jar;%APP_HOME%\lib\spring-jcl-6.1.6.jar;%APP_HOME%\lib\micrometer-commons-1.12.5.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-3.33.0.jar;%APP_HOME%\lib\error_prone_annotations-2.18.0.jar;%APP_HOME%\lib\failsafe-3.3.2.jar;%APP_HOME%\lib\commons-exec-1.3.jar;%APP_HOME%\lib\logback-core-1.4.14.jar;%APP_HOME%\lib\slf4j-api-2.0.13.jar


@rem Execute Projeto.Dare
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PROJETO_DARE_OPTS%  -classpath "%CLASSPATH%"  %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable PROJETO_DARE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%PROJETO_DARE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
