package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.exception.BusinessException;
import com.itcast.taliswebmanagement.mapper.DeptMapper;
import com.itcast.taliswebmanagement.mapper.EmpMapper;
import com.itcast.taliswebmanagement.pojo.Dept;
import com.itcast.taliswebmanagement.service.impl.DeptServicelmpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeptServiceTest {

    @InjectMocks
    private DeptServicelmpl deptService;

    @Mock
    private DeptMapper deptMapper;

    @Mock
    private EmpMapper empMapper;

    @Test
    void deleteById_success_whenNoEmployees() {
        Integer deptId = 1;
        when(empMapper.countByDeptId(deptId)).thenReturn(0);

        deptService.deleteById(deptId);

        verify(empMapper).countByDeptId(deptId);
        verify(deptMapper).deleteById(deptId);
    }

    @Test
    void deleteById_throwBusinessException_whenHasEmployees() {
        Integer deptId = 2;
        when(empMapper.countByDeptId(deptId)).thenReturn(5);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> deptService.deleteById(deptId));
        assertEquals("部门下有员工， 不能删除", ex.getMessage());
        verify(deptMapper, never()).deleteById(deptId);
    }

    @Test
    void save_shouldFillCreateTimeAndUpdateTime() {
        Dept dept = new Dept();
        dept.setName("测试部");

        deptService.save(dept);

        assertNotNull(dept.getCreateTime());
        assertNotNull(dept.getUpdateTime());
        verify(deptMapper).insert(dept);
    }

    @Test
    void update_shouldFillUpdateTime() {
        Dept dept = new Dept();
        dept.setId(1);
        dept.setName("研发部");

        deptService.update(dept);

        assertNotNull(dept.getUpdateTime());
        verify(deptMapper).update(dept);
    }

    @Test
    void list_returnAllDepartments() {
        Dept d1 = new Dept(1, "研发部", null, null);
        Dept d2 = new Dept(2, "学工部", null, null);
        when(deptMapper.list()).thenReturn(Arrays.asList(d1, d2));

        List<Dept> result = deptService.list();

        assertEquals(2, result.size());
        assertEquals("研发部", result.get(0).getName());
        verify(deptMapper).list();
    }

    @Test
    void getById_returnCorrectDept() {
        Dept dept = new Dept(1, "研发部", null, null);
        when(deptMapper.getById(1)).thenReturn(dept);

        Dept result = deptService.getById(1);

        assertNotNull(result);
        assertEquals("研发部", result.getName());
    }

    @Test
    void getById_returnNull_whenNotFound() {
        when(deptMapper.getById(999)).thenReturn(null);

        Dept result = deptService.getById(999);

        assertNull(result);
    }
}
