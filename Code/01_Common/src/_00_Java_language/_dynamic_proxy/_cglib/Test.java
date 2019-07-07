package _00_Java_language._dynamic_proxy._cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * TODO fix bug
 */
public class Test {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();

        // 设置生成代理类的父类class对象
        enhancer.setSuperclass(Target.class);

        // 设置增强目标类的方法拦截器
        MethodInterceptor methodInterceptor = new TargetMethodInterceptor();
        enhancer.setCallback(methodInterceptor);

        // 生成代理类并实例化
        Target proxy = (Target) enhancer.create();

        // 用代理类调用方法
        proxy.request();
    }
}
