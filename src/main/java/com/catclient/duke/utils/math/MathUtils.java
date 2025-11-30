package com.catclient.duke.utils.math;

import java.util.Random;

public class MathUtils {

    public static float getRandomInRange(float min, float max) {
        return min >= max ? min : new Random().nextFloat() * (max - min) + min;
    }

    public static double getRandomInRange(double min, double max) {
        return min >= max ? min : new Random().nextDouble() * (max - min) + min;
    }

    public static int getRandomInRange(int min, int max) {
        return min >= max ? min : new Random().nextInt() * (max - min) + min;
    }

    public static long getRandomInRange(long min, long max) {
        return min >= max ? min : new Random().nextLong() * (max - min) + min;
    }
}
