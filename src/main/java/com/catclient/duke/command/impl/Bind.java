package com.catclient.duke.command.impl;

import com.catclient.duke.Duke;
import com.catclient.duke.command.Command;
import com.catclient.duke.config.ConfigManager;
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
            ChatUtils.addChatMessage("用法: .bind <模块英文或中文名> [按键名称]");
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

        if (args.length > 2) {
            String keyName = args[2].toUpperCase();
            int key = -1;
            try {
                // 尝试反射获取 GLFW key code
                java.lang.reflect.Field field = GLFW.class.getField("GLFW_KEY_" + keyName);
                key = field.getInt(null);
            } catch (Exception e) {
                // 如果找不到对应的字段，尝试直接解析数字（虽然不太可能，但作为备用）
                try {
                     key = Integer.parseInt(keyName);
                } catch (NumberFormatException ignored) {}
            }

            if (key != -1) {
                targetModule.setKey(key);
                ChatUtils.addChatMessage("模块 ", targetModule.getName(), " 已绑定到 ", keyName, " 键");
                Duke.getInstance().getConfigManager().saveConfig();
            } else {
                 ChatUtils.addChatMessage("无效的按键名称: ", keyName);
            }
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
            ChatUtils.addChatMessage("模块 ", module.getName(), " 已绑定到 ", GLFW.glfwGetKeyName(key, 0), " 键");
            this.module = null;
            event.setBind(true);
            Duke.getInstance().getConfigManager().saveConfig();
        }
    }
}
