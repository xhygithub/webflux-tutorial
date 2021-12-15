package com.example.webfluxtutorial.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Pointcut("execution(* com.example.webfluxtutorial.controller.*.*(..))")
//    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) "
//            + "|| @within(org.springframework.stereotype.Controller)")
//    @Pointcut("execution(public * com.example.webfluxtutorial.controller.OrderController.getAllOrders(..))")
//    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void declareControllerMethodJointPointerExpression() {}

    @Around("declareControllerMethodJointPointerExpression()")
//    @Around("@within(org.springframework.web.bind.annotation.RestController) "
//            + "|| @within(org.springframework.stereotype.Controller)"
//            + "&& !execution(* org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.getErrorPath())")
    public Object logControllerMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logMethodStart(joinPoint);

        try {
            Object proceed = joinPoint.proceed();
            if(proceed instanceof Mono) {
                return ((Mono<?>) proceed).doFinally(r -> logMethodEnd(joinPoint));
            }
            if(proceed instanceof Flux) {
                return ((Flux<?>) proceed).doFinally(r -> logMethodEnd(joinPoint));
            }
            logMethodEnd(joinPoint);
            return proceed;
        } catch (Throwable e) {
            logMethodEnd(joinPoint);
            throw  e;
        }
    }

    private void logMethodEnd(JoinPoint joinPoint) {
        log.info("[Controller] method {} end", joinPoint.getSignature().getName());
    }

    private void logMethodStart(JoinPoint joinPoint) {
        log.info("[Controller] method {} start, with args: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }
}
