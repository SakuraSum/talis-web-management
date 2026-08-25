package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.Clazz;
import com.itcast.taliswebmanagement.pojo.ClazzQueryParam;
import com.itcast.taliswebmanagement.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /**
     * 班级列表数据的条件分页查询
     */
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    void saveClazz(Clazz clazz);

    /**
     * 根据ID查询班级
     */
    Clazz getClazzById(Integer id);

    /**
     * 修改班级
     */
    void updateClazz(Clazz clazz);

    /**
     * 删除班级
     */
    void deleteClazz(Integer id);

    /**
     * 查询所有班级
     */
    List<Clazz> list();
}
