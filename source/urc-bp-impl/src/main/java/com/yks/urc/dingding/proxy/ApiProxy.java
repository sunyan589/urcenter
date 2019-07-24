package com.yks.urc.dingding.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.dingding.client.DingApiProxyImpl;

/**
 * @Author: wujianghui@youkeshu.com
 * @Date: 2018/6/6 8:59
 */
public class ApiProxy {

    /**
     * 得到钉钉接口的proxy
     * @return
     */
    public  DingApiProxy getDingProxy() {
        DingApiProxy dingApiProxy = new DingApiProxyImpl();
        return (DingApiProxy) Proxy.newProxyInstance(ApiProxy.class.getClassLoader(), dingApiProxy.getClass().getInterfaces(),new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(dingApiProxy, args);
                    }
                });
    }

    //其他代理类编写

}
