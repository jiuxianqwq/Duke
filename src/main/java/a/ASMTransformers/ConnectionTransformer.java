package a.ASMTransformers;

import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.event.api.EventType;
import com.catclient.duke.event.impl.PacketEvent;
import com.catclient.duke.utils.client.ReflectionUtils;
import com.catclient.duke.utils.wrapper.Wrapper;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 19:28
 * @Filename：ConnectionTransformer
 */
public class ConnectionTransformer extends ASMTransformer implements Wrapper {
    public ConnectionTransformer() {
        super(Connection.class);
    }

    @Inject(method = "channelRead0", desc = "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V")
    public void channelRead0(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
                MethodInsnNode insnNode = (MethodInsnNode) abstractInsnNode;
                if (insnNode.owner.equals("net/minecraft/network/Connection") && insnNode.name.equals("genericsFtw") && insnNode.desc.equals("(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V")) {
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ConnectionTransformer.class), "hookGenericsFtw", "(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/Connection;)V", false));
                    methodNode.instructions.insert(insnNode, insnList);
                    methodNode.instructions.remove(insnNode);
                }
            }
        }
    }

    public static void hookGenericsFtw(Packet<?> packet, PacketListener packetListener, Connection connection) {
        PacketEvent packetEvent = new PacketEvent(EventType.INCOMING, packet);
        Duke.getInstance().getEventManager().call(packetEvent);
        if (!packetEvent.isCancelled()) {
            Packet<PacketListener> eventPacket = (Packet<PacketListener>) packetEvent.getPacket();
            eventPacket.handle(packetListener);
        }
    }

    @Inject(method = "send", desc = "(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V")
    public void send(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                MethodInsnNode insnNode = (MethodInsnNode) abstractInsnNode;
                if (insnNode.owner.equals("net/minecraft/network/Connection") && insnNode.name.equals("sendPacket") && insnNode.desc.equals("(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V")) {
                    methodNode.instructions.remove(abstractInsnNode.getPrevious().getPrevious().getPrevious());
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ConnectionTransformer.class), "hookSendPacket", "(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;Lnet/minecraft/network/Connection;)V", false));
                    methodNode.instructions.insert(insnNode, insnList);
                    methodNode.instructions.remove(abstractInsnNode);
                }
            }
        }
    }

    public static void hookSendPacket(Packet<?> packet, PacketSendListener packetSendListener, Connection connection) {
        PacketEvent packetEvent = new PacketEvent(EventType.OUTGOING, packet);
        Duke.getInstance().getEventManager().call(packetEvent);
        if (!packetEvent.isCancelled()) {
            Method sendPacket = ReflectionUtils.getMethod(connection.getClass(), "sendPacket", "(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V", Packet.class, PacketSendListener.class);
            try {
                sendPacket.invoke(connection, packetEvent.getPacket(), packetSendListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
