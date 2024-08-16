package com.breelock.autoclicky;

import net.minecraft.util.Util;

import java.net.URI;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static int randint(int min, int max) {
        if (min == max)
            return min;

        if (min > max) {
            min = min + max;
            max = min - max;
            min = min - max;
        }

        return random.nextInt(max - min + 1) + min;
    }

    public static void openLink(String url) {
        try {
            URI uri = new URI(url);
            Util.getOperatingSystem().open(uri);
        } catch (Exception e) {
            AutoClicky.LOGGER.error(String.format("AutoClicky mod failed to open website [%s]!", url), e);
        }
    }
}
