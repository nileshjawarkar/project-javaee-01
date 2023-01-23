package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.UUID;

import javax.inject.Inject;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.Diesel;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

public class CarFactory {

	// -- @Named("Red")
	@Diesel
	@Inject
	Color defaultColor;

	public Car createCar(final Specification specs) {
		Car car = new Car();
		car.setId(UUID.randomUUID().toString());
		car.setColor(specs.getColor() == null ? this.defaultColor : specs.getColor());
		car.setEngine(specs.getEngineType());
		return car;
	}
}
