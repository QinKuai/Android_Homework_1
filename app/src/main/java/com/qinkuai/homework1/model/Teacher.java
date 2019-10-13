package com.qinkuai.homework1.model;

import java.util.ArrayList;

public class Teacher extends User {
    //老师教授的课程序号列表
    private ArrayList<Long> courses = new ArrayList<>();

    public Teacher(){
    }

    public Teacher(String name, String passwd){
        this.username = name;
        this.password = passwd;
    }

    public ArrayList<Long> getCourses() {
        return courses;
    }
}
