package com.fhpt.imageqmind.aop;

import com.alibaba.fastjson.JSON;
import com.fhpt.imageqmind.annotation.EagleEye;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * aop切面
 */
@Component
@Aspect
@Slf4j
public class EnhanceClass {

    /**
     * 切到所有注解修饰的方法
     */
    @Pointcut("@annotation(com.fhpt.imageqmind.annotation.EagleEye)")
    public void eagleEye() {

    }

    @Around("eagleEye()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //请求开始时间戳
        long begin = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        EagleEye eagleEye = method.getAnnotation(EagleEye.class);
        //接口描述信息
        String desc = eagleEye.desc();
        log.info("=============请求开始==============");
        log.info("请求链接：{}", request.getRequestURL().toString());
        log.info("接口描述：{}", desc);
        log.info("请求类型：{}", request.getMethod());
        log.info("请求控制器：{}", signature.getDeclaringTypeName());
        log.info("请求方法：{}", signature.getName());
        log.info("请求IP：{}", request.getRemoteAddr());
        log.info("请求入参：{}", JSON.toJSONString(pjp.getArgs()));
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("请求耗时：{} ms", end - begin);
        log.info("请求返回：{}", JSON.toJSONString(result));
        log.info("=============请求结束==============");
        return result;
    }

}
