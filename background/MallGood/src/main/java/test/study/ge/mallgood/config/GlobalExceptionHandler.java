package test.study.ge.mallgood.config;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.study.ge.mallgood.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 匹配bind的exception， 传参不一致
    // json不常见，form表单提交比较常见
    @ExceptionHandler(BindException.class)
    public Object bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        System.out.println("bindException:"+ Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return "bindException:"+Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
    }

    // 模式方法不正确
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result bindException(MethodArgumentNotValidException e) {
        System.out.println("MethodArgumentNotValidException");
        System.out.println("*********************************************************");
        System.out.println(e.getBindingResult().getFieldError().getDefaultMessage());
        System.out.println(e.getBindingResult().getFieldError().getField());
        return Result.error(e.getBindingResult().getFieldError().getField(),e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // 所有异常，对于其他异常都可以捕获
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest req) {
        System.out.println("handleException");
        System.out.println(e);
        return "handleException";

    }
}
