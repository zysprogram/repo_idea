package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    /**
     * 多条件课程列表查询
     */
    public List<Course> findCourseByCondition(CourseVO courseVO);

    /*
    添加课程及讲师信息(CourseVO获取的属性值分别封装Course和Teacher)
     */
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据id获取课程信息
     * */
    public CourseVO findCourseById(int id);

    /**
     * 修改课程信息
     * */
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 修改课程状态
     * */
    public void updateCourseStatus(int id,int status);
}
