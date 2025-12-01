package com.catclient.duke.module.impl.movement;

import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.event.impl.PacketEvent;
import com.catclient.duke.event.impl.SprintEvent;
import com.catclient.duke.module.Category;
import com.catclient.duke.module.Module;
import org.lwjgl.glfw.GLFW;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 01:39
 * @Filename：Sprint
 */
public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "自动疾跑", "", "", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_I);
    }

    @EventTarget
    public void onSprint(SprintEvent event) {
        event.setSprint(true);
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        System.out.println(event.getPacket());
    }
}
