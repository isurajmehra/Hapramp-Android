package com.hapramp.steem;

import android.util.Log;

import com.hapramp.preferences.HaprampPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Ankit on 2/21/2018.
 */

public class PermlinkGenerator {

    private static final int MAX_LENGTH = 64;
    private final static String ALPHA_NUMERIC_STRING = "q0w1e2r3t4y5u6i7o8p9l0kjhgfdsazxcvbnm";
    public static String getPermlink() {

        StringBuilder builder = new StringBuilder()
                .append(random())
                .append(getCurrentTimeStamp());
        return builder.toString();

    }

    private static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            return dateFormat.format(new Date()); // Find todays date

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private static String random() {

        int count = MAX_LENGTH;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
