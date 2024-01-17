#!/bin/bash

# 变量初始化
readonly scriptName=${0}
appPath=${1}
# 初始化 APP 路径: 尾部拼接 "/" 如果不存在
[ "${appPath: -1}" != "/" ] && appPath="${appPath}/"
readonly appJarName=${2}
readonly appName=${appJarName:0:-4}
readonly option=${3}
readonly OPTIONS=("start" "stop" "restart" "status")
readonly JVM_CONFIG="-Xms128M -Xmx128M -XX:+UseG1GC -XX:HeapDumpPath=${appName}-heap.hprof"

# 用法
usage() {
    optionsStr=""
    for ((i = 0; i < ${#OPTIONS[*]}; i++)); do
        optionsStr="${optionsStr}${OPTIONS[i]} | "
    done
    optionsStr=${optionsStr:0:-3}
    echo "Usage: sh ${scriptName} [APP_PATH] [APP_JAR_NAME] [${optionsStr}]"
    exit 1
}

# 启动
start() {
    if isRunning; then
        echo "${appName} is already running. pid=${pid}."
    else
        # shellcheck disable=SC2086
        nohup java ${JVM_CONFIG} -jar "${appPath}${appJarName}" >"${appName}"_startup.log 2>&1 &
        echo "Start ${appName} application success."
    fi
}

# 停止
stop() {
    if isRunning; then
        kill -9 "${pid}"
        echo "Stop ${appName} application success."
    else
        echo "${appName} is not running."
    fi
}

# 重启
restart() {
    stop
    start
}

# 应用状态
status() {
    if isRunning; then
        echo "${appName} is running. Pid is ${pid}."
    else
        echo "${appName} is not running."
    fi
}

# 应用是否正在运行
isRunning() {
    pid=$(pgrep -a java | grep "${appName}" | grep -v grep | awk '{print $1}')
    if [ -z "${pid}" ]; then
        return 1
    else
        return 0
    fi
}

# 检查应用名称参数
invalidAppName() {
    count=$(pgrep -a java | grep "${appName}" | grep -v grep | awk '{print $1}' | wc -l)
    if [ "${count}" -gt 1 ]; then
        echo -e "\n应用名称参数: ${appName} 匹配了多个进程. 进程信息: \n$(pgrep -a java | grep "${appName}" | grep -v grep)\n"
        exit 1
    fi
}

# 检查应用路径
invalidPath() {
    if [ ! -f "${appPath}${appJarName}" ]; then
        echo "文件路径不存在: ${appPath}${appJarName}"
        exit 1
    fi
}

# =================================================================================================

# 执行
case "${option}" in
"start")
    invalidPath
    invalidAppName
    start
    ;;
"stop")
    invalidPath
    invalidAppName
    stop
    ;;
"status")
    invalidPath
    invalidAppName
    status
    ;;
"restart")
    invalidPath
    invalidAppName
    restart
    ;;
*)
    usage
    ;;
esac