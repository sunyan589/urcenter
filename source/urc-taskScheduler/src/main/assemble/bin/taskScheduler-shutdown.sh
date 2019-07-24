#!/bin/sh

#check JAVA_HOME & java
if [ -z "$JAVA_HOME" ] ; then
    JAVA_HOME=/usr/local/java/jdk1.8.0_151
fi


#==============================================================================
#set JAVA_OPTS
JAVA_OPTS="-Xss256k"
#==============================================================================

#stop Server
$(ps -ef | grep TaskSchedulerStarter | awk '{print $2}' | xargs kill -9 )

echo "Shutdown urc-taskScheduler is done....."
#==============================================================================
