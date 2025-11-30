package com.catclient.duke.module;

import com.catclient.duke.Duke;
import com.catclient.duke.utils.client.SoundUtils;
import com.catclient.duke.utils.wrapper.Wrapper;
import com.catclient.duke.value.Value;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 02:58
 * @Filename：Module
 */
@Getter
@Setter
public class Module implements Wrapper {
    private String name;
    private String cnName;
    private String desc;
    private String cnDesc;
    private Category category;

    private int key;
    private boolean enabled;
    private List<Value> values = new ArrayList<>();
    private String suffix = "";


    public Module(String name, String cnName, String desc, String cnDesc, Category category) {
        this.name = name;
        this.cnName = cnName;
        this.desc = desc;
        this.cnDesc = cnDesc;
        this.category = category;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            SoundUtils.playSound(soundFolder.getAbsolutePath() + "\\enable.wav", 1f);
            Duke.getInstance().getEventManager().register(this);
            onEnable();
        } else {
            Duke.getInstance().getEventManager().unregister(this);
            SoundUtils.playSound(soundFolder.getAbsolutePath() + "\\disable.wav", 1f);
            onDisable();
        }
    }

    public boolean toggle() {
        setEnabled(!enabled);
        return enabled;
    }

    public void onEnable() {}

    public void onDisable() {}

    public void addValue(Value value) {
        values.add(value);
    }

    public String getName() {
        return name;
    }
}
