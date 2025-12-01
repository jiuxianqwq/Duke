package com.catclient.duke.module;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 03:01
 * @Filename：Category
 */
public enum Category {
    COMBAT("Combat", "战斗", "\uf889"),
    PLAYER("Player", "玩家", "\uea1d"),
    MOVEMENT("Movement", "移动", "\ue566"),
    RENDER("Visual", "视觉", "\ue8f4"),
    MISC("Misc", "杂项", "\ue8b8");

    private final String name;
    private final String cnName;
    private final String icon;

    Category(String name, String cnName, String icon) {
        this.name = name;
        this.cnName = cnName;
        this.icon = icon;
    }
}
