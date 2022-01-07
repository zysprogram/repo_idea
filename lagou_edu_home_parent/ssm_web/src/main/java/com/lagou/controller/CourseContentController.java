package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {
    @Autowired
    private CourseContentService courseContentService;
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam Integer courseId){
        List<CourseSection> sectionList = courseContentService.findSectionAndLessonByCourseId(courseId);
        ResponseResult result = new ResponseResult(true,200,"响应成功",sectionList);
        return result;
    }

    /*
  回显章节对应的课程信息
   */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam int courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult result = new ResponseResult(true,200,"响应成功",course);
        return result;
    }

    /*
     保存章节
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section) {
        if(section.getId() == null) {
            courseContentService.saveSection(section);
            return new ResponseResult(true, 200, "新增响应成功", null);
        }else{
            courseContentService.updateSection(section);
            return new ResponseResult(true, 200, "修改响应成功", null);
        }
    }

    /*
     修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id,@RequestParam int status){
        courseContentService.updateSectionStatus(id,status);
        //封装最新的状态信息
        Map<String,Integer> map = new HashMap<>();
        map.put("status",status);
        ResponseResult result = new ResponseResult(true,200,"响应成功",map);
        return result;
    }

    /*
    保存&修改课时
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson lesson){
        if(lesson.getId() == null){
            courseContentService.saveLesson(lesson);
            return new ResponseResult(true,200,"新增响应成功",null);
        }else{
            courseContentService.updateLesson(lesson);
            return new ResponseResult(true,200,"修改响应成功",null);
        }
    }
}
