package com.nilesh.jawarkar.learn.javaee8.boundry;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.nilesh.jawarkar.learn.javaee8.control.CarFactory;
import com.nilesh.jawarkar.learn.javaee8.control.CarProcessing;
import com.nilesh.jawarkar.learn.javaee8.control.NewTech;
import com.nilesh.jawarkar.learn.javaee8.control.TrackColor;
import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;
import com.nilesh.jawarkar.learn.javaee8.entity.CarUser;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;
import com.nilesh.jawarkar.learn.javaee8.entity.InvalidColor;
import com.nilesh.jawarkar.learn.javaee8.entity.PowerStearing;
import com.nilesh.jawarkar.learn.javaee8.entity.Seat;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Stateless
public class CarManufacturer {
	@Inject
	CarFactory carFactory;
	/*
	 * @Inject CarRepository carRepository;
	 */

	@PersistenceContext(name = "prod")
	EntityManager entityManager;

	@Inject
	Event<CarCreated> carCreatedEvent;

	@NewTech
	@Inject
	Event<CarCreated> newTechCarCreatedEvent;

	@Inject
	CarProcessing carProcessing;

	@Resource
	ManagedExecutorService mes;

	// -- @Interceptors(TrackFavouriteColor.class)
	@TrackColor(Color.ANY)
	public Car createCar(final Specification spec) {
		if (spec.getColor() != null && spec.getColor() == Color.ANY) {
			throw new InvalidColor(Color.ANY);
		}
		final Car car = this.carFactory.createCar(spec);
		// -- carRepository.save(car);
		final Collection<Seat> seats = car.getSeats();
		if (seats != null) {
			seats.forEach(s -> {
				this.entityManager.persist(s);
			});
		}

		final Collection<CarUser> users = car.getUsers();
		if (users != null) {
			users.forEach(u -> {
				this.entityManager.persist(u);
			});
		}

		this.entityManager.persist(car);

		if (car.getEngineType() == EngineType.ELECTRIC) {
			this.newTechCarCreatedEvent.fireAsync(new CarCreated(car.getId()));
		} else {
			this.carCreatedEvent.fire(new CarCreated(car.getId()));
		}

		// -- this.carProcessing.processAsync(car);

		this.mes.execute(() -> this.carProcessing.process(car));
		return car;
	}

	public Car retrieveCar(final String id) {
		// -- return carRepository.findById(id);
		return this.entityManager.createNamedQuery(Car.FIND_ONE, Car.class)
				.setParameter("id", id).getSingleResult();
	}

	public JsonArray retrieveSelectedCars(final List<String> carIds) {

		final List<Car> carList = this.entityManager
				.createNamedQuery(Car.FIND_ALL, Car.class).setParameter("carIds", carIds)
				.getResultList();

		if (carList != null && carList.size() > 0) {
			final JsonArrayBuilder builder = Json.createArrayBuilder();
			for (final Car car : carList) {
				final PowerStearing stearing = car.getStearing();
				builder.add(Json.createObjectBuilder().add("id", car.getId())
						.add("color", car.getColor().name())
						.add("engineType", car.getEngineType().name())
						.add("stearing",
								Json.createObjectBuilder().add("id", stearing.getId())
										.add("stearingType",
												stearing.getStearingType().name())
										.build())
						.build());
			}
			return builder.build();
		}
		return null;
	}

	public List<String> retrieveCarIds() {
		// -- return carRepository.getAll(null, null);
		return this.entityManager.createNamedQuery(Car.FIND_ALL_IDS, String.class)
				.getResultList();
	}

	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<String> retrieveCarIds(final String filterByAttr,
			final String filterByValue) {
		if (filterByAttr == null || "".equals(filterByAttr) || filterByValue == null) {
			return retrieveCarIds();
		}
		// return carRepository.getAll(filterByAttr, filterByValue);
		final List<String> result = this.entityManager.createNativeQuery(
				"select car_id from Cars where " + filterByAttr + "= ?1", String.class)
				.setParameter(1, filterByValue).getResultList();
		return result;
	}

}
