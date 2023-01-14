package com.nilesh.jawarkar.learn.javaee8.entity;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintVoilationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		String msg = "Some message";
		return Response.status(Status.EXPECTATION_FAILED)
		        .header("X-Validation-Error", msg).build();

		// -- Note - header("X-Validation-Error", "CustomeError")
		// -- Here CustomeError can not be null.
	}
}
