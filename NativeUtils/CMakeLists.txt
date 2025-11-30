cmake_minimum_required(VERSION 4.0)
project(NativeUtils)

set(CMAKE_CXX_STANDARD 20)

add_library(NativeUtils SHARED dllmain.cpp
        jni.h
        jni_md.h
        jvmti.h)
