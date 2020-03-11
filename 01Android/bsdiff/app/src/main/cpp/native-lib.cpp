#include <jni.h>
#include <string>
#include "bspatch.h"

//extern "

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

extern "C" JNIEXPORT jint JNICALL
Java_com_cp_android_bsdiff_MainActivity_bspatch(
        JNIEnv *env,
        jobject /* this */,
        jstring oldpath,
        jstring newpath,
        jstring patchpath) {
    int argc = 4;
    char *argv[argc];
    argv[0] = (char *) "bsdiff";
    argv[1] = (char *) env->GetStringUTFChars(oldpath, 0);
    argv[2] = (char *) env->GetStringUTFChars(newpath, 0);
    argv[3] = (char *) env->GetStringUTFChars(patchpath, 0);

    printf("old apk = %s \n", argv[1]);
    printf("new apk = %s \n", argv[2]);
    printf("patch apk = %s \n", argv[3]);
    printf("patch apk = %s \n", argv[3]);
//    int ret = generateApk(argc, argv);

    int ret = 0;
    env->ReleaseStringUTFChars(oldpath, argv[1]);
    env->ReleaseStringUTFChars(newpath, argv[2]);
    env->ReleaseStringUTFChars(patchpath, argv[3]);
    return ret;
}