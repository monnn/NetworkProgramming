package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class IntThread extends Thread {

    int argument;

    public IntThread(int argument) {
        this.argument = argument;
    }

    public void run() {
        for (int i = 0; i < 15 ; i++) {
            System.out.println(argument);
        }
    }
}
