package com.itcast.taliswebmanagement.aop;

import com.itcast.taliswebmanagement.mapper.OperateLogMapper;
import com.itcast.taliswebmanagement.pojo.OperateLog;
import com.itcast.taliswebmanagement.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect//切面
@Component//组件
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    //环绕通知
    @Around("@annotation(com.itcast.taliswebmanagement.anno.LogOperation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        // 耗时
        long costTime = endTime - startTime;

        // 构建日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());//作用：：获取当前用户id
        operateLog.setOperateTime(LocalDateTime.now());//作用：获取当前时间
        operateLog.setClassName(joinPoint.getTarget().getClass().getName());//作用：获取当前类名
        operateLog.setMethodName(joinPoint.getSignature().getName());//作用：获取当前方法名
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));//作用：获取当前方法的参数
        operateLog.setReturnValue(result.toString());//作用：获取当前方法返回值
        operateLog.setCostTime(costTime);//作用：获取当前方法耗时

        //插入日志
        log.info("操作日志：{}", operateLog);
        operateLogMapper.insert(operateLog);
        return result;
    }

    // 获取当前用户id
    private Integer getCurrentUserId() {
        //这里应该根据实际情况从认证信息中获取当前登陆用户的id
        return CurrentHolder.getCurrentId();
    }
}
