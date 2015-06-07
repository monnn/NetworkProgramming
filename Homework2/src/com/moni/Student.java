package com.moni;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Student implements Comparable<Student> {
    
    private int fn;
    private String name;
    private double grade;

    public Student(String name, int fn, double grade) {
        this.fn = fn;
        this.name = name;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Student s) {
        int cmp = 0;
        if (grade > s.grade) {
            cmp = -1;
        } else if (grade < s.grade) {
            cmp = 1;
        } else {
            name.compareTo(s.name);
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name=" + name +
                ", fn='" + fn + '\'' +
                ", grade=" + grade +
                '}';
    }

}
