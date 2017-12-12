SUMMARY = "config files and scripts for a guest"
DESCRIPTION = "config files and scripts for guest which will be running for tests"

require inc/xt_shared_env.inc

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://bridge-nfsroot.sh \
    file://bridge.sh \
    file://doma_loop_detach.sh \
    file://doma_loop_setup.sh \
    file://android-disks.sh \
    file://displbe.service \
    file://adisks.service \
    file://adisks.conf \
    file://eth0.network \
    file://xenbr0.netdev \
    file://xenbr0.network \
"

S = "${WORKDIR}"

inherit systemd

PACKAGES += " \
    ${PN}-bridge-config \
    ${PN}-displbe-service \
    ${PN}-adisks-service \
"

FILES_${PN}-bridge-config = " \
    ${sysconfdir}/systemd/network/eth0.network \
    ${sysconfdir}/systemd/network/xenbr0.netdev \
    ${sysconfdir}/systemd/network/xenbr0.network \
"

SYSTEMD_PACKAGES = " \
    ${PN}-displbe-service \
    ${PN}-adisks-service \
"

SYSTEMD_SERVICE_${PN}-displbe-service = " displbe.service"

SYSTEMD_SERVICE_${PN}-adisks-service = " adisks.service"

FILES_${PN}-adisks-service = " \
    ${systemd_system_unitdir}/adisks.service \
    ${sysconfdir}/tmpfiles.d/adisks.conf \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}/android-disks.sh \
"

FILES_${PN}-displbe-service = " \
    ${systemd_system_unitdir}/displbe.service \
"

do_install() {
    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}
    install -m 0744 ${WORKDIR}/*.sh ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/*.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/adisks.conf ${D}${sysconfdir}/tmpfiles.d/adisks.conf

    install -d ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/*.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/*.netdev ${D}${sysconfdir}/systemd/network
}

FILES_${PN} = " \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}/*.sh \
"

