package com.catclient.duke.utils.client;

import com.catclient.duke.utils.wrapper.Wrapper;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 01:03
 * @Filename：LibraryUtils
 */
public class LibraryUtils implements Wrapper {
    public static void loadNatives() {
        try {
            File[] files = nativeFolder.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".dll")) {
                    System.load(file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading native libraries.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static URLClassLoader loadLibrary() {
        try {
            File[] files = libFolder.listFiles();
            List<URL> urls = new ArrayList<>();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    urls.add(file.toURI().toURL());
                }
            }
            URL[] urlArray = urls.toArray(new URL[0]);
            return new URLClassLoader(urlArray, LibraryUtils.class.getClassLoader());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading libraries.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }
}
