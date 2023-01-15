package com.nilesh.jawarkar.learn.javaee8.boundry;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nilesh.jawarkar.learn.javaee8.control.CarFactory;
import com.nilesh.jawarkar.learn.javaee8.control.TrackColor;
import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.InvalidColor;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Stateless
public class CarManufacturer {
	@Inject
	CarFactory        carFactory;
	/*
	 * @Inject CarRepository carRepository;
	 */

	@PersistenceContext(name = "prod")
	EntityManager     entityManager;

	@Inject
	Event<CarCreated> carCreatedEvent;

	// -- @Interceptors(TrackFavouriteColor.class)
	@TrackColor(Color.ANY)
	public Car createCar(final Specification spec) {
		if (spec.getColor() == Color.ANY)
			throw new InvalidColor(Color.ANY);
		final Car car = carFactory.createCar(spec);
		// -- carRepository.save(car);
		entityManager.persist(car);
		carCreatedEvent.fire(new CarCreated(car.getId()));
		return car;
	}

	public Car retrieveCar(final String id) {
		// -- return carRepository.findById(id);
		return entityManager.createNamedQuery(Car.FIND_ONE, Car.class)
		        .setParameter("id", id).getSingleResult();
	}

	public List<Car> retrieveCars() {
		// -- return carRepository.getAll(null, null);
		return entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
	}

	public List<Car> retrieveCars(final String filterByAttr, final String filterByValue) {
		if (filterByAttr == null || filterByAttr.equals("") || filterByValue == null)
			return retrieveCars();
		// return carRepository.getAll(filterByAttr, filterByValue);
		final List<Car> result = entityManager
		        .createNativeQuery("select * from Cars where " + filterByAttr + "= ?1",
		                Car.class)
		        .setParameter(1, filterByValue).getResultList();
		return result;
	}

}
