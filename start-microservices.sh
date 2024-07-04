#!/bin/bash


echo "--------------------------------------------------------------"
# Name of the JAR file
JAR_NAME="discoveryService.jar"

# Path to the directory where the JAR file is located
JAR_PATH="./discovery-service/build/libs"

# JVM options (optional)
JVM_OPTS="-Xms512m -Xmx1024m"

# Log file path (optional)
LOG_FILE="./logs/discoveryService/discoveryService.log"

#echo $JAR_PATH/$JAR_NAME

# Check if the JAR file exists
if [ -f "$JAR_PATH/$JAR_NAME" ]; then
    echo "Starting $JAR_NAME..."

    # Run the JAR file
    nohup java $JVM_OPTS -jar "$JAR_PATH/$JAR_NAME" > "$LOG_FILE" 2>&1 &

    # Capture the PID of the process
    PID=$!
    echo "$JAR_NAME started with PID $PID"
else
    echo "JAR file $JAR_PATH/$JAR_NAME not found!"
fi

echo "--------------------------------------------------------------"


# Name of the JAR file
JAR_NAME="apiGateway.jar"

# Path to the directory where the JAR file is located
JAR_PATH="./api-gateway/build/libs"

# JVM options (optional)
JVM_OPTS="-Xms512m -Xmx1024m"

# Log file path (optional)
LOG_FILE="./logs/apiGateway/apiGateway.log"

#echo $JAR_PATH/$JAR_NAME

# Check if the JAR file exists
if [ -f "$JAR_PATH/$JAR_NAME" ]; then
    echo "Starting $JAR_NAME..."

    # Run the JAR file
    nohup java $JVM_OPTS -jar "$JAR_PATH/$JAR_NAME" > "$LOG_FILE" 2>&1 &

    # Capture the PID of the process
    PID=$!
    echo "$JAR_NAME started with PID $PID"
else
    echo "JAR file $JAR_PATH/$JAR_NAME not found!"
fi
#
#echo "--------------------------------------------------------------"
#
## Name of the JAR file
#JAR_NAME="tms.jar"
#
## Path to the directory where the JAR file is located
#JAR_PATH="./terminal-management-service/build/libs"
#
## JVM options (optional)
#JVM_OPTS="-Xms512m -Xmx1024m"
#
## Log file path (optional)
#LOG_FILE="./logs/tms/tms.log"
#
##echo $JAR_PATH/$JAR_NAME
#
## Check if the JAR file exists
#if [ -f "$JAR_PATH/$JAR_NAME" ]; then
#    echo "Starting $JAR_NAME..."
#
#    # Run the JAR file
#    nohup java $JVM_OPTS -jar "$JAR_PATH/$JAR_NAME" > "$LOG_FILE" 2>&1 &
#
#    # Capture the PID of the process
#    PID=$!
#    echo "$JAR_NAME started with PID $PID"
#else
#    echo "JAR file $JAR_PATH/$JAR_NAME not found!"
#fi
#
#echo "--------------------------------------------------------------"
#
## Name of the JAR file
#JAR_NAME="transactionsService.jar"
#
## Path to the directory where the JAR file is located
#JAR_PATH="./transactions-service/build/libs"
#
## JVM options (optional)
#JVM_OPTS="-Xms512m -Xmx1024m"
#
## Log file path (optional)
#LOG_FILE="./logs/transactionsService/transactionsService.log"
#
##echo $JAR_PATH/$JAR_NAME
#
## Check if the JAR file exists
#if [ -f "$JAR_PATH/$JAR_NAME" ]; then
#    echo "Starting $JAR_NAME..."
#
#    # Run the JAR file
#    nohup java $JVM_OPTS -jar "$JAR_PATH/$JAR_NAME" > "$LOG_FILE" 2>&1 &
#
#    # Capture the PID of the process
#    PID=$!
#    echo "$JAR_NAME started with PID $PID"
#else
#    echo "JAR file $JAR_PATH/$JAR_NAME not found!"
#fi

echo "--------------------------------------------------------------"

# Name of the JAR file
JAR_NAME="ams.jar"

# Path to the directory where the JAR file is located
JAR_PATH="./attestation-monitoring-service/build/libs"

# JVM options (optional)
JVM_OPTS="-Xms512m -Xmx1024m"

# Log file path (optional)
LOG_FILE="./logs/ams/ams.log"

#echo $JAR_PATH/$JAR_NAME

# Check if the JAR file exists
if [ -f "$JAR_PATH/$JAR_NAME" ]; then
    echo "Starting $JAR_NAME..."

    # Run the JAR file
    nohup java $JVM_OPTS -jar "$JAR_PATH/$JAR_NAME" > "$LOG_FILE" 2>&1 &

    # Capture the PID of the process
    PID=$!
    echo "$JAR_NAME started with PID $PID"
else
    echo "JAR file $JAR_PATH/$JAR_NAME not found!"
fi
echo "--------------------------------------------------------------"


