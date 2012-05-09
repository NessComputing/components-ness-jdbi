package com.nesscomputing.jdbi.binding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

/**
 * Like {@link BindBean} except that it also defines all arguments for use
 * in StringTemplate expressions
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@BindingAnnotation(BindDefineBeanFactory.class)
public @interface BindDefineBean
{
    String value() default "___jdbi_bare___";
}
