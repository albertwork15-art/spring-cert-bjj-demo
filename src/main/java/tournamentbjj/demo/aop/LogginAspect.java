package tournamentbjj.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {

    @Pointcut("execution(* tournamentbjj.demo.aop.AopDemoService.*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP - @Before] Se va a ejecutar el método: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[AOP - @AfterReturning] El método finalizó con éxito. Resultado devuelto: " + result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("[AOP - @AfterThrowing] ¡Alerta! El método lanzó un error: " + error.getMessage());
    }

    @Around("execution(* tournamentbjj.demo.aop.AopDemoService.performAction(..))")
    public Object profilePerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("[AOP - @Around] Iniciando medición de tiempo...");
        try {
            Object result = proceedingJoinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            System.out.println("[AOP - @Around] Método finalizado. Tiempo de ejecución: " + duration + " ms");
            return result;
        } catch (Throwable e) {
            System.out.println("[AOP - @Around] Se capturó un error durante la ejecución.");
            throw e;
        }
    }
}