package com.famlink.frame.view.activity;

import android.view.View;

/**
 * Created by qibin on 2015/8/11.
 */
public class Student {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void onMyClick(View v){
        System.out.println("sadasdasdasd"+v + "index;");
    }
}
