package com.nilesh.jawarkar.learn.javaee8.boundry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;

@RunWith(Arquillian.class)
public class TestCarResource11 {
	static long startTime = 0;

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

	@AfterClass
	public static void endForAll() {
		final long endTime = new Date().getTime();
		System.out.println("Time taken by all tests - " + (endTime - startTime));
	}

	@BeforeClass
	public static void startForAll() {
		startTime = new Date().getTime();
	}

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	UserTransaction utx;

	@ArquillianResource
	private URL url;

	WebTarget createAndQueryTarget = null;
	Client    client               = null;

	@After
	public void cleanUp() {
		this.client.close();
	}

	@Before
	public void init() throws NotSupportedException, SystemException,
			IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException {
		this.utx.begin();
		this.entityManager.joinTransaction();
		System.out.println("Dumping old records...");
		this.entityManager.createQuery("delete from Car").executeUpdate();
		this.utx.commit();

		String strURL = "http://localhost:8080/carman/resources/cars";
		final String startPort = System.getProperty("tomee.httpPort");
		if (this.url != null) {
			strURL = this.url.toString() + "/resources/cars";
		} else if (startPort != null) {
			strURL = "http://localhost:" + startPort + "/carman/resources/cars";
		}
		System.out.println("URL = " + strURL);
		this.client = ClientBuilder.newClient();
		this.createAndQueryTarget = this.client.target(strURL);
	}

	@Test
	public void should_create_and_retrieve_car() {
		// -- Creation
		final JsonObject obj = Json.createObjectBuilder()
				.add("engineType", EngineType.DIESEL.name())
				.add("color", Color.BLUE.name()).build();

		Response postRes = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(obj));
		final Car car01 = postRes.readEntity(Car.class);
		assertNotNull(car01);

		postRes = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(obj));
		assertNotNull(postRes);
		final Car car02 = postRes.readEntity(Car.class);
		assertNotNull(car02);

		// -- Retrieval
		final GenericType<List<String>> genCarList = new GenericType<List<String>>() {
		};

		List<String> carList = null;
		try {
			carList = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
					.async().get(genCarList).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		assertNotNull(carList);
		assertEquals(2, carList.size());
		carList.forEach(c -> System.out.println(c));

		LockSupport.parkNanos(5000000000L);
	}
}
