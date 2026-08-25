package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.anno.LogOperation;
import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.EmpQueryParam;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.pojo.Result;
import com.itcast.taliswebmanagement.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 分页查询员工（包含条件分页查询）
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("查询请求参数： {}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加员工
     */
    @LogOperation
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("请求参数emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 删除员工
     */
    @LogOperation
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("批量删除员工:ids={}",ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 查询回显
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工信息");
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 更新员工信息
     */
    @LogOperation
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息，emp={}", emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * 查询所有的员工
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有员工");
        List<Emp> empList = empService.findAllEmp();
        return Result.success(empList);
    }
}