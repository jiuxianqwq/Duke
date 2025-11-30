package a.ASMTransformers;

import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.event.impl.SprintEvent;
import com.catclient.duke.utils.mapping.MappingUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 01:53
 * @Filename：LocalPlayerTransformer
 */
public class LocalPlayerTransformer extends ASMTransformer {
    public LocalPlayerTransformer() {
        super(LocalPlayer.class);
    }

    @Inject(method = "aiStep", desc = "()V")
    public void aiStep(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                if (methodInsnNode.owner.equals("net/minecraft/client/KeyMapping") && methodInsnNode.name.equals(MappingUtils.get(KeyMapping.class, "isDown", "()Z")) && methodInsnNode.desc.equals("()Z")) {
                    InsnList insnList = new InsnList();
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(LocalPlayerTransformer.class), "hookSprint", "()Z", false));
                    methodNode.instructions.remove(methodInsnNode.getPrevious().getPrevious().getPrevious().getPrevious());
                    methodNode.instructions.remove(methodInsnNode.getPrevious().getPrevious().getPrevious());
                    methodNode.instructions.remove(methodInsnNode.getPrevious().getPrevious());
                    methodNode.instructions.remove(methodInsnNode.getPrevious());
                    methodNode.instructions.insert(methodInsnNode, insnList);
                    methodNode.instructions.remove(methodInsnNode);
                }
            }
        }
    }

    public static boolean hookSprint() {
        SprintEvent sprintEvent = new SprintEvent(options.keySprint.isDown());
        Duke.getInstance().getEventManager().call(sprintEvent);
        return sprintEvent.isSprint();
    }
}
