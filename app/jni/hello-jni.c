#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "hello-jni"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

void Java_com_tequeno_app_ndk_NdkActivity_testInC(JNIEnv *env, jobject thiz) {
    LOGI("hello world");
    LOGE("hello world");
}