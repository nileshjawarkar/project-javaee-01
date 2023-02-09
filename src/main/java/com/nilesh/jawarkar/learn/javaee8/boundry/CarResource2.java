package com.nilesh.jawarkar.learn.javaee8.boundry;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;
import javax.json.stream.JsonCollectors;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Path("/v2/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource2 {

	@Context
	UriInfo uriInfo;

	@Inject
	CarManufacturer carManufacturer;

	@POST
	public Response createCar(@NotNull final JsonObject jsonSpec) {
		final String strColor = (jsonSpec.containsKey("color")
				? jsonSpec.getString("color")
				: null);
		final String strEngineType = (jsonSpec.containsKey("engineType")
				? jsonSpec.getString("engineType")
				: null);
		if (strEngineType != null) {
			final Specification spec = new Specification();
			spec.setColor(strColor == null ? Color.RED : Color.valueOf(strColor));
			spec.setEngineType(EngineType.valueOf(strEngineType));

			final Car car = this.carManufacturer.createCar(spec);
			final URI uri = this.uriInfo.getBaseUriBuilder().path(CarResource2.class)
					.path(CarResource2.class, "retrieveCar").build(car.getId());
			return Response.created(uri).entity(car).build();

		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("{id}")
	public Response retrieveCar(@PathParam("id") final String id) {
		final Car car = this.carManufacturer.retrieveCar(id);
		if (car != null) {
			final JsonObject jobj = Json.createObjectBuilder().add("id", car.getId())
					.add("color", car.getColor().name())
					.add("engineType", car.getEngineType().name()).build();
			return Response.status(Response.Status.OK).entity(jobj).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Path("/aa")
	public Response retrieveSelectedCars(@NotNull final JsonArray carIds) {
		final List<String> ids = new ArrayList<>();
		for (int i = 0; i < carIds.size(); ++i) {
			if (carIds.get(i).getValueType() == ValueType.STRING) {
				ids.add(carIds.getString(i));
			}
		}

		if (ids != null && ids.size() > 0) {

			final JsonArray resultList = this.carManufacturer.retrieveSelectedCars(ids);
			/*
			 * final JsonArray resultList = cars.stream().map(car -> { return
			 * Json.createObjectBuilder().add("id", car.getId()) .add("color",
			 * car.getColor().name()) .add("engineType",
			 * car.getEngineType().name()).build();
			 * }).collect(JsonCollectors.toJsonArray());
			 */

			final Response res = Response.ok().entity(resultList).build();
			return res;
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	public Response retrieveCarIds(@QueryParam("attr") final String filterByAttr,
			@QueryParam("value") final String filterByValue) {
		final List<String> list = this.carManufacturer.retrieveCarIds(filterByAttr,
				filterByValue);
		if (list != null) {
			final JsonArray resultList = list.stream().map(c -> {
				return Json.createValue(c);
			}).collect(JsonCollectors.toJsonArray());
			final Response res = Response.ok().entity(resultList).build();
			return res;
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
