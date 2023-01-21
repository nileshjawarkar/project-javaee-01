package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.concurrent.locks.LockSupport;

import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;

public class CarCreationListener {

	public void onCarCreation(@Observes final CarCreated event) {
		LockSupport.parkNanos(2000000000L);
		System.out.println("Car created - " + event.getIdentifier());
	}

	public void onNewTechCarCreation(@ObservesAsync @NewTech final CarCreated event) {
		LockSupport.parkNanos(2000000000L);
		System.out.println("New Tech Car created - " + event.getIdentifier());
	}
}
