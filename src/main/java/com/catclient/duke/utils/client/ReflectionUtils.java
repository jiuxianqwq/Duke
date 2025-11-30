package com.catclient.duke.utils.client;

import com.catclient.duke.utils.mapping.MappingUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 02:39
 * @Filename：ReflectionUtils
 */
public class ReflectionUtils {
    public static Class<?> getClass(String classFullName) {
        try {
            return Class.forName(classFullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String notObfuscatedName, String docs, Class<?>... parameterTypes) {
        String mappedName = MappingUtils.get(clazz, notObfuscatedName, docs);
        if (mappedName != null) {
            try {
                Method method = clazz.getDeclaredMethod(mappedName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String notObfuscatedName) {
        String mappedName = MappingUtils.get(clazz, notObfuscatedName, null);
        if (mappedName != null) {
            try {
                Field field = clazz.getDeclaredField(mappedName);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getFieldValue(Class<?> clazz, String notObfuscatedName, Object obj) {
        String mappedName = MappingUtils.get(clazz, notObfuscatedName, null);
        if (mappedName != null) {
            try {
                Field field = clazz.getDeclaredField(mappedName);
                field.setAccessible(true);
                Object value = field.get(obj);
                return value;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Boolean setFieldValue(Class<?> clazz, String notObfuscatedName, Object obj, Object value) {
        String mappedName = MappingUtils.get(clazz, notObfuscatedName, null);
        if (mappedName != null) {
            try {
                Field field = clazz.getDeclaredField(mappedName);
                field.setAccessible(true);
                field.set(obj, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
