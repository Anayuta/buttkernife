package com.example.viewinject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nayuta on 2017/4/3.
 * <p>
 * 布局注解器
 */
@Target(ElementType.TYPE)//Type表示是类、接口或是枚举注解
@Retention(RetentionPolicy.RUNTIME)//运行时注解
public @interface ContentView {
    int value() default -1;
}
