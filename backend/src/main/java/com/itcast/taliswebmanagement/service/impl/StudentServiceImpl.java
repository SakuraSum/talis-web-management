package com.itcast.taliswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.taliswebmanagement.mapper.StudentMapper;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.pojo.Student;
import com.itcast.taliswebmanagement.pojo.StudentQueryParam;
import com.itcast.taliswebmanagement.service.StudentService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询学生信息
     */
    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        //1. 设置PageHelper分页参数
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        //2. 执行查询
        List<Student> studentList = studentMapper.page(studentQueryParam);
        //3. 封装分页结果
        Page<Student> p = (Page<Student>)studentList;
        return new PageResult<Student>(p.getTotal(), p.getResult());
    }

    /**
     * 添加学员
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveStudent(Student student) {
        //设置创建时间
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());

        //保存学员信息
        studentMapper.saveStudent(student);
    }

    /**
     * 根据主键ID查询学员
     */
    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.getStudentById(id);
    }

    /**
     * 修改学员信息
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateStudent(student);
    }

    /**
     * 记录学生违纪处理
     * @param id
     * @param score
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void violation(Integer id, Short score) {
        Student student = studentMapper.getStudentById(id);
        student.setUpdateTime(LocalDateTime.now());
        student.setViolationCount((short) (student.getViolationCount() + 1));
        student.setViolationScore((short) (student.getViolationScore() + score));
        studentMapper.setViolation(student);
    }

    /**
     * 删除学员信息
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteStudentById(List<Integer> ids) {
        studentMapper.deleteStudentById(ids);
    }
}
