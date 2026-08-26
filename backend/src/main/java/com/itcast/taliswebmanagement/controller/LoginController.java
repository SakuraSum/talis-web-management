package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.LoginInfo;
import com.itcast.taliswebmanagement.pojo.Result;
import com.itcast.taliswebmanagement.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("员工登录,{}",emp);
        LoginInfo loginInfo = empService.login(emp);
        if(loginInfo!=null){
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
