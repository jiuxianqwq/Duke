package com.catclient.duke.asm.api;

import a.a;
import com.catclient.duke.utils.mapping.MappingUtils;
import com.catclient.duke.asm.api.transformer.Operation;
import com.catclient.duke.asm.api.transformer.impl.InjectOperation;
import com.catclient.duke.utils.asm.ASMTransformer;
import com.catclient.duke.utils.asm.ASMUtils;
import com.catclient.duke.utils.client.LogUtils;
import lombok.Getter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

@Getter
public class Transformer {
    public final ArrayList<ASMTransformer> transformers;
    private final ArrayList<Operation> operations;

    public Transformer() {
        this.operations = new ArrayList<>();
        this.transformers = new ArrayList<>();
        this.operations.add(new InjectOperation());
    }

    public void addTransformer(ASMTransformer transformer) {
        transformers.add(transformer);
    }

    public Map<String, byte[]> transform() {
        Map<String, byte[]> classMap = new HashMap<>();

        for (ASMTransformer transformer : transformers) {
            if (transformer.getTarget() == null) {
                System.out.println("Transformer " + transformer.getClass().getName() + " has no target class, skipping.");
                continue;
            }
            String name = transformer.getTarget().getName().replace('/', '.');
            byte[] bytes = classMap.get(name);
            ClassNode targetNode;
            if (bytes == null) {
                ClassNode node = null;
                while (node == null) {
                    try {
                        bytes = a.getClassesBytes(transformer.getTarget());
                        node = ASMUtils.node(bytes);
                    } catch (Throwable ignored) {
                    }
                }
                targetNode = node;
            } else targetNode = ASMUtils.node(bytes);
            for (Method method : transformer.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getParameterCount() != 1) continue;
                ASMTransformer.Inject annotation = method.getAnnotation(ASMTransformer.Inject.class);
                if (annotation == null) continue;
                MethodNode node = Operation.findTargetMethod(targetNode.methods, targetNode.name, MappingUtils.get(transformer.getTarget(), annotation.method(), annotation.desc()), annotation.desc());
                try {
                    method.invoke(transformer, node);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (TransformerManager.debugging) LogUtils.throwableBug(targetNode.name, e);
                }
            }
            byte[] class_bytes = ASMUtils.rewriteClass(targetNode);
            classMap.put(name, class_bytes);
        }
        return classMap;
    }
}
