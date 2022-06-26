package com.oursurvey.config.advice;

import com.oursurvey.dto.MyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    /**
     * 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            RuntimeException.class
    })
    public MyResponse exceptionFor400(Exception e) {
        return new MyResponse().setCode(MyResponse.CLIENT_ERROR).setMessage(e.getMessage());
    }

    /**
     * 마지막 처리용
     */
    @ExceptionHandler
    public MyResponse exceptionFor500(Exception e) {
        return new MyResponse().setCode(MyResponse.SERVER_ERROR).setMessage(e.getMessage());
    }
}
