package com.itcast.taliswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.taliswebmanagement.mapper.OperateLogMapper;
import com.itcast.taliswebmanagement.pojo.Emp;
import com.itcast.taliswebmanagement.pojo.OperateLog;
import com.itcast.taliswebmanagement.pojo.PageResult;
import com.itcast.taliswebmanagement.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<OperateLog> operateLogList = operateLogMapper.list();
        Page<OperateLog> p = (Page<OperateLog>)operateLogList;
        return new PageResult<OperateLog>(p.getTotal(), p.getResult());
    }
}
