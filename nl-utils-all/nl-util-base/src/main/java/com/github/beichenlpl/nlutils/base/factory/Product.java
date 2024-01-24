package com.github.beichenlpl.nlutils.base.factory;

import java.lang.annotation.*;

/**
 * @author beichenlpl
 * @since 2023/09/06
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Product {
    String value() default "";

    String name() default "";
}