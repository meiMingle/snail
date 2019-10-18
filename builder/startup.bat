@echo off

rem 加载配置文件
call config.bat

echo 启动项目【%project%】

rem 设置环境变量
set path=.\%runtime%\bin

rem 启动命令
call start "snail" javaw -server -XX:NewRatio=2 -XX:SurvivorRatio=2 -Xms128m -Xmx256m -jar %jar%

exit
