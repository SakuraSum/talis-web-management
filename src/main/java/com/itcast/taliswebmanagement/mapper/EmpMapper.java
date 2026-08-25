package com.itcast.taliswebmanagement.mapper;

import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    /**
     * 查询所有的员工及其对应的部门名称
     */

    //@Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id")
    //public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 根据查询条件查询员工（包含条件分页查询）
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工数据
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")//设置主键返回 - id值回填 - 获取到生成的主键
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 根据id批量删除员工
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 更新员工基本信息
     * @param emp
     */
    void updateById(Emp emp);

    /**
     * 统计各个职位的员工人数
     */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();

    /**
     * 统计各个性别的员工人数
     */
    @MapKey("name")
    List<Map<String,Object>> countEmpGenderData();

    /**
     * 查询所有员工
     */
    List<Emp> findAllEmp();

    /**
     * 根据部门id查询员工人数
     */
    @Select("select count(*) from emp where dept_id = #{deptId}")
    Integer countByDeptId(Integer deptId);

    /**
     * 员工登录
     */
    @Select("select id,username,name from emp where username = #{username} and password = #{password}")
    Emp getUsernameAndPassword(Emp emp);
}