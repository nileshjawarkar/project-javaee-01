package com.nilesh.jawarkar.learn.javaee8.entity;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@AttributeOverride(name = "id", column = @Column(name = "carId"))
@NamedQueries({ @NamedQuery(name = Car.FIND_ALL, query = "select c from Car c"),
		@NamedQuery(name = Car.FIND_ONE, query = "select c from Car c where c.id = :id") })
public class Car extends BaseEntity {
	public static final String FIND_ALL = "Car.findAll";
	public static final String FIND_ONE = "Car.findOne";

	@Column(name = "engine")
	@Enumerated(EnumType.STRING)
	private EngineType engineType;

	@Enumerated(EnumType.STRING)
	private Color color;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "stearing_id")
	private PowerStearing stearing;

	@OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
	private Collection<Seat> seats;

	public Collection<Seat> getSeats() {
		return this.seats;
	}

	public void setSeats(final Collection<Seat> seats) {
		this.seats = seats;
	}

	public PowerStearing getStearing() {
		return this.stearing;
	}

	public void setStearing(final PowerStearing stearing) {
		this.stearing = stearing;
	}

	public Color getColor() {
		return this.color;
	}

	public EngineType getEngineType() {
		return this.engineType;
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
}
