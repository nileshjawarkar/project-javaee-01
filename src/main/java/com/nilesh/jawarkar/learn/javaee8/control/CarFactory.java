package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
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
		Car car = new Car();
		car.setId(UUID.randomUUID().toString());
		car.setColor(specs.getColor() == null ? this.defaultColor : specs.getColor());
		car.setEngine(specs.getEngineType());

		PowerStearing stearing = new PowerStearing();
		stearing.setId(UUID.randomUUID().toString());
		stearing.setStearingType(StearingType.ELECTRONIC);
		car.setStearing(stearing);

		SeatBelt belt = new SeatBelt();
		belt.setModel(SeatBeltModel.BM01);

		Seat seat01 = new Seat();
		seat01.setId(UUID.randomUUID().toString());
		seat01.setSeatBelt(belt);

		Seat seat02 = new Seat();
		seat02.setId(UUID.randomUUID().toString());
		seat02.setSeatBelt(belt);

		Seat seat03 = new Seat();
		seat03.setId(UUID.randomUUID().toString());
		seat03.setSeatBelt(belt);

		Seat seat04 = new Seat();
		seat04.setId(UUID.randomUUID().toString());
		seat04.setSeatBelt(belt);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat01);
		seats.add(seat02);
		seats.add(seat03);
		seats.add(seat04);

		car.setSeats(seats);

		return car;
	}
}
