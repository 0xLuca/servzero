package net.servzero.helper;

import java.util.Random;

public class RandomHelper {
    private static final Random random = new Random();

    public static int getInt() {
        return random.nextInt();
    }

    public static int getInt(int bound) {
        return random.nextInt(bound);
    }

    public static long getLong() {
        return random.nextLong();
    }
}
