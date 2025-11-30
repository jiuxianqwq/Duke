package com.catclient.duke.value.impl;

import com.catclient.duke.module.Module;
import com.catclient.duke.value.Value;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 22:08
 * @Filename：TextValue
 */
public class TextValue extends Value {
    private String value;

    public TextValue(String name, String cnName, String value, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.value = value;
    }

    public TextValue(String name, String cnName, String value, Module parent) {
        super(name, cnName, parent, () -> true);
        this.value = value;
    }

    public String get() {
        return value;
    }

    public void set(String value) {
        this.value = value;
    }
}
