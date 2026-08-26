package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.anno.LogOperation;
import com.itcast.taliswebmanagement.pojo.Dept;
import com.itcast.taliswebmanagement.pojo.Result;
import com.itcast.taliswebmanagement.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@Slf4j
@RestController
public class DeptController {
    // lombok中提供的@Slf4j注解，可以简化定义日志记录器这步操作。
    // 添加了该注解，就相当于在类中定义了日志记录器，就下面这句代码：

    /**
     * 主要用于实现依赖注入（Dependency Injection, DI），
     * 它能够自动将需要的依赖对象注入到目标类中，从而减少手动配置的复杂性
     */
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     */
    //指定请求方式的方法：
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)//方法一：通过 method 指定get请求方式方式
    @GetMapping("/depts")//方法二：@XXXMapping 简化方式
    public Result findAll(){
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    /**
     * 根据id删除部门 - delete http://localhost:8080/depts?id=1
     */
    @LogOperation
    @DeleteMapping("/depts")
    public Result delete(Integer id){
        log.info("根据id删除部门, id: {}" , id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门 - POST http://localhost:8080/depts
     * 请求参数：{"name":"研发部"}
     * JSON数据的键名与方法形参对象的属性名相同，并需要使用@RequestBody注解标识
     */
    @LogOperation
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept){
        //System.out.println("新增部门，dept="+dept);
        log.info("新增部门, dept: {}" , dept);
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 查询回显
     * 根据ID查询 - GET http://localhost:8080/depts/1
     */
    @GetMapping("/depts/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询, id: {}" , id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门 - PUT
     * http://localhost:8080/depts  请求参数：{"id":1,"name":"研发部"}
     */
    @LogOperation
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        log.info("修改部门, dept: {}" , dept);
        deptService.update(dept);
        return Result.success();
    }
}
