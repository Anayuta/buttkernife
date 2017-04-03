package com.example.viewinject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nayuta on 2017/4/3.
 * <p>
 * 字段注解，初始化view
 */

@Target(ElementType.FIELD)//FIELD表示字段注解
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int value() default 0;
}
