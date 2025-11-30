package com.catclient.duke.asm.api.transformer.api;

import com.catclient.duke.asm.api.TransformerManager;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public class VisitorLocal extends MethodVisitor {
    private final String name;
    private final int[] varIndex;

    public VisitorLocal(String name, int[] varIndex) {
        super(TransformerManager.ASM_API);
        this.name = name;
        this.varIndex = varIndex;
    }

    @Override
    public void visitLocalVariable(String varName, String descriptor, String signature, Label start, Label end, int index) {
        if (name.equals(varName))
            varIndex[0] = index;
        super.visitLocalVariable(varName, descriptor, signature, start, end, index);
    }

    public int[] getTarget() {
        return varIndex;
    }
}
