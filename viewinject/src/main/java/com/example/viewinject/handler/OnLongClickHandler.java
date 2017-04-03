package com.example.viewinject.handler;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nayuta on 2017/4/3.
 */

public class OnLongClickHandler implements InvocationHandler {

    private final Map<String, Method> methodMap = new HashMap<>();
    //防止内存泄漏
    private WeakReference<Object> activityRef;

    public OnLongClickHandler(Object activityRef) {
        this.activityRef = new WeakReference<Object>(activityRef);
    }

    /**
     * @param name
     * @param method
     */
    public void addMethod(String name, Method method) {
        if (!methodMap.containsKey(name)) {
            this.methodMap.put(name, method);
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object activity = activityRef.get();//得到acitivity实例
        if (activity != null) {
            //method对应的就是回调方法的Onclick，得到方法名
            String methodName = method.getName();
            method = methodMap.get(methodName);
            if (method != null) {
                //回调clickBtnInvoked方法
                //java.lang.IllegalArgumentException: Wrong number of arguments; expected 0, g
                Object object = method.invoke(activity, objects);
                return object == null ? false : Boolean.valueOf(object.toString());
            } else {
                Log.w("Handler", "please call #addMethod() Method...");
            }
        }
        return false;
    }
}
