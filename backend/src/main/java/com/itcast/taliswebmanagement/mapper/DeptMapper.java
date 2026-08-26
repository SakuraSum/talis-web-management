package com.itcast.taliswebmanagement.mapper;

import com.itcast.taliswebmanagement.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     */
    //数据封装规则
    //解决字段名和实体类属性名不一致的造成的部分数据无法封装的问题
    //方法1：手动结果映射
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    //方法2：起别名
    //@Select("select id,name,create_time createTime,update_time updateTime from dept order by update_time desc")
    @Select("select id,name,create_time,update_time from dept order by update_time desc")
    //方法3：开启驼峰命名开关，注意字段名必须是xxx_nnn全小写带下划线的方式，实体名为驼峰命名xxxNnn
    List<Dept> list();

    /**
     * 根据id删除部门
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 查询回显
     */
    @Select("SELECT id,name,create_time,update_time from dept where id=#{id}")
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
