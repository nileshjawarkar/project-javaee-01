package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "stearing")
@AttributeOverride(name = "id", column = @Column(name = "stearingId"))
public class PowerStearing extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private StearingType stearingType;

	public StearingType getStearingType() {
		return this.stearingType;
	}

	public void setStearingType(final StearingType stearingType) {
		this.stearingType = stearingType;
	}
}
