package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.concurrent.locks.LockSupport;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;

@Stateless
public class CarProcessing {

	@Asynchronous
	public void processAsync(final Car car) {

		System.out.println(">> START - Processing a Car - " + car.getId() + " >>");
		// -- This is to simulate heavy job that takes 5 seconds to process.
		LockSupport.parkNanos(5000000000L);
		System.out.println("<< END - " + car.getId() + " <<");
	}

	public void process(final Car car) {

		System.out.println(">> START - Sync - Processing a Car - " + car.getId() + " >>");
		// -- This is to simulate heavy job that takes 5 seconds to process.
		LockSupport.parkNanos(5000000000L);
		System.out.println("<< END - " + car.getId() + " <<");
	}
}
