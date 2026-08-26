package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.anno.LogOperation;
import com.itcast.taliswebmanagement.pojo.*;
import com.itcast.taliswebmanagement.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    /**
     * 班级列表数据的条件分页查询
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("查询请求参数： {}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加班级
     */
    // @RequestBody： 表示将请求体中的json数据转换为java对象
    @LogOperation
    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("添加班级请求参数： {}", clazz);
        clazzService.saveClazz(clazz);
        return Result.success();
    }

    /**
     * 根据ID查询班
     */
    @GetMapping("/{id}")
    public Result getClazzById(@PathVariable Integer id) {
        log.info("根据id查询班级，id: {}", id);
        Clazz clazz = clazzService.getClazzById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级
     */
    @LogOperation
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级信息,{}",clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }

    /**
     * 删除班级
     */
    @LogOperation
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除班级,id:{}",id);
        clazzService.deleteClazz(id);
        return Result.success();
    }

    /**
     * 查询所有班级
     */
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有班级");
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);
    }


}
