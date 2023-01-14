package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@NamedQueries({ @NamedQuery(name = Car.FIND_ALL, query = "select c from Car c"),
        @NamedQuery(name = Car.FIND_ONE, query = "select c from Car c where c.id = :id") })
public class Car {
	public static final String FIND_ALL = "Car.findAll";
	public static final String FIND_ONE = "Car.findOne";

	@Id
	private String             id;

	@Enumerated(EnumType.STRING)
	private EngineType         engineType;
	@Enumerated(EnumType.STRING)
	private Color              color;

	public Color getColor() {
		return color;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public String getId() {
		return id;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public void setEngine(final EngineType engineType) {
		this.engineType = engineType;
	}

	public void setEngineType(final EngineType engineType) {
		this.engineType = engineType;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setIdentifier(final String id) {
		this.id = id;
	}

}
