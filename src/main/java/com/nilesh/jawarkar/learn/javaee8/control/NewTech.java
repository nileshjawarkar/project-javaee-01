package com.nilesh.jawarkar.learn.javaee8.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE,
		ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NewTech {
}
