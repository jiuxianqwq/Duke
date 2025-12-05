package com.catclient.duke.config;

import com.catclient.duke.utils.wrapper.Wrapper;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/6 06:28
 * @Filename：ConfigManager
 */
public class ConfigManager implements Wrapper {
    public String currentConfig = "latest";

    public ConfigManager() {
        new Config(currentConfig).load();
    }

    public void saveConfig() {
        new Config(currentConfig).save();
        if (!currentConfig.equals("latest")) new Config("latest").save();
    }

    public void loadConfig() {
        new Config(currentConfig).load();
        if (!currentConfig.equals("latest")) new Config("latest").save();
    }

    public void saveConfig(String name) {
        currentConfig = name;
        new Config(currentConfig).save();
        if (!currentConfig.equals("latest")) new Config("latest").save();
    }

    public void loadConfig(String name) {
        currentConfig = name;
        new Config(currentConfig).load();
        if (!currentConfig.equals("latest")) new Config("latest").save();
    }
}
