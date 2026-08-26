package com.itcast.taliswebmanagement.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 当前持有者类，用于线程本地存储当前员工ID。
 * 使用ThreadLocal确保每个线程拥有独立的员工ID副本，避免多线程环境下的数据竞争问题。
 */
@Slf4j
public class CurrentHolder {

    // 使用ThreadLocal来存储当前线程的员工ID，初始值为null
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程的员工ID。
     * @param employeeId 要设置的员工ID，可以为null
     */
    public static void setCurrentId(Integer employeeId){
        log.info("设置当前线程的员工ID:{}",employeeId);
        CURRENT_LOCAL.set(employeeId);
    }

    /**
     * 获取当前线程的员工ID。
     * @return 当前线程的员工ID，如果没有设置则返回null
     */
    public static Integer getCurrentId(){
        log.info("获取当前线程的员工ID");
        return CURRENT_LOCAL.get();
    }

    /**
     * 移除当前线程的员工ID。
     * 调用此方法后，再次调用getCurrentId()将返回null，直到重新设置。
     * 通常用于线程结束前清理资源，防止内存泄漏。
     */
    public static void remove(){
        log.info("移除当前线程的员工ID");
        CURRENT_LOCAL.remove();
    }
}
