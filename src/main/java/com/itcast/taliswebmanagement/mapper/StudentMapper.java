package com.itcast.taliswebmanagement.mapper;

import com.itcast.taliswebmanagement.pojo.Student;
import com.itcast.taliswebmanagement.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    /**
     * 分页查询学生信息
     */
    List<Student> page(StudentQueryParam studentQueryParam);

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
    void setViolation(Student student);

    /**
     * 删除学员信息
     */
    void deleteStudentById(List<Integer> ids);

    /**
     * 班级人数统计
     */
    /**
     * 统计班级人数
     */
    @Select("select c.name cname , count(s.id) scount from clazz c  left join student s on s.clazz_id = c.id group by c.name order by count(s.id) desc ")
    List<Map<String,Object>> getStudentCount();

    /**
     * 统计学员学历
     */
    @MapKey("name")
    List<Map> countStudentDegreeData();
}
