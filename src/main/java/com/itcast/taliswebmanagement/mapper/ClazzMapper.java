package com.itcast.taliswebmanagement.mapper;

import com.itcast.taliswebmanagement.pojo.Clazz;
import com.itcast.taliswebmanagement.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {
    /**
     * 查询所有班级
     */
    List<Clazz> list();

    /**
     * 班级列表数据的条件分页查询
     */
    List<Clazz> page(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    void insert(Clazz clazz);

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
    void deleteClazzById(Integer id);

}
