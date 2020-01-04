package com.qinkuai.homework1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qinkuai.homework1.model.Course;
import com.qinkuai.homework1.model.CourseDetail;
import com.qinkuai.homework1.model.Teacher;

public class CourseDetailViewModel extends ViewModel {
    private MutableLiveData<CourseDetail> courseDetail;
    private MutableLiveData<Teacher> teacher;

    public MutableLiveData<CourseDetail> getCourseDetail() {
        if (courseDetail == null){
            courseDetail = new MutableLiveData<>();
        }
        return courseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail.setValue(courseDetail);
    }

    public MutableLiveData<Teacher> getTeacher() {
        if (teacher == null){
            teacher = new MutableLiveData<>();
        }
        return teacher;
    }

    public void setCourseDetail(Teacher teacher) {
        this.teacher.setValue(teacher);
    }
}
