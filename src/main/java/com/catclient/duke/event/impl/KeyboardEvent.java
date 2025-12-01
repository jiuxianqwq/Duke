package com.catclient.duke.event.impl;

import com.catclient.duke.event.api.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 23:20
 * @Filename：KeyboardEvent
 */
@Getter
@Setter
public class KeyboardEvent implements Event {
    private long window;
    private int key;
    private int scancode;
    private int action;
    private int mods;
    private boolean bind;

    public KeyboardEvent(long window, int key, int scancode, int action, int mods) {
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }
}
