SUMMARY = "ARMV8 Generic LITMUS-RT system built with yocto"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

# override what xt-distro wants as machine as we don't depend on HW
#MACHINE = "genericarmv8"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-litmusrt \
"

# Set default revision to pyro
BRANCH ?= "pyro"

SRC_URI = " \
    git://git.yoctoproject.org/poky;destsuffix=repo/poky;branch=${BRANCH} \
    git://github.com/aanisov/meta-litmusrt.git;destsuffix=repo/meta-litmusrt;branch=master \
"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

configure_versions_base() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}
    # override what xt-distro wants as machine as we don't depend on HW
    base_update_conf_value ${local_conf} MACHINE "generic-armv8-xt"
    base_update_conf_value ${local_conf} SERIAL_CONSOLE "115200 hvc0"
    base_update_conf_value ${local_conf} "PREFERRED_PROVIDER_virtual/kernel" "linux-litmusrt"
}

python do_configure_append() {
    bb.build.exec_func("configure_versions_base", d)
}

################################################################################
# Generic
################################################################################
XT_BB_IMAGE_TARGET = "litmusrt-image-minimal"


FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../inc:"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
# N.B. xt_shared_env.inc MUST be listed AFTER meta-xt-prod-extra
XT_QUIRK_UNPACK_SRC_URI += "\
    file://meta-xt-images-extra;subdir=repo \
    file://meta-xt-prod-extra;subdir=repo \
    file://xt_shared_env.inc;subdir=repo/meta-xt-prod-extra/inc \
"

XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-extra \
    meta-xt-prod-extra \
"

