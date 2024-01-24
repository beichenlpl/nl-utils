package com.github.beichenlpl.nlutils.simple.annotation;

import java.lang.annotation.*;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableName {
    String value();
}
