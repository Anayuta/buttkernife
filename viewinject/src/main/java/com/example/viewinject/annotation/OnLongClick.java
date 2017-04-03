package com.example.viewinject.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nayuta on 2017/4/3.
 * <p>
 * 长按事件
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EvensBase(listenerType = View.OnLongClickListener.class, listenerSetter = "setOnLongClickListener", methodName = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
