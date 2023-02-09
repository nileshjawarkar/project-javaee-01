package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seats")
@AttributeOverride(name = "id", column = @Column(name = "seat_Id"))
public class Seat extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	private SeatBelt seatBelt;

	private SeatMaterial seatMaterial;

	public SeatBelt getSeatBelt() {
		return this.seatBelt;
	}

	public void setSeatBelt(final SeatBelt seatBelt) {
		this.seatBelt = seatBelt;
	}

	public SeatMaterial getSeatMaterial() {
		return this.seatMaterial;
	}

	public void setSeatMaterial(final SeatMaterial seatMaterial) {
		this.seatMaterial = seatMaterial;
	}
}
