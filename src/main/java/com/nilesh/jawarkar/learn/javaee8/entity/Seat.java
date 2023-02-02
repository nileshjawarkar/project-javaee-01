package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seats")
@AttributeOverride(name = "id", column = @Column(name = "seatId"))
public class Seat extends BaseEntity {

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
