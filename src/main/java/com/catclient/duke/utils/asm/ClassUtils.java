package com.catclient.duke.utils.asm;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public class ClassUtils {
    public static Class<?> getClass(String name) {
        Class<?> clazz = null;
        name = name.replace('/', '.');
        try {
            clazz = Class.forName(name);
        } catch (Throwable ignored) {
        }
        return clazz;
    }
}
