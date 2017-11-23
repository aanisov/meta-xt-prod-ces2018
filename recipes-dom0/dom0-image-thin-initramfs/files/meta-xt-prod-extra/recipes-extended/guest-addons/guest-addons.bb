SUMMARY = "config files and scripts for a guest"
DESCRIPTION = "config files and scripts for guest which will be running for tests"

require inc/xt_shared_env.inc

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "\
    file://domd-salvator-x-m3.cfg \
    file://domd-salvator-x-h3.cfg \
    file://doma.cfg \
"

S = "${WORKDIR}"

DOMD_CONFIG_salvator-x-m3-xt = "domd-salvator-x-m3.cfg"
DOMD_CONFIG_salvator-x-h3-xt = "domd-salvator-x-h3.cfg"
DOMA_CONFIG_salvator-x-m3-xt = "doma-salvator-x-m3.cfg"
DOMA_CONFIG_salvator-x-h3-xt = "doma-salvator-x-h3.cfg"

do_install() {
    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}
    install -m 0744 ${WORKDIR}/${DOMD_CONFIG} ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/domd.cfg
    install -m 0744 ${WORKDIR}/${DOMA_CONFIG} ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/doma.cfg
}

FILES_${PN} += " \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/*.cfg \
"

