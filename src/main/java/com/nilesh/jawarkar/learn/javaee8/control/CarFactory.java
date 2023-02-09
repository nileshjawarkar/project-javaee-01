package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.UUID;

import javax.inject.Inject;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.CarUser;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.Diesel;
import com.nilesh.jawarkar.learn.javaee8.entity.PowerStearing;
import com.nilesh.jawarkar.learn.javaee8.entity.Seat;
import com.nilesh.jawarkar.learn.javaee8.entity.SeatBelt;
import com.nilesh.jawarkar.learn.javaee8.entity.SeatBeltModel;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;
import com.nilesh.jawarkar.learn.javaee8.entity.StearingType;

public class CarFactory {

	// -- @Named("Red")
	@Diesel
	@Inject
	Color defaultColor;

	public Car createCar(final Specification specs) {
		final Car car = new Car();
		final String id = UUID.randomUUID().toString();
		car.setId(id);
		car.setColor(specs.getColor() == null ? this.defaultColor : specs.getColor());
		car.setEngine(specs.getEngineType());

		car.setTestId(id);

		final PowerStearing stearing = new PowerStearing();
		stearing.setId(UUID.randomUUID().toString());
		stearing.setStearingType(StearingType.ELECTRONIC);
		car.setStearing(stearing);

		final SeatBelt belt = new SeatBelt();
		belt.setModel(SeatBeltModel.BM01);

		final Seat seat01 = new Seat();
		seat01.setId(UUID.randomUUID().toString());
		seat01.setSeatBelt(belt);

		final Seat seat02 = new Seat();
		seat02.setId(UUID.randomUUID().toString());
		seat02.setSeatBelt(belt);

		final Seat seat03 = new Seat();
		seat03.setId(UUID.randomUUID().toString());
		seat03.setSeatBelt(belt);

		final Seat seat04 = new Seat();
		seat04.setId(UUID.randomUUID().toString());
		seat04.setSeatBelt(belt);

		car.addSeat(seat01);
		car.addSeat(seat02);
		car.addSeat(seat03);
		car.addSeat(seat04);

		final CarUser defaultUser = new CarUser();
		defaultUser.setId(UUID.randomUUID().toString());
		defaultUser.setName("NA");
		car.addUser(defaultUser);

		return car;
	}
}
