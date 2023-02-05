package com.nilesh.jawarkar.learn.javaee8.control;

import javax.enterprise.inject.Produces;

import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.Diesel;

public class DefaultColorProducer {

	// -- @Named("Red")
	@Diesel
	@Produces
	public Color setDefaultColor() {
		return Color.RED;
	}

	/*
	 * public void cleanUp(@Disposes Color color) { color = null; }
	 */
}
