package com.qinkuai.homework1.model;

public class Teacher{
    //老师教授的课程序号列表

    private String name;
    private String phone;
    private String email;
    private String teacher_desc;
    public Teacher(){
    }

    public Teacher(String name, String phone,String email,String teacher_desc){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.teacher_desc=teacher_desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher_desc() {
        return teacher_desc;
    }

    public void setTeacher_desc(String teacher_desc) {
        this.teacher_desc = teacher_desc;
    }
}
