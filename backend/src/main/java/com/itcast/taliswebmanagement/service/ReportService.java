package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.pojo.ClazzCountOption;
import com.itcast.taliswebmanagement.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计各个职位的员工人数
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计各个性别的员工人数
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 班级人数统计
     */
    ClazzCountOption getStudentCountData();

    /**
     * 统计学员的学历信息
     */
    List<Map> getStudentDegreeData();
}