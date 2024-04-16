package com.bobabrewery.common.annotation;

import com.bobabrewery.common.constant.PermissionConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author PailieXiangLong
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * {@link PermissionConstant}
     *
     * @return
     */
    String value();
}
