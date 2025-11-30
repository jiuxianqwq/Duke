package com.catclient.duke.asm.api.annotations;

import com.catclient.duke.utils.asm.ASMUtils;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Local {
    String source();

    String target() default "";

    int index() default -1;

    class Builder {
        public static Local fromAnnotation(AnnotationNode annotation) {
            return new Local() {

                @Override
                public Class<? extends Annotation> annotationType() {
                    return Local.class;
                }

                @Override
                public String source() {
                    return ASMUtils.getAnnotationValue(annotation, "source");
                }

                @Override
                public String target() {
                    Object object = ASMUtils.getAnnotationValue(annotation, "target");
                    if (object != null) return (String) object;
                    return "";
                }

                @Override
                public int index() {
                    Object object = ASMUtils.getAnnotationValue(annotation, "index");
                    if (object != null) return (int) object;
                    return -1;
                }
            };
        }
    }
}
