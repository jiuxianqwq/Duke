package com.catclient.duke.event.impl;

import com.catclient.duke.event.api.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 02:02
 * @Filename：SprintEvent
 */
@Getter
@Setter
public class SprintEvent implements Event {
    private boolean sprint;
    public SprintEvent(boolean state) {
        this.sprint = state;
    }
}
