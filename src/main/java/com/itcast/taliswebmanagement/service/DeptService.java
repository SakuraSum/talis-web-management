package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     */
    List<Dept> list();

    /**
     * 根据id删除部门
     */
    void deleteById(Integer id);

    /**
     * 增加部门
     */
    void save(Dept dept);

    /**
     * 查询回显
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    void update(Dept dept);
}
