package com.catclient.duke;

import com.catclient.duke.asm.api.TransformerManager;
import com.catclient.duke.event.EventManager;
import com.catclient.duke.module.ModuleManager;
import com.catclient.duke.utils.client.LibraryUtils;
import com.catclient.duke.utils.client.SoundUtils;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.lang.reflect.Field;

@Getter
public class Duke {

    public static final String ModID = "duke";
    public static final String CLIENT_NAME = "Duke";
    public static final String CLIENT_VERSION = "1.0";
    public static final File CLIENT_FOLDER = new File("C:\\Duke\\");
    @Getter
    private static Duke instance;
    @Getter
    private static boolean canPlaySound = false;
    private EventManager eventManager;
    private ModuleManager moduleManager;

    public Duke() {
        try {
            instance = this;
            init(Class.forName("net.minecraft.client.Minecraft"));
            canPlaySound = true;
            SoundUtils.playSound(CLIENT_FOLDER.getAbsolutePath() + "\\resources\\sounds\\opening.wav", 1f);
        } catch (Exception e) {
            System.out.println("[Duke] Failed to load Duke");
            e.printStackTrace();
        }
    }

    public static File b(String s) {
        System.out.println("[Duke] init client");
        System.setProperty("java.awt.headless", "false");
        new Duke();
        return null;
    }

    public Minecraft init(Class<?> clazz) throws Exception {
        LibraryUtils.loadNatives();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();

        TransformerManager.init();
        return null;
    }

    public static Minecraft getMinecraft() {
        Minecraft minecraft = null;
        try {
            Class<?> classMinecraft = Class.forName("net.minecraft.client.Minecraft");
            for (Field field : classMinecraft.getDeclaredFields()) {
                if (field.getType() == classMinecraft) {
                    field.setAccessible(true);
                    minecraft = (Minecraft) field.get(null);
                    field.setAccessible(false);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return minecraft;
    }
}
