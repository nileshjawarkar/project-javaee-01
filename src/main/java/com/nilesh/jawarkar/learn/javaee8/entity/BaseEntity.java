package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	@Id
	protected String id;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
