/*
 * 文件名：UrcServiceDemo.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月23日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.service;

import com.yks.urc.motan.service.api.IUrcService;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月23日
 * @see UrcServiceDemo
 * @since JDK1.8
 */
public class UrcServiceDemo {

    public static void main(String[] args) {
        Class<IUrcService> clz = IUrcService.class;
        Method[] methods = clz.getMethods();
        Arrays.asList(methods).stream().forEach(method -> {
            System.out.println(method.getReturnType().getName() + " " + method.getName() + " "+ Arrays.asList(method.getParameterTypes()));
        });
    }
}
