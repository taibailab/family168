
@echo off

if "%1" == "migrate" goto migrate
if "%1" == "rollback" goto rollback
if "%1" == "view" goto view
if "%1" == "rebuild" goto rebuild
if "%1" == "init" goto init
if "%1" == "clean" goto clean
goto end


:migrate
echo "migrate"
if "%2" == "" goto migrate-no-ver
call ant migrate -Dversion=%2
goto end
:migrate-no-ver
call ant migrate
goto end


:rollback
echo "rollback"
if "%2" == "" goto rollback-no-ver
call ant rollback -Dversion=%2
goto end
:rollback-no-ver
call ant rollback
goto end

:view
echo "view"
call ant view
goto end


:rebuild
echo "rebuild"
call ant rebuild
goto end


:init
echo "init"
call ant init
goto end


:clean
echo "clean"
call ant clean
goto end


:end

