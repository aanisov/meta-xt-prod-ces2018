IMAGE_INSTALL_append = " \
    guest-addons \
    libxenbe \
    displbe \
    guest-addons-displbe-service \
    guest-addons-android-disks-service \
    kmscube \
    sndbe \
"

#Add Xen and additional packages to build
IMAGE_INSTALL_append = " \
    nftables \
    dhcp-client \
    xen-base \
    xen-flask \
    xen-xenstat \
    pulseaudio \
    alsa-utils \
"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " arm-trusted-firmware"

# u-boot
EXTRA_IMAGEDEPENDS += " u-boot"

# Dom0 is a generic ARMv8 machine w/o machine overrides,
# but still needs to know which system we are building,
# e.g. Salvator-X M3 or H3, for instance
# So, we provide machine overrides from this build to Dom0
EXTRA_IMAGEDEPENDS += " domd-install-machine-overrides"

# Do not support secure environment
IMAGE_INSTALL_remove = " \
    optee-linuxdriver \
    optee-linuxdriver-armtz \
    optee-client \
"

CORE_IMAGE_BASE_INSTALL_remove += "gtk+3-demo clutter-1.0-examples"

