package com.catclient.duke.utils.player;

import com.catclient.duke.Duke;
import com.catclient.duke.utils.wrapper.Wrapper;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;

public class ChatUtils implements Wrapper {
    private static final String PREFIX = "§7[§b" + Duke.CLIENT_NAME.charAt(0) + "§7] ";

    public static void component(Component component) {
        ChatComponent chat = mc.gui.getChat();
        chat.addMessage(component);
    }

    public static void addChatMessage(String message) {
        addChatMessage(true, message);
    }

    public static void addChatMessage(String... message) {
        StringBuilder sb = new StringBuilder();
        for (String s : message) {
            sb.append(s);
        }
        addChatMessage(true, sb.toString());
    }

    public static void addChatMessage(boolean prefix, String... message) {
        StringBuilder sb = new StringBuilder();
        for (String s : message) {
            sb.append(s);
        }
        component(Component.nullToEmpty((prefix ? PREFIX : "") + sb));
    }
}
