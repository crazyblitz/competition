package com.ley.innovation.contest.advice;


import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.ResponseMessageCode;
import com.ley.springboot.base.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@Slf4j
public class MultipartExceptionAdvice {


    @Value("${spring.http.multipart.max-file-size}")
    private String maxFileSize;

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseMessage handlerAdcDaBaseException(MultipartException exception) {
        log.warn(exception.getMessage(), exception);
        return Result.error(ResponseMessageCode.ERROR.getCode(), "文件大小超过限制(" + maxFileSize.toUpperCase() + ")",
                false);
    }

}
