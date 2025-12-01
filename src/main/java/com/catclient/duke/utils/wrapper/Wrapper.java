package com.catclient.duke.utils.wrapper;

import com.catclient.duke.Duke;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.io.File;

public interface Wrapper {
    //请勿使用Minecraft.getInstance(), 妖猫给Minecraft.getInstance()写了stack检测
    Minecraft mc = Duke.getMinecraft();
    Player player = mc.player;
    Level level = mc.level;
    MultiPlayerGameMode gameMode = mc.gameMode;
    Options options = mc.options;
    HitResult hitResult = mc.hitResult;
    ClientPacketListener connection = mc.getConnection();

    File resourcesFolder = new File(Duke.CLIENT_FOLDER, "\\resources");
    File configFolder = new File(Duke.CLIENT_FOLDER, "\\config");
    File mappingFolder = new File(Duke.CLIENT_FOLDER, "\\resources\\mapping\\");
    File soundFolder = new File(Duke.CLIENT_FOLDER, "\\resources\\sounds\\");
    File nativeFolder = new File(Duke.CLIENT_FOLDER, "\\resources\\natives\\");
    File libFolder = new File(Duke.CLIENT_FOLDER, "\\resources\\libs\\");
    File fontFolder = new File(Duke.CLIENT_FOLDER, "\\resources\\fonts\\");

//    URLClassLoader libCL = LibraryUtils.loadLibrary();
}
