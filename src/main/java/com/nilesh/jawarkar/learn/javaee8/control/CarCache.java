package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;

@Singleton
@Startup
public class CarCache {

	@Resource
	ManagedScheduledExecutorService mses;

	@PersistenceContext(name = "prod")
	EntityManager entityManager;

	HashMap<String, Car> carMap = new HashMap<>();

	// -- @Schedule(hour = "*")
	public void loadCars() {
		this.carMap.clear();
		List<Car> list = this.entityManager.createNamedQuery(Car.FIND_ALL, Car.class)
				.getResultList();
		list.forEach((car) -> {
			this.carMap.put(car.getId(), car);
		});
	}

	@PostConstruct
	public void scheduleLoading() {
		// -- Executes continuously after delay of 10 seconds and per 60 seconds.
		this.mses.scheduleAtFixedRate(() -> loadCars(), 10, 60, TimeUnit.SECONDS);
		// -- Execute only once after delay of 10 seconds
		// -- this.mses.schedule(() -> loadCars(), 10, TimeUnit.SECONDS);
	}

	public List<Car> retrieveCars() {
		List<Car> list = new ArrayList<>();
		this.carMap.forEach((id, car) -> {
			list.add(car);
		});
		return list;
	}
}
