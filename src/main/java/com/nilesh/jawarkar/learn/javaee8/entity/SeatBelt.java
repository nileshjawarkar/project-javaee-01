package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class SeatBelt {

	@Enumerated(EnumType.STRING)
	private SeatBeltModel model;

	public void setModel(final SeatBeltModel model) {
		this.model = model;
	}

	public SeatBeltModel getModel() {
		return this.model;
	}
}
