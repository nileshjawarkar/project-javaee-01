package com.nilesh.jawarkar.learn.javaee8.boundry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import javax.json.Json;
import javax.json.JsonObject;
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
public class TestCarResource2 {
	static long startTime = 0;

	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive arc = ShrinkWrap.create(WebArchive.class, "carman.war")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.boundry")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.config")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.control")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.entity")
				.addAsResource("persistence_test.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("beans.xml", "beans.xml");
		System.out.println(arc.toString(true));
		return arc;
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

	/*
	 * @PersistenceContext EntityManager entityManager;
	 *
	 * @Inject UserTransaction utx;
	 */

	@ArquillianResource
	private URL url;

	WebTarget createAndQueryTarget = null;
	WebTarget queryTarget          = null;
	Client    client               = null;

	@After
	public void cleanUp() {
		this.client.close();
	}

	@Before
	public void init() /*
						 * throws NotSupportedException, SystemException,
						 * IllegalStateException, SecurityException,
						 * HeuristicMixedException, HeuristicRollbackException,
						 * RollbackException
						 */ {
		/*
		 * utx.begin(); entityManager.joinTransaction();
		 * System.out.println("Dumping old records...");
		 * entityManager.createQuery("delete from Car").executeUpdate(); utx.commit();
		 */

		String strURL = "http://localhost:8080/carman/resources/v2/cars";
		final String startPort = System.getProperty("tomee.httpPort");
		if (this.url != null) {
			strURL = this.url.toString() + "/resources/v2/cars";
		} else if (startPort != null) {
			strURL = "http://localhost:" + startPort + "/carman/resources/v2/cars";
		}
		System.out.println("URL = " + strURL);
		this.client = ClientBuilder.newClient();
		this.createAndQueryTarget = this.client.target(strURL);
		this.queryTarget = this.client.target(strURL + "?attr={attr}&value={value}");
	}

	@Test
	public void should_create_and_retrieve_car() {

		// -- Create 2 blue cars
		final JsonObject blueCarJO = Json.createObjectBuilder()
				.add("engineType", EngineType.DIESEL.name())
				.add("color", Color.BLUE.name()).build();

		// -- 1 blue car
		Response postRes = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(blueCarJO));
		final Car car01 = postRes.readEntity(Car.class);
		assertNotNull(car01);

		// -- 2 blue car
		postRes = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(blueCarJO));
		assertNotNull(postRes);
		final Car car02 = postRes.readEntity(Car.class);
		assertNotNull(car02);

		// --- Get all the cars.. must be 2
		final Response getRes = this.createAndQueryTarget
				.request(MediaType.APPLICATION_JSON).get();
		assertNotNull(getRes);

		// -- Using generic type as we can not use List<Car>.class
		final GenericType<List<String>> genCarList = new GenericType<List<String>>() {
		};
		final List<String> carList = getRes.readEntity(genCarList);
		assertNotNull(carList);
		assertEquals(2, carList.size());
		carList.forEach(c -> System.out.println(c));

		// -- create 1 red car
		final JsonObject redCarJO = Json.createObjectBuilder()
				.add("engineType", EngineType.PETROL.name())
				.add("color", Color.RED.name()).build();

		postRes = this.createAndQueryTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(redCarJO));
		assertNotNull(postRes);
		final Car car03 = postRes.readEntity(Car.class);
		assertNotNull(car03);

		// -- Retrieve red cars - must be 1
		final Response getResRed = this.queryTarget.resolveTemplate("attr", "color")
				.resolveTemplate("value", Color.RED.name())
				.request(MediaType.APPLICATION_JSON).get();

		final List<String> redCarList = getResRed.readEntity(genCarList);
		assertNotNull(redCarList);
		assertEquals(1, redCarList.size());

		// -- Retrieve blue cars - must be 2
		final Response getResBlue = this.queryTarget.resolveTemplate("attr", "color")
				.resolveTemplate("value", Color.BLUE.name())
				.request(MediaType.APPLICATION_JSON).get();

		// -- Using JsonArray to read value instead of generic type.
		/*
		 * final List<String> blueCarIdList =
		 * getResBlue.readEntity(JsonArray.class).stream() .map(jco ->
		 * jco.asJsonObject().getString("id")) .collect(Collectors.toList()); }
		 */

		final List<String> blueCarIdList = getResBlue.readEntity(genCarList);
		assertNotNull(blueCarIdList);
		assertEquals(2, blueCarIdList.size());
		blueCarIdList.forEach(id -> System.out.println("id = " + id));

		LockSupport.parkNanos(5000000000L);
	}
}
