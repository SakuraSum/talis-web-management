package com.itcast.taliswebmanagement.service.impl;

import com.itcast.taliswebmanagement.exception.BusinessException;
import com.itcast.taliswebmanagement.mapper.DeptMapper;
import com.itcast.taliswebmanagement.mapper.EmpMapper;
import com.itcast.taliswebmanagement.pojo.Dept;
import com.itcast.taliswebmanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServicelmpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;
    /**
     * 查询所有部门
     */
    public List<Dept> list() {
        return deptMapper.list();
    }

    public void deleteById(Integer id) {
        //1. 判断部门下是否有员工， 如果有， 需要提示错误信息
        Integer count = empMapper.countByDeptId(id);
        if(count > 0){
            throw new BusinessException("部门下有员工， 不能删除");
        }

        //2. 删除部门
        deptMapper.deleteById(id);
    }

    public void save(Dept dept){
        //补全基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //保存部门
        deptMapper.insert(dept);
    }

    public Dept getById(Integer id){
        return deptMapper.getById(id);
    }

    public void update(Dept dept){
        //补全基本属性
        dept.setUpdateTime(LocalDateTime.now());
        //保存部门
        deptMapper.update(dept);
    }
}
