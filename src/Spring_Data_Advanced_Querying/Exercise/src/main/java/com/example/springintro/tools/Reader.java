package com.example.springintro.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
    private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static String readString() throws IOException {
        return bf.readLine();
    }

    public static int readInt() throws IOException {
        return Integer.parseInt(bf.readLine());
    }

    public static double readDouble() throws IOException {
        return Double.parseDouble(bf.readLine());
    }

    public static long readLong() throws IOException {
        return Long.parseLong(bf.readLine());
    }

    public static String[] readStringArray(String delimiter) throws IOException {
        return bf.readLine().split(delimiter);
    }
}
