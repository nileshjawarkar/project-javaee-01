package com.nilesh.jawarkar.learn.javaee8.control;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;

public class CarRepository {

	private List<Car> list = new ArrayList<>();

	public CarRepository() {
		final Car c1 = new Car();
		c1.setId(UUID.randomUUID().toString());
		c1.setColor(Color.BLUE);
		c1.setEngine(EngineType.DIESEL);
		list.add(c1);

		final Car c2 = new Car();
		c2.setId(UUID.randomUUID().toString());
		c2.setColor(Color.WHITE);
		c2.setEngine(EngineType.PETROL);
		list.add(c2);
	}

	public Car findById(final String id) {
		for (final Car c : list) {
			final String cid = c.getId();
			if (cid != null & cid.equals(id))
				return c;
		}
		return null;
	}

	public List<Car> getAll(final String filterByAttr, final String filterByValue) {
		if (filterByAttr == null || filterByAttr.equals("") || filterByValue == null
		        || filterByValue.equals(""))
			return list;

		return list.stream().filter(c -> {
			if (filterByAttr.equals("color"))
				return c.getColor() == Color.valueOf(filterByValue);
			if (filterByAttr.equals("engineType"))
				return c.getEngineType() == EngineType.valueOf(filterByValue);
			return false;
		}).collect(Collectors.toList());
	}

	public void save(final Car car) {
		list.add(car);
	}

}
