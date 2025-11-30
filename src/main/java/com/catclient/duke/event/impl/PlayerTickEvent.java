package com.catclient.duke.event.impl;

import com.catclient.duke.event.api.Cancellable;
import com.catclient.duke.event.api.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 03:22
 * @Filename：PlayerTickEvent
 */
@Getter
@Setter
public class PlayerTickEvent implements Cancellable, Event {
    boolean cancelled = false;
}
