package com.itcast.taliswebmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.itcast.taliswebmanagement.mapper")
@SpringBootApplication
@ServletComponentScan
public class TalisWebManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(TalisWebManagementApplication.class, args);
    }

}
