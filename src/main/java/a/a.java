package a;


import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.TransformerManager;
import com.catclient.duke.utils.asm.ASMTransformer;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod(Duke.ModID)
public class a {

    public static HashMap<Class, byte[]> cachedClassBytes = new HashMap<>();

    public a() {
        Duke.b("8964");
    }

    public static byte[] getClassesBytes(Class<?> clazz) {
        try {
            return cachedClassBytes.get(clazz);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static byte[] a(Class capturedClass, ClassLoader itsClassLoader, String className, byte[] classBytesIn) {
        try {
//            System.out.println("[nativeCallJava]\n" + capturedClass.getName());
            for (ASMTransformer asmTransformer : TransformerManager.transformer.transformers) {
                if (asmTransformer.getTarget() == capturedClass) {
                    cachedClassBytes.putIfAbsent(capturedClass, classBytesIn);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        return classBytesIn;
    }

    public static native void a(Class cls);

    public static native void a(Class<?> targetClass, byte[] newClassBytes);
}