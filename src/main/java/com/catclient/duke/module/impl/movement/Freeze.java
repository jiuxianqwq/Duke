package com.catclient.duke.module.impl.movement;

import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.event.impl.PlayerTickEvent;
import com.catclient.duke.module.Category;
import com.catclient.duke.module.Module;
import org.lwjgl.glfw.GLFW;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 03:43
 * @Filename：Freeze
 */
public class Freeze extends Module {
    public Freeze() {
        super("Freeze", "不许动", "", "", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_X);
    }

    @EventTarget
    public void onPlayerTick(PlayerTickEvent event) {
        event.setCancelled(true);
    }
}
