package com.catclient.duke.command.impl;

import com.catclient.duke.Duke;
import com.catclient.duke.command.Command;
import com.catclient.duke.utils.player.ChatUtils;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/6
 * @Filename：ConfigCommand
 */
public class ConfigCommand extends Command {

    public ConfigCommand() {
        super("config", "cfg");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            ChatUtils.addChatMessage("用法: .config <load/save> [配置名]");
            return;
        }

        String action = args[1].toLowerCase();

        if (action.equals("load")) {
            if (args.length > 2) {
                String configName = args[2];
                Duke.getInstance().getConfigManager().loadConfig(configName);
                ChatUtils.addChatMessage("已加载配置: " + configName);
            } else {
                Duke.getInstance().getConfigManager().loadConfig();
                ChatUtils.addChatMessage("已加载配置: " + Duke.getInstance().getConfigManager().currentConfig);
            }
        } else if (action.equals("save")) {
            if (args.length > 2) {
                String configName = args[2];
                Duke.getInstance().getConfigManager().saveConfig(configName);
                ChatUtils.addChatMessage("已保存配置: " + configName);
            } else {
                Duke.getInstance().getConfigManager().saveConfig();
                ChatUtils.addChatMessage("已保存配置: " + Duke.getInstance().getConfigManager().currentConfig);
            }
        } else {
            ChatUtils.addChatMessage("用法: .config <load/save> [配置名]");
        }
    }
}
