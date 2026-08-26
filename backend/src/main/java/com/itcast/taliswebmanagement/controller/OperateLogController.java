package com.itcast.taliswebmanagement.controller;

import com.itcast.taliswebmanagement.pojo.OperateLog;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.pojo.Result;
import com.itcast.taliswebmanagement.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/log/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询操作日志数据，page={}, pageSize={}", page, pageSize);
        PageResult<OperateLog> pageResult = operateLogService.page(page, pageSize);
        return Result.success(pageResult);
    }
}
