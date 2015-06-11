package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class TestThread2 {

    public static void main(String[] args) {
        IntThread one = new IntThread(1);
        IntThread two = new IntThread(2);
        IntThread three = new IntThread(3);
        one.setPriority(Thread.MIN_PRIORITY);
        two.setPriority(Thread.MIN_PRIORITY + 1);
        three.setPriority(Thread.MIN_PRIORITY + 2);
        one.start();
        two.start();
        three.start();
    }
}
