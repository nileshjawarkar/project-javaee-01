package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnvoirnmentFriendlyValidator
        implements ConstraintValidator<EnvoirnmentFriendly, EngineType> {

	@Override
	public void initialize(final EnvoirnmentFriendly con) {

	}

	@Override
	public boolean isValid(final EngineType value,
	        final ConstraintValidatorContext context) {
		return (value == EngineType.ELECTRIC);
	}
}
