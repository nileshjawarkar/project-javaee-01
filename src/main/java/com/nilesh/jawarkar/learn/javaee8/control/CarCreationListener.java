package com.nilesh.jawarkar.learn.javaee8.control;

import javax.enterprise.event.Observes;

import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;

public class CarCreationListener {
	
	public void onCarCreation(@Observes CarCreated event) {
		System.out.println("Car created - " + event.getIdentifier());
	}
}
