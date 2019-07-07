package _00_Java_language._dynamic_proxy._cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("方法拦截增强逻辑-前置处理执行");
        Object result =methodProxy.invoke(o,objects);
        System.out.println("方法拦截增强逻辑-后置处理执行");
        return result;
    }
}
