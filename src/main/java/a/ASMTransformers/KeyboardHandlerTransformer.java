package a.ASMTransformers;

import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.event.impl.KeyboardEvent;
import com.catclient.duke.utils.mapping.MappingUtils;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 00:21
 * @Filename：KeyboardHandlerTransformer
 */
public class KeyboardHandlerTransformer extends ASMTransformer {
    public KeyboardHandlerTransformer() {
        super(KeyboardHandler.class);
    }

    public static void onKeyPress(long window, int key, int scancode, int action, int mods) {
        Duke.getInstance().getEventManager().call(new KeyboardEvent(window, key, scancode, action, mods));
    }

    @Inject(method = "keyPress", desc = "(JIIII)V")
    public void injectKeyPress(MethodNode methodNode) {
        AbstractInsnNode[] insnNodes = methodNode.instructions.toArray();
        for (AbstractInsnNode abstractInsnNode : insnNodes) {
            if (abstractInsnNode.getOpcode() == Opcodes.GETFIELD) {
                FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
                if (fieldInsnNode.owner.equals("net/minecraft/client/Minecraft") && fieldInsnNode.name.equals(MappingUtils.get(Minecraft.class, "screen", null))) {
                    AbstractInsnNode aload = fieldInsnNode.getPrevious().getPrevious();
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(Opcodes.LLOAD, 1));
                    insnList.add(new VarInsnNode(Opcodes.ILOAD, 3));
                    insnList.add(new VarInsnNode(Opcodes.ILOAD, 4));
                    insnList.add(new VarInsnNode(Opcodes.ILOAD, 5));
                    insnList.add(new VarInsnNode(Opcodes.ILOAD, 6));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(KeyboardHandlerTransformer.class), "onKeyPress", "(JIIII)V", false));
                    methodNode.instructions.insertBefore(aload, insnList);
                    break;
                }
            }
        }
    }
}
