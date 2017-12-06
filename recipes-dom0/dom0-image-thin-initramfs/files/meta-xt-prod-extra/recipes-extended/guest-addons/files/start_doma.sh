#!/bin/sh

#echo "Waiting for backends"

check_be () {
    BE=$1
    DOMD_ID=$2
    for be in $BE
    do
        #echo "process $DOMD_ID $BE"
        status=`xenstore-read /local/domain/$DOMD_ID/drivers/$be/status`
        if [ $? -eq 1 ] ; then
            return 1
        fi
        #echo "status $status"
        if [ "$status" != "ready" ] ; then
            return 1
        fi
    done
    return 0
}

exit_function () {
    if [ -n "$XENSTORE_WATCH" ] ; then
	#echo "killing xenstore-watch"
	kill $XENSTORE_WATCH
    fi
    #echo "removing pipe"
    rm -rf $pipe
    exit 0
}

trap exit_function SIGINT SIGTERM

BE=$@

#echo "BE = \"$BE\""

DOMD_ID=`xl domid DomD`

if [ $? -eq 1 ] ; then
    echo "DomD is not running"
    echo "Exiting"
    exit 1
fi

pipe=/tmp/doma_xenstore

if ! mkfifo $pipe ; then
    echo "Failed to create a pipe, is the script already running?"
    echo "Exiting."
    exit 1
fi

xenstore-watch /local/domain/$DOMD_ID/drivers > $pipe &
XENSTORE_WATCH=$!

#echo "xenstore-watch PID $XENSTORE_WATCH"

while read event ; do
    if check_be "$BE" $DOMD_ID ; then
        xl create /xt/dom.cfg/doma.cfg
        break
    fi
done <$pipe

exit_function
