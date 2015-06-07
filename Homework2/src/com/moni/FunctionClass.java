package com.moni;

import java.util.*;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class FunctionClass {

    public static void main(String[] args) {
        Student nameArray[] = {
                new Student("Ivan",345, 4.76),
                new Student("Ani",443, 4.34),
                new Student("Gosho",2135, 3.45),
                new Student("Maria",543, 4.76)
        };
        List<Student> names = Arrays.asList(nameArray);
        Collections.sort(names);
        System.out.println(names);
    }
}
