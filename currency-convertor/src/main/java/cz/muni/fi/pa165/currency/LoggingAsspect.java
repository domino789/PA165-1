package cz.muni.fi.pa165.currency;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAsspect {

    @Pointcut("execution(* cz.muni.fi.pa165.currency.CurrencyConvertor.*(..))")
    public void convert() {
        System.out.println("logBeforeIsRunning!");
        //System.out.println("Method is going to run " + joinPoint.getSignature().getName());
        System.out.println("***************");
    }
    @After("execution(* cz.muni.fi.pa165.currency.CurrencyConvertor.convert(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("logAfterIsRunning!");
        System.out.println("Method ran " + joinPoint.getSignature().getName());
        System.out.println("***************");
    }
}
