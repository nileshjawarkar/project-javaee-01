package com.nilesh.jawarkar.learn.javaee8.boundry;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Path("cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource1 {

	@Inject
	CarManufacturer carManufacturer;

	@POST
	public Car createCar(@Valid @NotNull Specification specs) {
		return carManufacturer.createCar(specs);
	}

	@GET
	public List<Car> retrieveCars() {
		return carManufacturer.retrieveCars();
	}

}
