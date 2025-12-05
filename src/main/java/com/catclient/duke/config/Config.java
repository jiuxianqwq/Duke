package com.catclient.duke.config;

import com.catclient.duke.Duke;
import com.catclient.duke.module.Module;
import com.catclient.duke.utils.player.ChatUtils;
import com.catclient.duke.utils.wrapper.Wrapper;
import com.catclient.duke.value.Value;
import com.catclient.duke.value.impl.BooleanValue;
import com.catclient.duke.value.impl.ModeValue;
import com.catclient.duke.value.impl.NumberValue;
import com.catclient.duke.value.impl.TextValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 22:34
 * @Filename：Config
 */
public class Config implements Wrapper {
    private final String name;
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Config(String name) {
        this.name = name;
        this.file = new File(configFolder, name + ".json");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    public void save() {
        try {
            List<Module> modules = Duke.getInstance().getModuleManager().getModules();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Time", System.currentTimeMillis());
            jsonObject.addProperty("Version", Duke.CLIENT_VERSION);

            JsonObject modulesObject = new JsonObject();

            for (Module m : modules) {
                JsonObject moduleObject = new JsonObject();
                moduleObject.addProperty("State", m.isEnabled());
                moduleObject.addProperty("Key", m.getKey());

                JsonObject valueObject = new JsonObject();
                for (Value v : m.getValues()) {
                    if (v instanceof BooleanValue bv) {
                        valueObject.addProperty(bv.getEnName(), bv.get());
                    }
                    if (v instanceof ModeValue mv) {
                        valueObject.addProperty(mv.getEnName(), mv.get());
                    }
                    if (v instanceof NumberValue nv) {
                        if (nv.isRange()) {
                            JsonObject nvObject = new JsonObject();
                            nvObject.addProperty("Max", nv.getMax());
                            nvObject.addProperty("Min", nv.getMin());
                            valueObject.add(nv.getEnName(), nvObject);
                        } else {
                            valueObject.addProperty(nv.getEnName(), nv.get());
                        }
                    }
                    if (v instanceof TextValue tv) {
                        valueObject.addProperty(tv.getEnName(), tv.get());
                    }
                }
                moduleObject.add("Values", valueObject);
                modulesObject.add(m.getEnName(), moduleObject);
            }
            jsonObject.add("Modules", modulesObject);

            if (!file.exists()) {
                file.createNewFile();
            }

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(gson.toJson(jsonObject));
            }

        } catch (Exception e) {
            System.err.println("Failed to save config: " + name);
            e.printStackTrace();
            ChatUtils.addChatMessage("Failed to save config: ", name);
            ChatUtils.addChatMessage(e.getMessage());
        }
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }
        try (java.io.FileReader reader = new java.io.FileReader(file)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            if (jsonObject == null || !jsonObject.has("Modules")) return;

            JsonObject modulesObject = jsonObject.getAsJsonObject("Modules");
            List<Module> modules = Duke.getInstance().getModuleManager().getModules();

            for (Module m : modules) {
                try {
                    if (modulesObject.has(m.getEnName())) {
                        JsonObject moduleObject = modulesObject.getAsJsonObject(m.getEnName());
                        if (moduleObject.has("State")) {
                            m.setEnabled(moduleObject.get("State").getAsBoolean());
                        }
                        if (moduleObject.has("Key")) {
                            m.setKey(moduleObject.get("Key").getAsInt());
                        }

                        if (moduleObject.has("Values")) {
                            JsonObject valueObject = moduleObject.getAsJsonObject("Values");
                            for (Value v : m.getValues()) {
                                if (valueObject.has(v.getEnName())) {
                                    try {
                                        if (v instanceof BooleanValue bv) {
                                            bv.set(valueObject.get(v.getEnName()).getAsBoolean());
                                        } else if (v instanceof ModeValue mv) {
                                            mv.set(valueObject.get(v.getEnName()).getAsString());
                                        } else if (v instanceof NumberValue nv) {
                                            if (nv.isRange()) {
                                                JsonObject nvObject = valueObject.getAsJsonObject(v.getEnName());
                                                if (nvObject.has("Max"))
                                                    nv.setMax(nvObject.get("Max").getAsFloat());
                                                if (nvObject.has("Min"))
                                                    nv.setMin(nvObject.get("Min").getAsFloat());
                                            } else {
                                                nv.setValue(valueObject.get(v.getEnName()).getAsFloat());
                                            }
                                        } else if (v instanceof TextValue tv) {
                                            tv.set(valueObject.get(v.getEnName()).getAsString());
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load config: " + name);
            e.printStackTrace();
            ChatUtils.addChatMessage("Failed to load config: ", name);
            ChatUtils.addChatMessage(e.getMessage());
        }
    }
}
