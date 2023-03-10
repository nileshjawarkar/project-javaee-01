package com.nilesh.jawarkar.learn.javaee8.boundry;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@RunWith(Arquillian.class)
public class TestCarCreatedEventBroadcast {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "carman.war")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.boundry")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.config")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.control")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.entity")
				.addAsResource("persistence_test.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("beans.xml", "beans.xml");
	}

	@ArquillianResource
	private URL url;

	private WebTarget sseTarget;
	private WebTarget createTarget;
	private Client    client;
	private int       eventRecieved = 0;

	@Resource(name = "DefaultManagedExecutorService")
	ManagedScheduledExecutorService mses;

	@After
	public void clear() {
		this.client.close();
	}

	public void createCar() {
		createCar(Color.BLUE, EngineType.DIESEL);
		createCar(Color.RED, EngineType.PETROL);
		createCar(Color.WHITE, EngineType.PETROL);
		createCar(Color.BLUE, EngineType.ELECTRIC);
	}

	private void createCar(final Color color, final EngineType engineType) {
		final Specification spec = new Specification();
		spec.setColor(color);
		spec.setEngineType(engineType);
		this.createTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(spec));
	}

	@Before
	public void init() {

		String strURL = "http://localhost:8080/carman/resources";
		final String startPort = System.getProperty("tomee.httpPort");
		if (this.url != null) {
			strURL = this.url.toString() + "/resources";
		} else if (startPort != null) {
			strURL = "http://localhost:" + startPort + "/carman/resources";
		}
		System.out.println("URL = " + strURL);
		this.client = ClientBuilder.newClient();
		this.sseTarget = this.client.target(strURL + "/car-created-sse");
		this.createTarget = this.client.target(strURL + "/cars");

	}

	@Test
	public void testCarCreatedSSE() {
		final SseEventSource source = SseEventSource.target(this.sseTarget).build();
		source.register(event -> {
			System.out.println(">>> " + event.readData());
			this.eventRecieved += 1;
		});
		this.mses.schedule(() -> createCar(), 1, TimeUnit.SECONDS);

		source.open();
		LockSupport.parkNanos(7000000000L);
		source.close();

		// -- For Electric call async type event handler is used, so its
		// -- event will not sent by SSE
		// -- assertEquals(4, this.eventRecieved);
		assertEquals(3, this.eventRecieved);
	}
}
