package ru.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j // Slf4j - Simple logging facade for java (библиотека для логирования в lombok)
@Aspect
@Component
public class LoggingAspect {

    // Before - отрабатывает до аспектируемого метода
    // AfterThrowing - отрабатывает после выброса любого exception
    // AfterReturning - отрабатывает после аспектируемого метода
    // After = AfterReturning + AfterThrowing
    // Around -> вызывается вместо аспектируемого метода

    // Bean = TimesheetService, obj = timesheetService
    // proxyTimesheetService(obj)

    @Pointcut("execution(* ru.gb.service.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

    // Pointcut - точка входа в аспект
    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetServiceFindById(JoinPoint jp) {
/*      для домашнего задания (1)
        Object[] args = jp.getArgs();
        args[0].getClass();
*/
        String methodName = jp.getSignature().getName();
        log.info("Before -> TimesheetService#{}", methodName);
    }

  /*
  @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
  public void afterTimesheetServiceFindById(JoinPoint jp, Exception ex) {
    String methodName = jp.getSignature().getName();
    log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
  }

  @Around(value = "timesheetServiceMethodsPointcut()")
  public Object aroundTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
    return pjp.proceed();

    Timesheet timesheet = new Timesheet():  //
    timesheet.setId(-100L);                 // в этом случае оригинальный метод даже не будет вызван
    return Optional.of(timesheet);          //
  }

    // Замер времени работы метода (вход в БД) {вынесен в класс TimerAspect}
    @Around(value = "timesheetServiceMethodsPointcut()")
    public Object aroundTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("TimesheetService#{} duration = {}ms", pjp.getSignature().getName(), duration);
        }
    }
  */
}
