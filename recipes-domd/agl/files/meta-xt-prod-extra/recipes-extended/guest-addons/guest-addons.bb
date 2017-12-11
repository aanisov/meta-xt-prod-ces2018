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
    file://displbe.service \
"

S = "${WORKDIR}"

inherit systemd

PACKAGES += " \
    ${PN}-displbe-service \
"

SYSTEMD_PACKAGES = "${PN}-displbe-service"

SYSTEMD_SERVICE_${PN}-displbe-service = " displbe.service"

FILES_${PN}-displbe-service = " \
    ${systemd_system_unitdir}/displbe.service \
"

do_install() {
    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}
    install -m 0744 ${WORKDIR}/*.sh ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/*.service ${D}${systemd_system_unitdir}
}

FILES_${PN} += " \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}/*.sh \
"

