package com.catclient.duke.utils.mapping;

import com.catclient.duke.utils.wrapper.Wrapper;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.IOException;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public class MappingUtils implements Wrapper {
    private static IMappingFile mappingFile;

    static {
        try {
            mappingFile = IMappingFile.load(new File(mappingFolder.getAbsolutePath() + "\\mappings.tsrg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNotObfuscated() {
        try {
            Class.forName("net.minecraft.client.Minecraft").getDeclaredField("instance");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String get(Class<?> clazz, String notObfuscatedName, String desc) {
        if (isNotObfuscated()) return notObfuscatedName;

        if (clazz == null || notObfuscatedName == null) {
            return null;
        }

        // 提前计算类内部名称，避免重复调用
        String className = Type.getInternalName(clazz);
        IMappingFile.IClass mappingClass = mappingFile.getClass(className);

        // 如果未混淆或映射类不存在，直接返回原始名称
        if (mappingClass == null) {
            return notObfuscatedName;
        }

        // 分拆方法/字段逻辑，减少嵌套
        if (desc != null) {
            IMappingFile.IMethod method = mappingClass.getMethod(notObfuscatedName, desc);
            return (method != null && method.getMapped() != null) ? method.getMapped() : notObfuscatedName;
        } else {
            IMappingFile.IField field = mappingClass.getField(notObfuscatedName);
            return (field != null && field.getMapped() != null) ? field.getMapped() : notObfuscatedName;
        }
    }
}