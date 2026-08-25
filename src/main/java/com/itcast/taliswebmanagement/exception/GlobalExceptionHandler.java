package com.itcast.taliswebmanagement.exception;

import com.itcast.taliswebmanagement.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 在三层构架项目中，出现了异常，该如何处理?
     * 方法1:在所有Controller的所有方法中进行 try…catch处理
     *      缺点：代码臃肿（不推荐）
     *
     *
     * 方法2:定义全局异常处理器。定义一个类，在类上加上一个注解@RestControllerAdvice，
     *      加上这个注解就代表我们定义了一个全局异常处理器。
     *
     *      在全局异常处理器当中，需要定义一个方法来捕获异常，
     *      在这个方法上需要加上注解@ExceptionHandler。
     *      通过@ExceptionHandler注解当中的value属性来指定我们要捕获的是哪一类型的异常。
     */

    //处理异常
    @ExceptionHandler
    public Result handleException(Exception e){//方法形参中指定能够处理的异常类型

        // 根据不同异常类型返回更具体的错误提示
        String errorMessage = "操作失败，请联系管理员";
        if (e instanceof SQLException) {
            errorMessage = "数据库操作异常，请联系管理员";
        } else if (e instanceof NullPointerException) {
            errorMessage = "数据不完整，请联系管理员";
        } else if (e instanceof IllegalArgumentException) {
            errorMessage = e.getMessage(); // 直接使用业务校验的提示
        }
        // 记录完整的异常堆栈信息（关键修改）
        log.error(errorMessage+"\n错误提示："+e.getMessage());
        //捕获到异常之后，响应一个标准的Result
        return Result.error(errorMessage);
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错啦！错误原因：主键重复");
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMag = message.substring(i);
        String[] arr = errMag.split(" ");
        return Result.error(arr[2]+"已存在");
    }
}
