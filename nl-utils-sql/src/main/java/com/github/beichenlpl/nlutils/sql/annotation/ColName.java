package com.github.beichenlpl.nlutils.sql.annotation;

import com.github.beichenlpl.nlutils.sql.enums.ColType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.22
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColName {
    String value();

    ColType type() default ColType.DEFAULT;
}
