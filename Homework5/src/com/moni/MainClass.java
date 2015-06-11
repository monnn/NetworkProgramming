package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class MainClass {

    String[] first = {"one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten"};
    String[] second = {"blue", "red", "grey", "green",
            "yellow", "orange", "pink", "black", "white"};

    public static void main(String[] args) {
        MainClass mc = new MainClass();
        Thread s1 = new Thread(new StringOut(mc.first, 500L));
        Thread s2 = new Thread(new StringOut(mc.second, 1000L));
        s1.start();
        s2.start();
    }
}
