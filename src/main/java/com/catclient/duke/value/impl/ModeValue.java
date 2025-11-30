package com.catclient.duke.value.impl;

import com.catclient.duke.module.Module;
import com.catclient.duke.value.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 22:05
 * @Filename：ModeValue
 */
@Getter
@Setter
public class ModeValue extends Value {
    private String mode;
    private String[] modes;

    public ModeValue(String name, String cnName, String mode, String[] modes, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.mode = mode;
        this.modes = modes;
    }

    public ModeValue(String name, String cnName, String mode, String[] modes, Module parent) {
        super(name, cnName, parent, () -> true);
        this.mode = mode;
        this.modes = modes;
    }

    public String get() {
        return mode;
    }

    public void set(String mode) {
        this.mode = mode;
    }

    public boolean isMode(String mode) {
        return mode.equals(this.mode);
    }
}
