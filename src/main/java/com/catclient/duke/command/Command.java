package com.catclient.duke.command;

import com.catclient.duke.utils.wrapper.Wrapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 04:35
 * @Filename：Command
 */
@Getter
@Setter
public class Command implements Wrapper {
    private String command;
    private String[] alias;

    public Command(String command, String... alias) {
        this.command = command;
        this.alias = alias;
    }

    public void onCommand(String[] args) {

    }
}
