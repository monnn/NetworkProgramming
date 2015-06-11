package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class StringOut implements Runnable {

    String[] s;
    long sleepTime;

    public StringOut(String[] s, long sleepTime) {
        this.s = s;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        for (int x= 0; x< s.length; x++) {
            System.out.println(s[x]);
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
            }
        }
    }
}
