package org.github.norbo11.norbznetwork.util;

import java.util.Random;

import org.github.norbo11.norbznetwork.frames.Main;

public class RandomUtil {
    private static Random random = new Random();
    
    //Returns a random number from 0 (inclusive) to end (exclusive)
    public static int from0(int end) {
        return random.nextInt(end);
    }

    public static boolean chance(double percentageChange) {
        double random = RandomUtil.random.nextDouble();
        return percentageChange >= random * 100;
    }

    public static int between(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static void seedRandom(long randomSeed) {
        Main.writeText("Setting seed to " + randomSeed + ".");
        random.setSeed(randomSeed);
    }
}
