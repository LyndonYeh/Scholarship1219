package scholarship.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// AOP controller package 的日誌紀錄 : 回傳1. 方法 2. jsp 輸入參數到 Console, debug 用
//servlet.xml 如有定義, @annotation 可省略
@Aspect
@Component
public class LoggingAspect {
	@Before("execution(* scholarship.controller.*.*(..))")
	public void logMethodParams(JoinPoint joinPoint) {
		// 取得執行的方法名稱
		String methodName = joinPoint.getSignature().getName();
		// 取得傳入方法的參數
		Object[] args = joinPoint.getArgs();
		// 紀錄方法名稱與方法參數
		System.out.printf("調用方法: %s 參數: %s%n", methodName, Arrays.toString(args));
	}
}