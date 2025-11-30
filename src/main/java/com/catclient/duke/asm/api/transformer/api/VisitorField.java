package com.catclient.duke.asm.api.transformer.api;

import com.catclient.duke.asm.api.TransformerManager;
import org.objectweb.asm.MethodVisitor;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public class VisitorField extends MethodVisitor {
    private final String[] target;

    public VisitorField(String[] target) {
        super(TransformerManager.ASM_API);
        this.target = target;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        target[0] = owner + "." + name + " " + descriptor;
    }

    public String[] getTarget() {
        return target;
    }
}
