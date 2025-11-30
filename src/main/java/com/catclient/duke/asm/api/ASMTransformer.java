package com.catclient.duke.asm.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

@Getter
@AllArgsConstructor
@SuppressWarnings("unused")
public class ASMTransformer {
    private Class<?> target;

    @Retention(RetentionPolicy.RUNTIME)
    @java.lang.annotation.Target(ElementType.METHOD)
    public @interface Inject {
        String method();

        String desc();
    }
}