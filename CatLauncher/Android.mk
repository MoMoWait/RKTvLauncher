LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_PACKAGE_NAME := RKTvLauncher
LOCAL_CERTIFICATE := platform
LOCAL_DEX_PREOPT := false
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/src/main/res \
       $(TOP)/frameworks/support/v7/appcompat/res \
       frameworks/support/v17/leanback/res \
       frameworks/support/v7/recyclerview/res \
       $(TOP)/frameworks/support/v7/cardview/res
LOCAL_AAPT_FLAGS := --auto-add-overlay \
      --extra-packages android.support.v7.appcompat:android.support.v17.leanback:android.support.v7.recyclerview:android.support.v7.cardview
LOCAL_STATIC_JAVA_LIBRARIES := android-support-v17-leanback
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-appcompat
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-recyclerview
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-cardview
LOCAL_STATIC_JAVA_LIBRARIES += libxutils
LOCAL_STATIC_JAVA_LIBRARIES += libandroidutils
LOCAL_STATIC_JAVA_LIBRARIES += libjunit
LOCAL_STATIC_JAVA_LIBRARIES += libfm
LOCAL_PROGUARD_ENABLED := disabled
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := libxutils:libs/xutils.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += libandroidutils:libs/androidutils.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += libjunit:libs/junit.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += libfm:libs/fm.jar
include $(BUILD_MULTI_PREBUILT)
