package com.libv.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogInfoAspect {
    @Pointcut("@annotation(com.libv.aop.annotation.RecordLoginInfo)")
    public void loginInfo() {
    }

    @Around(value = "loginInfo()", argNames = "proceedingJoinPoint")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
