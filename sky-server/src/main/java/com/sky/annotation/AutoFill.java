package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation, use to annotate methods to autofill some values in db(update_time, update_user)
 */
@Target(ElementType.METHOD)//this annotation can only be used on methods
@Retention(RetentionPolicy.RUNTIME)//this annotation keeps alive during runtime
public @interface AutoFill {
    //database operation type (INSERT/UPDATE)
    OperationType value();
}
