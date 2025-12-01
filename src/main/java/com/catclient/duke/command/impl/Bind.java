package com.catclient.duke.command.impl;

import com.catclient.duke.Duke;
import com.catclient.duke.command.Command;
import com.catclient.duke.event.api.annotations.EventPriority;
import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.event.impl.KeyboardEvent;
import com.catclient.duke.module.Module;
import com.catclient.duke.utils.player.ChatUtils;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 04:44
 * @Filename：Bind
 */
public class Bind extends Command {
    private Module module;
    private boolean enter = false;

    public Bind() {
        super("bind", "b");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            ChatUtils.addChatMessage("用法: §b.bind §7<§b模块英文或中文名§7> , 再按下你要绑定的按键");
            return;
        }
        List<Module> modules = Duke.getInstance().getModuleManager().getModules();
        Module targetModule = null;
        for (Module module : modules) {
            if (module.getEnName().equalsIgnoreCase(args[1]) || module.getCnName().equalsIgnoreCase(args[1])) {
                targetModule = module;
                break;
            }
        }
        if (targetModule == null) {
            ChatUtils.addChatMessage("找不到模块 ", args[1]);
            return;
        }

        ChatUtils.addChatMessage("请按下你要绑定的按键");
        this.module = targetModule;
        enter = true;
    }

    @EventTarget
    @EventPriority(0)
    public void onKey(KeyboardEvent event) {
        if (enter) {
            enter = false;
            return;
        }
        if (this.module != null && mc.screen == null) {
            int key = event.getKey();
            this.module.setKey(key);
            ChatUtils.addChatMessage("§7模块 §b", module.getName(), "§7 已绑定到 §b", GLFW.glfwGetKeyName(key, 0), "§7 键");
            this.module = null;
            event.setBind(true);
        }
    }
}
