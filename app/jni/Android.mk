LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := hello-jni # 每个模块名称必须唯一，且不含任何空格 最终生曾动态库 libhello-jni.so
LOCAL_SOURCE_FILES := hello-jni.c # 列举源文件 以空格分隔多个文件
LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)