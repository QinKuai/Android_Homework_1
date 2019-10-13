package com.qinkuai.homework1.model;

import android.media.Image;

public class Course {
    //课程ID
    private int id;
    //课程配图
    private int imageId;
    //课程名
    private String courseName;
    //课程简介
    private String courseDesc;
    //课程老师
    private Teacher teacher;

    public Course(){
    }

    public Course(int imageId, String courseName, String courseDesc, Teacher teacher){
        this.imageId = imageId;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.teacher = teacher;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
