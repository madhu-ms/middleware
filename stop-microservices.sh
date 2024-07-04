#!/bin/bash


echo "--------------------------------------------------------------"

# Name of the JAR file
JAR_NAME="discoveryService.jar"

# Find the process ID (PID) of the running JAR file
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

# Check if the PID exists
if [ -z "$PID" ]; then
    echo "No process found running for $JAR_NAME"
else
    echo "Stopping process $PID running for $JAR_NAME"
    kill -9 $PID
    echo "Process $PID has been stopped."
fi

echo "--------------------------------------------------------------"


# Name of the JAR file
JAR_NAME="apiGateway.jar"

# Find the process ID (PID) of the running JAR file
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

# Check if the PID exists
if [ -z "$PID" ]; then
    echo "No process found running for $JAR_NAME"
else
    echo "Stopping process $PID running for $JAR_NAME"
    kill -9 $PID
    echo "Process $PID has been stopped."
fi

echo "--------------------------------------------------------------"


# Name of the JAR file
JAR_NAME="tms.jar"

# Find the process ID (PID) of the running JAR file
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

# Check if the PID exists
if [ -z "$PID" ]; then
    echo "No process found running for $JAR_NAME"
else
    echo "Stopping process $PID running for $JAR_NAME"
    kill -9 $PID
    echo "Process $PID has been stopped."
fi

echo "--------------------------------------------------------------"


# Name of the JAR file
JAR_NAME="transactionsService.jar"

# Find the process ID (PID) of the running JAR file
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

# Check if the PID exists
if [ -z "$PID" ]; then
    echo "No process found running for $JAR_NAME"
else
    echo "Stopping process $PID running for $JAR_NAME"
    kill -9 $PID
    echo "Process $PID has been stopped."
fi

echo "--------------------------------------------------------------"

# Name of the JAR file
JAR_NAME="ams.jar"

# Find the process ID (PID) of the running JAR file
PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

# Check if the PID exists
if [ -z "$PID" ]; then
    echo "No process found running for $JAR_NAME"
else
    echo "Stopping process $PID running for $JAR_NAME"
    kill -9 $PID
    echo "Process $PID has been stopped."
fi

echo "--------------------------------------------------------------"
