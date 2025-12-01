package com.catclient.duke.event.impl;

import com.catclient.duke.event.api.Cancellable;
import com.catclient.duke.event.api.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/2 03:24
 * @Filename：ChatEvent
 */
@Getter
@Setter
public class ChatEvent implements Event, Cancellable {
    private boolean cancelled;
    private String msg;

    public ChatEvent(String msg) {
        this.msg = msg;
    }
}
