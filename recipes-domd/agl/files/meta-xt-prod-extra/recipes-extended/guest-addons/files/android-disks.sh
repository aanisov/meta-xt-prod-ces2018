#!/bin/sh

ROOTFS_TYPE=`stat -f -c %T /`
ADISKS_FOLDER="/var/run/android-disks"
RAW_FOLDER="/xt/android/"

if [ $ROOTFS_TYPE == "nfs" ]
then
# rootfs is nfs, expect Android partitions are raw files in /xt/android
    ln -s /dev/loop1 $ADISKS_FOLDER/system
    ln -s /dev/loop2 $ADISKS_FOLDER/vendor
    ln -s /dev/loop3 $ADISKS_FOLDER/misc
    ln -s /dev/loop4 $ADISKS_FOLDER/userdata

    cd $RAW_FOLDER && /xt/scripts/doma_loop_setup.sh || exit 1
else
    ln -s /dev/mmcblk1p2 $ADISKS_FOLDER/system
    ln -s /dev/mmcblk1p3 $ADISKS_FOLDER/vendor
    ln -s /dev/mmcblk1p5 $ADISKS_FOLDER/misc
    ln -s /dev/mmcblk1p6 $ADISKS_FOLDER/userdata
fi

xenstore-write drivers/disks/status ready
