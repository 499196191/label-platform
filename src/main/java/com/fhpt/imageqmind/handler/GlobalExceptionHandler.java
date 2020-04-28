package com.fhpt.imageqmind.handler;

import com.fhpt.imageqmind.objects.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handle(Exception exception) {
        Result result = new Result();
        result.code = -1;
        StringBuilder stringBuilder = new StringBuilder();
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                stringBuilder.append(item.getMessage());
            }
        }
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) exception;
            List<ObjectError> allErrors = exs.getBindingResult().getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuilder.append(objectError.getDefaultMessage());
            }
        }
        result.msg = stringBuilder.toString();
        return result;
    }
}
