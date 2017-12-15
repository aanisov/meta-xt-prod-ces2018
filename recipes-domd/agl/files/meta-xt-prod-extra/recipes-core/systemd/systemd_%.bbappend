FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://0001-networkd-link_enter_configured-remove-assert-4800.patch \
"

PACKAGECONFIG_append = " networkd"

USERADD_ERROR_DYNAMIC = "warn"
