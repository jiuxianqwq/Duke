package com.catclient.duke.utils.client;

import com.catclient.duke.Duke;
import com.catclient.duke.utils.wrapper.Wrapper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LogUtils implements Wrapper {
    public static void throwableBug(String targetName, Throwable e) {
         try {
             // 确保文件路径存在
             File errorFile = new File(Duke.CLIENT_FOLDER, String.valueOf(System.currentTimeMillis()));
             Files.write(errorFile.toPath(), (e.getMessage() != null ? e.getMessage() : "无错误消息|" + targetName + "|" + e).getBytes(StandardCharsets.UTF_8));
         } catch (Throwable ignored) {}
    }
}