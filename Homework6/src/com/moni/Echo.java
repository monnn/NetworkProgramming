package com.moni;

import java.util.Scanner;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Echo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String temp = scanner.nextLine();
            if (temp.equals("END.")) {
                return;
            }
            System.out.println(temp);
        }
    }
}
