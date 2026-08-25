package com.itcast.taliswebmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.taliswebmanagement.exception.BusinessException;
import com.itcast.taliswebmanagement.pojo.Dept;
import com.itcast.taliswebmanagement.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeptController.class)
class DeptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeptService deptService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_returnsDeptList() throws Exception {
        Dept d1 = new Dept(1, "研发部", null, null);
        Dept d2 = new Dept(2, "学工部", null, null);
        when(deptService.list()).thenReturn(Arrays.asList(d1, d2));

        mockMvc.perform(get("/depts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data[0].name").value("研发部"))
                .andExpect(jsonPath("$.data[1].name").value("学工部"));
    }

    @Test
    void delete_success() throws Exception {
        doNothing().when(deptService).deleteById(1);

        mockMvc.perform(delete("/depts").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
    }

    @Test
    void delete_returnsError_whenDeptHasEmployees() throws Exception {
        doThrow(new BusinessException("部门下有员工， 不能删除"))
                .when(deptService).deleteById(2);

        mockMvc.perform(delete("/depts").param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("部门下有员工， 不能删除"));
    }

    @Test
    void save_success() throws Exception {
        Dept dept = new Dept();
        dept.setName("测试部");
        doNothing().when(deptService).save(any(Dept.class));

        mockMvc.perform(post("/depts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
    }

    @Test
    void save_returnsError_whenNameIsBlank() throws Exception {
        String jsonBody = "{\"name\":\"\"}";

        mockMvc.perform(post("/depts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void getById_success() throws Exception {
        Dept dept = new Dept(1, "研发部", null, null);
        when(deptService.getById(1)).thenReturn(dept);

        mockMvc.perform(get("/depts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.name").value("研发部"));
    }

    @Test
    void update_success() throws Exception {
        Dept dept = new Dept(1, "研发部改", null, null);
        doNothing().when(deptService).update(any(Dept.class));

        mockMvc.perform(put("/depts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
    }
}
