package com.itcast.taliswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台给前端返回的数据包含：
 * List集合(数据列表)【当前页面】
 * total(总记录数)【每页展示记录数】
 * 而这两部分我们封装到PageResult对象中
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total; //总记录数
    private List<T> rows; //当前页数据列表
}
