package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.concurrent.locks.LockSupport;

import javax.enterprise.event.ObservesAsync;

import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;

public class CarCreationListener {

	public void onCarCreation(@ObservesAsync final CarCreated event) {
		LockSupport.parkNanos(2000000000L);
		System.out.println("Car created - " + event.getIdentifier());
	}
}
