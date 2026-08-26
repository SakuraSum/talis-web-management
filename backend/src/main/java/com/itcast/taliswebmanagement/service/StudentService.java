package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.pojo.Student;
import com.itcast.taliswebmanagement.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    /**
     * 分页查询学生信息
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 添加学员
     */
    void saveStudent(Student student);

    /**
     * 根据主键ID查询学员
     */
    Student getStudentById(Integer id);

    /**
     * 修改学员信息
     */
    void updateStudent(Student student);

    /**
     * 记录学生违纪处理
     */
    void violation(Integer id,Short score);

    /**
     * 删除学员信息
     */
    void deleteStudentById(List<Integer> ids);
}
