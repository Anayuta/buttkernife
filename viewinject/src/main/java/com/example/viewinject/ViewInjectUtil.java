package com.example.viewinject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.viewinject.annotation.BindView;
import com.example.viewinject.annotation.ContentView;
import com.example.viewinject.annotation.EvensBase;
import com.example.viewinject.annotation.OnClick;
import com.example.viewinject.annotation.OnLongClick;
import com.example.viewinject.handler.DynamicHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by nayuta on 2017/4/3.
 */

public class ViewInjectUtil {

    private static final String LAYOUT_METHOD = "setContentView";
    private static final String VIEW_METHOD = "findViewById";

    /**
     * 绑定注解
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        injectLayout(activity);
        injectView(activity);
        injectClickEvents(activity);
        injectLongClickEvents(activity);
    }

    /**
     * 长按事件,注意OnLongClick返回一个boolean
     *
     * @param activity
     */
    private static void injectLongClickEvents(Activity activity) {
        Class<? extends Activity> a = activity.getClass();
        //得到activity的所有方法
        Method[] declaredMethods = a.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(OnLongClick.class)) {
                //判断下参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                //作为click的方法接收一个参数View
                if (parameterTypes.length != 1 || !parameterTypes[0].getName().equals("android.view.View")) {
                    Log.e("ViewInjectUtil", "onClick Method parameterType must be View...");
                    return;
                }
                OnLongClick annotation = method.getAnnotation(OnLongClick.class);
                int[] viewIds = annotation.value();//得到注解的所有值
                //获取EventsBase的注解
                EvensBase evensBase = annotation.annotationType().getAnnotation(EvensBase.class);
                //得到EventsBase的值
                Class listenerType = evensBase.listenerType();
                String listenerSetter = evensBase.listenerSetter();
                String methodName = evensBase.methodName();
                //动态代理
                DynamicHandler handler = new DynamicHandler(activity);
                //将OnLongClickListener给动态代理handler执行
                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                handler.addMethod(methodName, method);//
                //为每个view设置点击事件
                for (int viewId : viewIds) {
                    try {
                        //findviewById操作
                        Method viewMethod = a.getMethod(VIEW_METHOD, int.class);
                        viewMethod.setAccessible(true);
                        Object view = viewMethod.invoke(activity, viewId);//得到view的findviewById实例
                        //得到该view的setterOnClick方法
                        Method setterOnLongClickMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        setterOnLongClickMethod.setAccessible(true);
                        //onLongClick返回一个boolean
                        setterOnLongClickMethod.invoke(view, listener);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * 单击事件注解
     *
     * @param activity
     */
    private static void injectClickEvents(Activity activity) {
        Class<? extends Activity> a = activity.getClass();
        //得到activity的所有方法
        Method[] declaredMethods = a.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                //判断下参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                //作为click的方法接收一个参数View
                if (parameterTypes.length != 1 || !parameterTypes[0].getName().equals("android.view.View")) {
                    Log.e("ViewInjectUtil", "onClick Method parameterType must be View...");
                    return;
                }
                OnClick annotation = method.getAnnotation(OnClick.class);
                int[] viewIds = annotation.value();//得到注解的所有值
                //获取EventsBase的注解
                EvensBase evensBase = annotation.annotationType().getAnnotation(EvensBase.class);
                //得到EventsBase的值
                Class listenerType = evensBase.listenerType();
                String listenerSetter = evensBase.listenerSetter();
                String methodName = evensBase.methodName();
                //动态代理
                DynamicHandler handler = new DynamicHandler(activity);
                //将OnClickListener给动态代理handler执行
                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                handler.addMethod(methodName, method);//
                //为每个view设置点击事件
                for (int viewId : viewIds) {
                    try {
                        //findviewById操作
                        Method viewMethod = a.getMethod(VIEW_METHOD, int.class);
                        viewMethod.setAccessible(true);
                        Object view = viewMethod.invoke(activity, viewId);//得到view的findviewById实例
                        //得到该view的setterOnClick方法
                        Method setterOnClickMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        setterOnClickMethod.setAccessible(true);
                        //
                        setterOnClickMethod.invoke(view, listener);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * 字段注册，用于初始化
     *
     * @param activity
     */
    private static void injectView(Activity activity) {
        Class<? extends Activity> a = activity.getClass();
        //获取activity的所有字段
        Field[] declaredFields = a.getDeclaredFields();
        //得到BindView的注解
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(BindView.class)) {//如果该Field是Bindview的注解
                //得到字段注解
                BindView annotation = field.getAnnotation(BindView.class);
                int viewId = annotation.value();//得到注解的值
                if (viewId == 0) {
                    return;
                }
                try {
                    Method method = a.getMethod(VIEW_METHOD, int.class);
                    method.setAccessible(true);
                    Object resView = method.invoke(activity, viewId);
                    field.setAccessible(true);//设置该Field可访问
                    field.set(activity, resView);//赋值
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 布局注解
     *
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        Class<? extends Activity> a = activity.getClass();
        if (a.isAnnotationPresent(ContentView.class)) {
            //获取contentview注解
            ContentView annotation = a.getAnnotation(ContentView.class);
            //得到注解的值
            int layoutId = annotation.value();
            //反射实现
            if (layoutId == -1) {
                return;
            }
            try {
                Method method = a.getMethod(LAYOUT_METHOD, int.class);//根据方法和参数类型，获取该方法
                method.setAccessible(true);//设置可访问
                method.invoke(activity, layoutId);//注入到该activity中
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
