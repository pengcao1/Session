#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_cp_android_bsdiff_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_com_cp_android_bsdiff_MainActivity_getResultFromJni(
        JNIEnv *env,
        jobject /* this */,
        jint a,
        jint b) {
    return a + b + 1;
}