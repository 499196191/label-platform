package com.fhpt.imageqmind.exceptions;

/**
 * 自定义异常类，内部可以选择性传递需要的信息
 * @author Marty
 */
public class TaskNameVerifyException extends RuntimeException {
    public TaskNameVerifyException(String message) {
        super(message);
    }
}
