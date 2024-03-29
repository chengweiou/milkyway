package chengweiou.universe.milkyway.base.handler;


import org.springframework.context.annotation.Profile;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import chengweiou.universe.blackhole.exception.BaseExceptionHandler;
import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.exception.UnauthException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import lombok.extern.slf4j.Slf4j;

@Profile("prod")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(ProjException.class)
    public Rest handleProjException(ProjException ex) {
        return super.handleProjException(ex);
    }
    @ExceptionHandler(ParamException.class)
    public Rest handleParamException(ParamException ex) {
        return super.handleParamException(ex);
    }
    @ExceptionHandler(UnauthException.class)
    public Rest handleUnauthException(UnauthException ex) {
        return Rest.fail(BasicRestCode.UNAUTH);
    }
    @ExceptionHandler(BindException.class)
    public Rest handleParamException(BindException ex) {
        return Rest.fail(BasicRestCode.PARAM);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Rest handleParamException(MissingRequestHeaderException ex) {
        return Rest.fail(BasicRestCode.PARAM);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Rest handleException(HttpRequestMethodNotSupportedException ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        log.info(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(FailException.class)
    public Rest handleFailException(FailException ex) {
        return handleFailException(ex);
    }
    @ExceptionHandler(Exception.class)
    public Rest handleException(Exception ex) {
        return handleException(ex);
    }
}
