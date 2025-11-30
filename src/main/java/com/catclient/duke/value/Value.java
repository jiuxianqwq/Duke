package com.catclient.duke.value;

import com.catclient.duke.module.Module;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 04:35
 * @Filename：Value
 */
@Getter
@Setter
public class Value {
    private String name;
    private String cnName;
    private boolean visible;

    public Value(String name, String cnName, Module parent, Supplier<Boolean> visible) {
        this.name = name;
        this.cnName = cnName;
        this.visible = visible.get();
        if (parent != null) parent.addValue(this);
    }
}
