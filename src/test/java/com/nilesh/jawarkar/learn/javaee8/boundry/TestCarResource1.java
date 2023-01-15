package com.nilesh.jawarkar.learn.javaee8.boundry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Date;
import java.util.List;

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
public class TestCarResource1 {
	static long startTime = 0;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "carman.war")
		        .addPackage("com.nilesh.jawarkar.learn.javaee8.boundry")
		        .addPackage("com.nilesh.jawarkar.learn.javaee8.config")
		        .addPackage("com.nilesh.jawarkar.learn.javaee8.control")
		        .addPackage("com.nilesh.jawarkar.learn.javaee8.entity")
		        .addAsResource("persistence.xml", "META-INF/persistence.xml")
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
	EntityManager   entityManager;

	@Inject
	UserTransaction utx;

	@ArquillianResource
	private URL     url;

	WebTarget       createAndQueryTarget = null;
	Client          client               = null;

	@After
	public void cleanUp() {
		client.close();
	}

	@Before
	public void init() throws NotSupportedException, SystemException,
	        IllegalStateException, SecurityException, HeuristicMixedException,
	        HeuristicRollbackException, RollbackException {
		utx.begin();
		entityManager.joinTransaction();
		System.out.println("Dumping old records...");
		entityManager.createQuery("delete from Car").executeUpdate();
		utx.commit();

		String       strURL    = "http://localhost:8080/carman/resources/cars";
		final String startPort = System.getProperty("tomee.httpPort");
		if (url != null) {
			strURL = url.toString() + "/resources/cars";
		} else if (startPort != null) {
			strURL = "http://localhost:" + startPort + "/carman/resources/cars";
		}
		System.out.println("URL = " + strURL);
		client               = ClientBuilder.newClient();
		createAndQueryTarget = client.target(strURL);
	}

	@Test
	public void should_create_and_retrieve_car() {
		final JsonObject obj     = Json.createObjectBuilder()
		        .add("engineType", EngineType.DIESEL.name())
		        .add("color", Color.BLUE.name()).build();

		Response         postRes = createAndQueryTarget
		        .request(MediaType.APPLICATION_JSON).post(Entity.json(obj));
		final Car        car01   = postRes.readEntity(Car.class);
		assertNotNull(car01);

		postRes = createAndQueryTarget.request(MediaType.APPLICATION_JSON)
		        .post(Entity.json(obj));
		assertNotNull(postRes);
		final Car car02 = postRes.readEntity(Car.class);
		assertNotNull(car02);

		final Response getRes = createAndQueryTarget.request(MediaType.APPLICATION_JSON)
		        .get();
		assertNotNull(getRes);

		final GenericType<List<Car>> genCarList = new GenericType<List<Car>>() {
												};
		final List<Car>              carList    = getRes.readEntity(genCarList);
		assertNotNull(carList);
		assertEquals(2, carList.size());
		carList.forEach(c -> System.out.println(c.getId()));

	}
}
