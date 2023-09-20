package com.exercice.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("@annotation(com.exercice.test.annotation.ApiTracking)")
    public Object  logAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        log.info("API Access {} with arguments {} ", joinPoint.getSignature().getName(),getAgrs(joinPoint));
        var response = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long  duration = (end-begin);
        log.info("API output {} after duration of {} milliseconds", joinPoint.getSignature().getName(),duration);
        return response;
    }

    public String getAgrs(ProceedingJoinPoint joinPoint)
    {
        var ret = new StringBuilder();
        for(Object o : joinPoint.getArgs())
        {
            if(o != null)
            {
                if(!ret.isEmpty())
                {
                    ret.append(ret).append(",");
                }
                ret.append(ret).append(o);
            }
        }
        return ret.toString();
    }

}
