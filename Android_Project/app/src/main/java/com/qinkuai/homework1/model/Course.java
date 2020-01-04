package com.qinkuai.homework1.model;

import android.graphics.Bitmap;

public class Course {
    //定义三种常量  表示三种条目类型
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    public static final int TYPE_THREE = 2;


    public int type;
    //课程ID
    private String id;
    //课程配图
    private Bitmap imageId;
    //课程名
    private String courseName;
    //课程简介
    private String courseDesc;
    //课程老师
    private Teacher teacher;

    private String Coursetime;

    public Course(){
    }

    public Course(String id, Bitmap imageId, String courseName, String courseDesc, Teacher teacher, int type, String Coursetime){
          this.id=id;
        this.imageId = imageId;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.teacher = teacher;
        this.type=type;
        this.Coursetime=Coursetime;
    }
    public void setCoursetime(String coursetime){this.Coursetime=Coursetime;}
    public String getCoursetime()
    {
        return Coursetime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setImageBitmap(Bitmap imageId) {
        this.imageId = imageId;
    }

    public Bitmap getImageBitmap() {
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
