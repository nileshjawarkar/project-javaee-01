package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.ejb.ApplicationException;

//-- This is required for EJB - to manage exception as application exception.
//-- Otherwise container will wrap this exception inside EJBException.
@ApplicationException
public class InvalidColor extends RuntimeException {

	private static final long serialVersionUID = 3606745040081820644L;

	public InvalidColor(final Color c) {
		super("Color configuration not supported - " + c.name());
	}

}
