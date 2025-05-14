package com.mpc.wallet.app.api.common.exception;


import com.mpc.wallet.app.api.common.enums.AppCode;
import com.mpc.wallet.app.api.common.response.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 异常拦截
 *
 * @author leo
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private MessageSource messageSource;
    @Resource
    private HttpServletRequest request;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        FieldError fieldError = (FieldError) objectError;
        String message = objectError.getDefaultMessage();
        return Result.fail(AppCode.PARAMS_ERROR.getCode() , String.format("[%s] %s" , fieldError.getField() , message) , null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return Result.fail(AppCode.PARAMS_ERROR.getCode() , message , null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgument(IllegalArgumentException ex) {
        String message = messageSource.getMessage(ex.getMessage() , null , LocaleContextHolder.getLocale());
        return Result.fail(AppCode.FAIL.getCode() , message , null);
    }


    @ExceptionHandler(AppServerException.class)
    public Object handleAppServerException(AppServerException ex) {
        String message = messageSource.getMessage(ex.getCode().toString() , ex.getParam() , LocaleContextHolder.getLocale());
        return Result.fail(ex.getCode() , message , null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object methodException(HttpRequestMethodNotSupportedException ex) {
        String message = messageSource.getMessage(String.valueOf(AppCode.WRONG_REQUEST.getCode()) , null ,
                LocaleContextHolder.getLocale());
        return new Result<>(AppCode.WRONG_REQUEST.getCode() , message , null);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex) {
        String message = messageSource.getMessage(String.valueOf(AppCode.SERVER_ERROR.getCode()) , null ,
                LocaleContextHolder.getLocale());
        return Result.fail(AppCode.SERVER_ERROR.getCode() , message , null);
    }

    @ExceptionHandler(Throwable.class)
    public Object handleThrowable(Exception ex) {
        String message = messageSource.getMessage(String.valueOf(AppCode.SERVER_ERROR.getCode()) , null ,
                LocaleContextHolder.getLocale());
        return Result.fail(AppCode.SERVER_ERROR.getCode() , message , null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleThrowable(HttpMessageNotReadableException ex) {
        String message = messageSource.getMessage(String.valueOf(AppCode.PARAMS_ERROR.getCode()) , null ,
                LocaleContextHolder.getLocale());
        return Result.fail(AppCode.PARAMS_ERROR.getCode() , message , null);
    }

}
