package com.itcast.taliswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List clazzList;//班级列表
    private List dataList;//每个班级的学生人数
}
