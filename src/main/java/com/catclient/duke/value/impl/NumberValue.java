package com.catclient.duke.value.impl;

import com.catclient.duke.module.Module;
import com.catclient.duke.utils.math.MathUtils;
import com.catclient.duke.value.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 21:50
 * @Filename：NumberValue
 */
@Getter
@Setter
public class NumberValue extends Value {
    private float value;
    private float min;
    private float max;
    private boolean range;

    public NumberValue(String name, String cnName, float min, float max, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.value = value;
        this.min = min;
        this.max = max;
        range = true;
    }

    public NumberValue(String name, String cnName, float min, float max, Module parent) {
        super(name, cnName, parent, () -> true);
        this.value = value;
        this.min = min;
        this.max = max;
        range = true;
    }

    public NumberValue(String name, String cnName, float value, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.value = value;
        this.min = min;
        this.max = max;
        range = false;
    }

    public NumberValue(String name, String cnName, float value, Module parent) {
        super(name, cnName, parent, () -> true);
        this.value = value;
        this.min = min;
        this.max = max;
        range = false;
    }

    public float get() {
        if (range) return MathUtils.getRandomInRange(min, max); else return value;
    }

    public float getRandom() {
        return MathUtils.getRandomInRange(min, max);
    }
}
