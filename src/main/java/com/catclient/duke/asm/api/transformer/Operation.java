package com.catclient.duke.asm.api.transformer;

import com.catclient.duke.utils.asm.DescParser;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Field;
import java.util.List;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public interface Operation {
    static boolean isLoadOpe(int opcode) {
        for (Field field : Opcodes.class.getFields())
            if (field.getName().endsWith("LOAD"))
                try {
                    if ((int) field.get(null) == opcode)
                        return true;
                } catch (Throwable ignored) {
                }
        return false;
    }

    static boolean isStoreOpe(int opcode) {
        for (Field field : Opcodes.class.getFields())
            if (field.getName().endsWith("STORE"))
                try {
                    if ((int) field.get(null) == opcode)
                        return true;
                } catch (Throwable ignored) {
                }
        return false;
    }

    static MethodNode findTargetMethod(List<MethodNode> list, String owner, String name, String desc) {
        desc = DescParser.mapDesc(desc);
        String finalDesc = desc;
        return list.stream().filter(m -> m.name.equals(name) && m.desc.equals(finalDesc)).findFirst().orElse(null);
    }
}
