package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.EmpLog;

public interface EmpLogService {
    //记录新增员工日志
    public void insertLog(EmpLog empLog);
}
