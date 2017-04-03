package com.example.viewinject.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nayuta on 2017/4/3.
 * <p>
 * 单击OnClick
 */

@Target(ElementType.METHOD)//方法注解
@Retention(RetentionPolicy.RUNTIME)
@EvensBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick")
public @interface OnClick {
    int[] value();//支持多个view
}
