package com.itcast.taliswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.taliswebmanagement.mapper.EmpExprMapper;
import com.itcast.taliswebmanagement.mapper.EmpMapper;
import com.itcast.taliswebmanagement.pojo.*;
import com.itcast.taliswebmanagement.service.EmpLogService;
import com.itcast.taliswebmanagement.service.EmpService;
import com.itcast.taliswebmanagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;


    /**
     * 分页查询员工（包含条件分页查询）
     * @param empQueryParam
     * @return
     */
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1. 设置PageHelper分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        //2. 执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
        //3. 封装分页结果
        Page<Emp> p = (Page<Emp>)empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    /**
     * 保存员工信息
     * @param emp
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
            //1.补全基础属性
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            //2.保存员工基本信息
            empMapper.insert(emp);

            //int i = 1/0;//程序会发生Exception异常

            //3. 保存员工的工作经历信息 - 批量
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(empExpr -> empExpr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogService.insertLog(empLog);
        }
    }

    /**
     * 删除员工
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteByIds(List<Integer> ids) {
        //根据id批量删除员工基本信息
        empMapper.deleteByIds(ids);
        //根据id批量删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    /**
     * 更新员工信息
     * @param emp
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        //1. 根据ID更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2. 根据员工ID删除员工的工作经历信息 【删除老的】
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        //3. 新增员工的工作经历数据 【新增新的】
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询所有员工
     */
    @Override
    public List<Emp> findAllEmp() {
        return empMapper.findAllEmp();
    }

    /**
     * 员工登录
     * @param emp
     * @return
     */
    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin = empMapper.getUsernameAndPassword(emp);
        if(empLogin != null){
            //生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",empLogin.getId());
            claims.put("username",empLogin.getUsername());

            String jwt = JwtUtils.generateToken(claims);
            LoginInfo loginInfo = new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), jwt);
            return loginInfo;
        }
        return null;
    }

}