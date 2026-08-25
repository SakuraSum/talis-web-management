package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.PageResult;

public interface OperateLogService {
    PageResult page(Integer page, Integer pageSize);
}
