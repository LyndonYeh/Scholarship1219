package scholarship.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// AOP controller : 回傳 方法與jsp輸入參數到 consolog debug 用
//servlet.xml 如有定義, @ annotation 可省略
@Aspect
@Component
public class LoggingAspect {
	@Before("execution(* scholarship.controller.*.*(..))")
	public void logMethodParams(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		System.out.printf("調用方法: %s 參數: %s%n", methodName, Arrays.toString(args));
	}
}