package com.fhpt.imageqmind.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


import org.springframework.stereotype.Component;

/**
 * aop切面
 */
@Component
@Aspect
public class EnhanceClass {

    @Pointcut("execution(* com.fhpt.imageqmind.service..*.*(..))")
    public void point(){

    }

    @After("point()")
    public void afterLog(){
        System.out.println("测试AOP————————blablablabla");
    }

}
