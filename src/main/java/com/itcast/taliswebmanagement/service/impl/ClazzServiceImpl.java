package com.itcast.taliswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.taliswebmanagement.mapper.ClazzMapper;
import com.itcast.taliswebmanagement.pojo.Clazz;
import com.itcast.taliswebmanagement.pojo.ClazzQueryParam;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;


    /**
     * 班级列表数据的条件分页查询
     */
    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        //1. 设置PageHelper分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        //2. 执行查询
        List<Clazz> empList = clazzMapper.page(clazzQueryParam);
        //3. 封装分页结果
        Page<Clazz> p = (Page<Clazz>)empList;
        return new PageResult<Clazz>(p.getTotal(), p.getResult());
    }

    /**
     * 添加班级
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveClazz(Clazz clazz) {
        // 补全基础属性
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());

        // 保存班级信息
        clazzMapper.insert(clazz);
    }

    /**
     * 根据ID查询班级
     */
    @Override
    public Clazz getClazzById(Integer id) {
        return clazzMapper.getClazzById(id);
    }

    /**
     * 修改班级
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateClazz(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateClazz(clazz);
    }

    /**
     * 删除班级
     */
    @Override
    public void deleteClazz(Integer id) {
        clazzMapper.deleteClazzById(id);
    }

    /**
     * 查询所有班级
     */
    @Override
    public List<Clazz> list() {
        return clazzMapper.list();
    }
}
