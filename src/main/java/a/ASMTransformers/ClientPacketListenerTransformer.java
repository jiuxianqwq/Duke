package a.ASMTransformers;

import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.event.impl.ChatEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 03:21
 * @Filename：ClientPacketListenerTransformer
 */
public class ClientPacketListenerTransformer extends ASMTransformer {
    public ClientPacketListenerTransformer() {
        super(ClientPacketListener.class);
    }

    public static ChatEvent hookSendChat(String msg) {
        ChatEvent chatEvent = new ChatEvent(msg);
        Duke.getInstance().getEventManager().call(chatEvent);
        return chatEvent;
    }

    public static boolean canSend(ChatEvent chatEvent) {
        return !chatEvent.isCancelled();
    }

    public static String getMsg(ChatEvent chatEvent) {
        return chatEvent.getMsg();
    }

    @Inject(method = "sendChat", desc = "(Ljava/lang/String;)V")
    public void sendChat(MethodNode methodNode) {
        InsnList insnList = new InsnList();
        LabelNode returnLabel = new LabelNode();
        LabelNode labelNode = new LabelNode();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ClientPacketListenerTransformer.class), "hookSendChat", "(Ljava/lang/String;)L" + Type.getInternalName(ChatEvent.class) + ";", false));
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 2));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ClientPacketListenerTransformer.class), "canSend", "(L" + Type.getInternalName(ChatEvent.class) + ";)Z", false));
        insnList.add(new JumpInsnNode(Opcodes.IFEQ, returnLabel));
        insnList.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
        insnList.add(returnLabel);
        insnList.add(new InsnNode(Opcodes.RETURN));
        insnList.add(labelNode);
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ClientPacketListenerTransformer.class), "getMsg", "(L" + Type.getInternalName(ChatEvent.class) + ";)Ljava/lang/String;", false));
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 1));
        methodNode.instructions.insert(insnList);
    }
}
