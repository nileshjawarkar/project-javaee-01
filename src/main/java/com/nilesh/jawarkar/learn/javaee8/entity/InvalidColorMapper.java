package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider // -- This is required to enable the mapper.
public class InvalidColorMapper implements ExceptionMapper<InvalidColor> {
	@Override
	public Response toResponse(final InvalidColor exception) {
		return Response.status(Status.BAD_REQUEST).header("X-car-error", "InvalidColor")
		        .entity(exception.getMessage()).build();
	}
}
