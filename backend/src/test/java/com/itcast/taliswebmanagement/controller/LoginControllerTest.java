package com.itcast.taliswebmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.LoginInfo;
import com.itcast.taliswebmanagement.service.EmpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpService empService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_success_returnsLoginInfoWithToken() throws Exception {
        Emp emp = new Emp();
        emp.setUsername("admin");
        emp.setPassword("123456");

        LoginInfo loginInfo = new LoginInfo(1, "admin", "管理员", "eyJhbGciOiJIUzI1NiJ9.mock-token");
        when(empService.login(any(Emp.class))).thenReturn(loginInfo);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("admin"))
                .andExpect(jsonPath("$.data.name").value("管理员"))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    void login_fail_returnsErrorMessage() throws Exception {
        Emp emp = new Emp();
        emp.setUsername("wronguser");
        emp.setPassword("wrongpass");
        when(empService.login(any(Emp.class))).thenReturn(null);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("用户名或密码错误"));
    }

    @Test
    void login_returnsError_whenUsernameMissing() throws Exception {
        String jsonBody = "{\"password\":\"123456\"}";

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void login_returnsError_whenPasswordMissing() throws Exception {
        String jsonBody = "{\"username\":\"admin\"}";

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }
}
