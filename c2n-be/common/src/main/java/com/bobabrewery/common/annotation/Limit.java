package com.bobabrewery.common.annotation;

import com.bobabrewery.common.enums.ReCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author orange
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {
    /**
     * 次数
     *
     * @return
     */
    int max() default 1;

    /**
     * 时间
     *
     * @return
     */
    int time() default 5;


    /**
     * 默认错误提示
     *
     * @return
     */
    ReCode msg() default ReCode.REQUEST_TOO_FREQUENT;

    /**
     * 时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
