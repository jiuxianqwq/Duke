package com.catclient.duke.asm.api;

import a.ASMTransformers.ClientPacketListenerTransformer;
import a.ASMTransformers.ConnectionTransformer;
import a.ASMTransformers.KeyboardHandlerTransformer;
import a.ASMTransformers.LocalPlayerTransformer;
import a.a;
import com.catclient.duke.Duke;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public class TransformerManager {
    public static final boolean debugging = true;
    public static final int ASM_API = Opcodes.ASM5;
    public static Transformer transformer;
    public static Map<String, byte[]> classBytesMap;
    private static boolean loaded = false;

    public static void init() {
        if (loaded) return;


        loaded = true;

        transformer = new Transformer();

        try {
//            在这里注册你的变形金刚
//            transformer.addTransformer(new MinecraftTransformer());
            transformer.addTransformer(new KeyboardHandlerTransformer());
            transformer.addTransformer(new LocalPlayerTransformer());
            transformer.addTransformer(new ConnectionTransformer());
            transformer.addTransformer(new ClientPacketListenerTransformer());

            for (ASMTransformer asmTransformer : TransformerManager.transformer.transformers) {
                a.a(asmTransformer.getTarget());
            }


            classBytesMap = transformer.transform();


            for (Map.Entry<String, byte[]> entry : classBytesMap.entrySet()) {
                try {
                    a.b(Class.forName(entry.getKey()), entry.getValue());
                    if (debugging)
                        Files.write(new File(Duke.CLIENT_FOLDER, entry.getKey() + ".class").toPath(), entry.getValue());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    System.out.println("Failed to reload class:" + entry.getKey() + "\n" + ex.getMessage());
                }
            }


        } catch (Throwable ex) {
            System.out.println("Inject failed.");
            throw ex;
        }
    }


}