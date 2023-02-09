package com.nilesh.jawarkar.learn.javaee8.entity;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_Id"))
public class CarUser extends BaseEntity {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "USERS_CAR", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "car_id"))
	private Collection<Car> cars;

	public Collection<Car> getCars() {
		return this.cars;
	}

	public void setCars(final Collection<Car> cars) {
		this.cars = cars;
	}
}
