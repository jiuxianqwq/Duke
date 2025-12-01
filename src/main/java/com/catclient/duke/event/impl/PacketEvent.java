package com.catclient.duke.event.impl;

import com.catclient.duke.event.api.Cancellable;
import com.catclient.duke.event.api.Event;
import com.catclient.duke.event.api.EventType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.protocol.Packet;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 19:32
 * @Filename：PacketEvent
 */
@Getter
@Setter
public class PacketEvent implements Event, Cancellable {
    private boolean cancelled;
    private EventType type;
    private Packet<?> packet;

    public PacketEvent(EventType type, Packet<?> packet) {
        this.type = type;
        this.packet = packet;
    }
}
