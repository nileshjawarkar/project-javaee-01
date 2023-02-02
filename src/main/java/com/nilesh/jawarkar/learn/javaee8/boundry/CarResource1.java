package com.nilesh.jawarkar.learn.javaee8.boundry;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Path("cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource1 {

	@Inject
	CarManufacturer carManufacturer;

	@Resource
	ManagedExecutorService mes;

	@POST
	public void createCar(@Valid @NotNull final Specification specs,
			@Suspended final AsyncResponse response) {
		this.mes.execute(() -> response.resume(this.carManufacturer.createCar(specs)));
	}

	@GET
	public List<Car> retrieveCars() {
		List<Car> list = this.carManufacturer.retrieveCars();
		return list;
	}

	/*
	 * @GET public CompletionStage<List<Car>> retrieveCars01() { return
	 * CompletableFuture.supplyAsync(() -> this.carManufacturer.retrieveCars(),
	 * this.mes); }
	 */

}
