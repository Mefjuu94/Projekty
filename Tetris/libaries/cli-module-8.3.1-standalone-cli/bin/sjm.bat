@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\lib

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\simple-java-mail-8.3.1.jar;"%REPO%"\core-module-8.3.1.jar;"%REPO%"\jakarta.mail-2.0.1.jar;"%REPO%"\jmail-1.4.1.jar;"%REPO%"\java-reflection-4.0.0.jar;"%REPO%"\jakarta.activation-2.0.1.jar;"%REPO%"\picocli-3.9.0.jar;"%REPO%"\therapi-runtime-javadoc-0.13.0.jar;"%REPO%"\authenticated-socks-module-8.3.1.jar;"%REPO%"\dkim-module-8.3.1.jar;"%REPO%"\utils-mail-dkim-3.0.0.jar;"%REPO%"\utils-data-fetcher-4.0.2.jar;"%REPO%"\commons-nulls-1.0.4.jar;"%REPO%"\eddsa-0.3.0.jar;"%REPO%"\smime-module-8.3.1.jar;"%REPO%"\utils-mail-smime-2.1.2.jar;"%REPO%"\bcjmail-jdk15to18-1.75.jar;"%REPO%"\bcprov-jdk15to18-1.75.jar;"%REPO%"\bcutil-jdk15to18-1.75.jar;"%REPO%"\bcpkix-jdk15to18-1.75.jar;"%REPO%"\jakarta.activation-api-2.0.1.jar;"%REPO%"\jakarta.mail-api-2.0.1.jar;"%REPO%"\batch-module-8.3.1.jar;"%REPO%"\smtp-connection-pool-2.2.0.jar;"%REPO%"\clustered-object-pool-2.0.2.jar;"%REPO%"\generic-object-pool-2.0.1.jar;"%REPO%"\outlook-module-8.3.1.jar;"%REPO%"\outlook-message-parser-1.9.6.jar;"%REPO%"\poi-5.2.2.jar;"%REPO%"\commons-codec-1.15.jar;"%REPO%"\commons-collections4-4.4.jar;"%REPO%"\commons-math3-3.6.1.jar;"%REPO%"\SparseBitSet-1.2.jar;"%REPO%"\poi-scratchpad-5.2.2.jar;"%REPO%"\rtf-to-html-1.0.1.jar;"%REPO%"\kryo-5.0.0-RC1.jar;"%REPO%"\reflectasm-1.11.7.jar;"%REPO%"\objenesis-2.6.jar;"%REPO%"\minlog-1.3.0.jar;"%REPO%"\kryo-serializers-0.45.jar;"%REPO%"\log4j-slf4j-impl-2.17.1.jar;"%REPO%"\log4j-api-2.17.1.jar;"%REPO%"\log4j-core-2.17.1.jar;"%REPO%"\commons-io-2.7.jar;"%REPO%"\jetbrains-runtime-annotations-1.0.1.jar;"%REPO%"\throwing-function-1.5.1.jar;"%REPO%"\slf4j-api-1.7.32.jar;"%REPO%"\cli-module-8.3.1.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="sjm" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" org.simplejavamail.cli.SimpleJavaMail %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
