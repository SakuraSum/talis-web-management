package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.EmpQueryParam;
import com.itcast.taliswebmanagement.pojo.LoginInfo;
import com.itcast.taliswebmanagement.pojo.PageResult;

import java.util.List;

public interface EmpService {
    //PageResult page(Integer page, Integer pageSize);
    //PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 分页查询员工数据（包含条件分页查询）
     * @param empQueryParam
     * @return
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 删除员工
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询回显
     */
    Emp getInfo(Integer id);

    /**
     * 更新员工信息
     */
    void update(Emp emp);

    /**
     * 查询所有员工
     */
    List<Emp> findAllEmp();

    /**
     * 登录
     */
    LoginInfo login(Emp emp);
}