package com.nilesh.jawarkar.learn.javaee8.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Constraint(validatedBy = EnvoirnmentFriendlyValidator.class)
@Documented
public @interface EnvoirnmentFriendly {
	// -- These 3 lines are very important.. other validation contraint
	// -- will not work. It will always fail validation.
	Class<?>[] groups() default {};

	String message() default "Invalid EngineType.";

	Class<? extends Payload>[] payload() default {};
}
