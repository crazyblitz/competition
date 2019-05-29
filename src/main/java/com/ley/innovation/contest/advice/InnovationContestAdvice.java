package com.ley.innovation.contest.advice;

import com.ley.innovation.contest.utils.InnovationContestException;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.ResponseMessageCode;
import com.ley.springboot.base.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * innovation contest advice
 **/
@ControllerAdvice
@Order(value = 1)
@Slf4j
public class InnovationContestAdvice {


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InnovationContestException.class)
    @ResponseBody
    public ResponseMessage handlerOrderSystemBaseException(InnovationContestException exception) {
        log.warn(exception.getMessage(), exception);
        return Result.error(exception.getErrorCode(), exception.getMessage(), false);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage handlerOrderSystemBaseException(Exception exception) {
        log.error(exception.getMessage(), exception);
        // TODO 在数据库中记录程序异常，这个地方的异常是未处理的异常，需要管理员查看并进行处理以防重复出现
        return Result.error(ResponseMessageCode.ERROR.getCode(), "程序异常,请重试.如果重复出现请联系管理员处理!",
                false);
    }
}
