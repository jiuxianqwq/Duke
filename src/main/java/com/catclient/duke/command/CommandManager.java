package com.catclient.duke.command;

import com.catclient.duke.Duke;
import com.catclient.duke.command.impl.Bind;
import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.event.impl.ChatEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 04:38
 * @Filename：CommandManager
 */
@Getter
@Setter
public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    public CommandManager() {
        init();
        Duke.getInstance().getEventManager().register(this);
    }

    public void init() {
        addCommand(
                new Bind()
        );
    }

    private void addCommand(Command... command) {
        for (Command cmd : command) {
            commands.add(cmd);
            Duke.getInstance().getEventManager().register(cmd);
        }
    }

    @EventTarget
    public void onChat(ChatEvent event) {
        String msg = event.getMsg();
        if (!msg.startsWith(".")) return;
        String[] args = msg.split(" ");
        for (Command cmd : commands) {
            if (cmd.getCommand().equalsIgnoreCase(args[0].replace(".", ""))) {
                cmd.onCommand(args);
                event.setCancelled(true);
                return;
            }
            for (String alias : cmd.getAlias()) {
                if (alias.equalsIgnoreCase(args[0].replace(".", ""))) {
                    cmd.onCommand(args);
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
