package com.fhpt.imageqmind.annotation;

import java.lang.annotation.*;


/**
 * 程序鹰眼：监控方法函数等调用
 * @author Marty
 */
//注解生命周期
@Retention(RetentionPolicy.RUNTIME)
//注解用于方法
@Target(ElementType.METHOD)
//该注解被javadoc记录
@Documented
public @interface EagleEye {

    /**
     * 接口描述
     */
    String desc() default "";
}
