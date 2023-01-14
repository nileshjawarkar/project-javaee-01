package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.validation.constraints.NotNull;

public class Specification {
	Color      color;

	@NotNull
	// -- @EnvoirnmentFriendly
	EngineType engineType;

	public Color getColor() {
		return color;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public void setEngineType(final EngineType engineType) {
		this.engineType = engineType;
	}

}
