package com.bobabrewery.common.annotation;

import com.bobabrewery.common.aop.WebLog;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启@WebLog注解实现记录请求
 *
 * @author orange
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebLog.class)
public @interface EnableWebLog {
}
