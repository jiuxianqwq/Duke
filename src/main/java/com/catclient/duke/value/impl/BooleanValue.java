package com.catclient.duke.value.impl;

import com.catclient.duke.module.Module;
import com.catclient.duke.value.Value;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 04:43
 * @Filename：BooleanValue
 */
public class BooleanValue extends Value {
    private Boolean value;

    public BooleanValue(String name, String cnName, boolean defaultValue, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.value = defaultValue;
    }

    public BooleanValue(String name, String cnName, boolean defaultValue, Module parent) {
        super(name, cnName, parent, () -> true);
        this.value = defaultValue;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean toggle() {
        set(!value);
        return value;
    }

}
