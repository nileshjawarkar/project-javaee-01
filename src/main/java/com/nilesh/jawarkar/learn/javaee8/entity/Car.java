package com.nilesh.jawarkar.learn.javaee8.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@AttributeOverride(name = "id", column = @Column(name = "car_Id"))
@NamedQueries({ @NamedQuery(name = Car.FIND_ALL_IDS, query = "SELECT c.id FROM Car c"),
		@NamedQuery(name = Car.FIND_ALL, query = "SELECT c FROM Car c join fetch c.stearing WHERE c.id IN :carIds"),
		@NamedQuery(name = Car.FIND_ONE, query = "SELECT c FROM Car c where c.id = :id") })
public class Car extends BaseEntity {
	public static final String FIND_ALL     = "Car.findAll";
	public static final String FIND_ALL_IDS = "Car.findAllIds";
	public static final String FIND_ONE     = "Car.findOne";

	@Column(name = "engine")
	@Enumerated(EnumType.STRING)
	private EngineType engineType;

	@Enumerated(EnumType.STRING)
	private Color color;

	private String testId;

	public String getTestId() {
		return this.testId;
	}

	public void setTestId(final String testId) {
		this.testId = testId;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "stearing_id")
	private PowerStearing stearing;

	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
	private Collection<Seat> seats = new ArrayList<>();

	@ManyToMany(mappedBy = "cars", fetch = FetchType.LAZY)
	private Collection<CarUser> users = new ArrayList<>();

	public Collection<CarUser> getUsers() {
		return this.users;
	}

	public void addUser(final CarUser user) {
		this.users.add(user);
	}

	public Collection<Seat> getSeats() {
		return this.seats;
	}

	public void addSeat(final Seat seat) {
		this.seats.add(seat);
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
