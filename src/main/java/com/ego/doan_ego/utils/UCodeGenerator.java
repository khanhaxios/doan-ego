package com.ego.doan_ego.utils;

import java.util.Random;

public class UCodeGenerator {
    public static final int STANDARD_LENGTH = 12;
    private static final char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random rad = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(chars[rad.nextInt(0, length)]);
        }
        return stringBuilder.toString();
    }

    public static String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        Random rad = new Random();
        for (int i = 0; i < STANDARD_LENGTH; i++) {
            stringBuilder.append(chars[rad.nextInt(0, STANDARD_LENGTH)]);
        }
        return stringBuilder.toString();
    }
}
