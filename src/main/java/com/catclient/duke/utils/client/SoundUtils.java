package com.catclient.duke.utils.client;

import com.catclient.duke.Duke;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 03:18
 * @Filename：SoundUtils
 */
public class SoundUtils {

    public static void playSound(String path, float volume) {
        if (!Duke.isCanPlaySound()) return;
        Multithreading.runAsync((() -> {
            try {
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedInputStream);

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);

                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }));
    }
}
