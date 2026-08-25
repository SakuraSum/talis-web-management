package com.itcast.taliswebmanagement.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private String name; //学生名称
    private Integer degree;//学历
    private Integer clazzId;//学生所属班级id
    private Integer page = 1; //页码
    private Integer pageSize = 10; //每页展示记录数
}
