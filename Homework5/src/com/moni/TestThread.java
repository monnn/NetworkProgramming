package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class TestThread {

    public static void main(String[] args) {
        IntThread one = new IntThread(1);
        IntThread two = new IntThread(2);
        IntThread three = new IntThread(3);
        one.start();
        two.start();
        three.start();
    }
}
