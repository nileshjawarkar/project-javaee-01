package com.nilesh.jawarkar.learn.javaee8.boundry;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import com.nilesh.jawarkar.learn.javaee8.entity.CarCreated;

@Path("car-created-sse")
@Singleton // -- Multiple instance of this make no sense.
public class CarCreatedSSE {

	// -- 1) inject sse context
	// -- Injection not working in tomee
	// @Context
	Sse            sse;

	SseBroadcaster broadcaster = null;

	// -- 2) create broadcaster
	@PostConstruct
	public void init() {
		// broadcaster = sse.newBroadcaster();
	}

	// -- 4) Broadcast events
	public void onCarCreation(@Observes final CarCreated carCreated) {
		if (broadcaster != null) {
			broadcaster.broadcast(sse.newEvent(carCreated.getIdentifier()));
		}
	}

	// -- 3) Register client for broadcast
	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void registerForEvent(@Context final Sse sse,
	        @Context final SseEventSink clientEventSink) {
		if (broadcaster == null) {
			this.sse    = sse;
			broadcaster = sse.newBroadcaster();
		}
		broadcaster.register(clientEventSink);
	}

	// -- Injection not working in tomee
	/*
	 * public void setSSE(@Context final Sse sse) { this.sse = sse; }
	 */
}
