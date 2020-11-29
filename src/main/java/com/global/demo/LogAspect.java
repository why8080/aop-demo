package com.global.demo;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
	
	/**
     * 前置增强：目标方法执行之前执行
     *
     * @param jp
     */
	@Before("execution(* com.global.demo.dao..*.*(..))")
	public void log(JoinPoint point) {
		 String methodName = point.getSignature().getName();
		 log.info("【前置增强】" + point.getTarget().getClass() + "the method 【" + methodName + "】 args= " + Arrays.asList(point.getArgs()));
	}
	
	/**
     * 后置增强：目标方法执行之后执行以下方法体的内容，不管是否发生异常。
     *
     * @param jp
     */
	@After("execution(* com.global.demo.dao..*.*(..))")
	public void logAfter(JoinPoint point){
		String methodName = point.getSignature().getName();
		log.info("【后置增强】"+ point.getTarget().getClass() +"the method 【" + methodName + "】 args= " + Arrays.asList(point.getArgs()));
	}
	
	/**
     * 返回增强：目标方法正常执行完毕时执行
     *
     * @param jp
     * @param result
     */

    @AfterReturning(value = "execution(* com.global.demo.dao..*.*(..))", returning = "result")
	public void afterReturningMethod(JoinPoint point, Object result) {
        String methodName = point.getSignature().getName();
        log.info("【返回增强】the method 【" + methodName + "】 ends with 【" + result + "】");
    }
    
    /**
     * 异常增强：目标方法发生异常的时候执行，第二个参数表示补货异常的类型
     *
     * @param jp
     * @param e
     */
    @AfterThrowing(value = "execution(* com.global.demo.dao..*.*(..))", throwing = "e")
    public void afterThorwingMethod(JoinPoint point, Exception e) {
        String methodName = point.getSignature().getName();
        log.error("【异常增强】the method 【" + methodName + "】 occurs exception: ", e);
    }

    /**
     * 环绕增强：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
     *
     * @return
     */
    @Around(value = "execution(* com.global.demo.dao..*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint point) {
        String methodName = point.getSignature().getName();
        Object result = null;
        try {
            log.info("【环绕增强中的--->前置增强】：the method 【" + methodName + "】 begins with " + Arrays.asList(point.getArgs()));
            //执行目标方法
            result = point.proceed();
            log.info("【环绕增强中的--->返回增强】：the method 【" + methodName + "】 ends with " + result);
        } catch (Throwable e) {
            result = "error";
            log.info("【环绕增强中的--->异常增强】：the method 【" + methodName + "】 occurs exception " + e);
        }
        log.info("【环绕增强中的--->后置增强】：-----------------end.----------------------");
        return result;
    }

}
