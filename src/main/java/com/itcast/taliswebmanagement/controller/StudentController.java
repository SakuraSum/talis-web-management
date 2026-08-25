package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.anno.LogOperation;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.pojo.Result;
import com.itcast.taliswebmanagement.pojo.Student;
import com.itcast.taliswebmanagement.pojo.StudentQueryParam;
import com.itcast.taliswebmanagement.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生信息
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学员信息： {}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加学员
     */
    // @RequestBody： 表示将请求体中的json数据转换为java对象
    @LogOperation
    @PostMapping
    public Result saveStudent(@RequestBody Student student) {
        log.info("添加学员信息： {}", student);

        studentService.saveStudent(student);
        return Result.success();
    }

    /**
     * 根据主键ID查询学员
     */
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        log.info("根据ID查询学员信息： {}", id);
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    /**
     * 修改学员信息
     */
    @LogOperation
    @PutMapping
    public Result updateStudent(@RequestBody Student student) {
        log.info("修改学员信息： {}", student);
        studentService.updateStudent(student);
        return Result.success();
    }

    /**
     * 记录学生违纪处理
     */
    //@PathVariable 的作用：将 URL 模板变量绑定到方法的参数上
    //通俗地说，把网址里的数字抠出来，直接变成方法的参数
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable String score) {
        if("null".equals(score)|| score==null){
            score="0";//处理默认值为空的情况
        }
        Short scoreNum =  Short.parseShort(score);
        log.info("记录学生违纪处理： id={}, score={}", id, scoreNum);
        studentService.violation(id, scoreNum);
        return Result.success();
    }

    /**
     * 删除学员信息
     */
    //@PathVariable 的作用：将 URL 模板变量绑定到方法的参数上
    //通俗地说，把网址里的数字抠出来，直接变成方法的参数
    @LogOperation
    @DeleteMapping("/{ids}")
    public Result deleteStudentByIds(@PathVariable List<Integer> ids) {
        //用@RequestParam注解接收不行，
        // 因为之前员工批量删除的时候老师是用的(?ids=...)的格式，这个就可以用@RequestParam注解，
        // 但是这次学员删除用的是路径参数接收的所以要指定路径@DeleteMapping("/{ids}")，
        // 而且还要用@PathVariable注解接收参数。
        log.info("批量删除学员信息：ids= {}", ids);
        studentService.deleteStudentById(ids);
        return Result.success();
    }
}
