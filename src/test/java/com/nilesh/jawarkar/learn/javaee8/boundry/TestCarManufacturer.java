package com.nilesh.jawarkar.learn.javaee8.boundry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nilesh.jawarkar.learn.javaee8.entity.Car;
import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.EngineType;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@RunWith(Arquillian.class)
public class TestCarManufacturer {
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
	EntityManager entityManager;

	@Inject
	UserTransaction utx;

	@Inject
	CarManufacturer carManufacturer;

	@Before
	public void init() throws NotSupportedException, SystemException,
			IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException {
		this.utx.begin();
		this.entityManager.joinTransaction();
		System.out.println("Dumping old records...");
		this.entityManager.createQuery("delete from Car").executeUpdate();
		this.utx.commit();
	}

	@Test
	public void should_create_car() {
		final Specification spec = new Specification();
		spec.setColor(Color.BLUE);
		spec.setEngineType(EngineType.DIESEL);
		final Car car = this.carManufacturer.createCar(spec);
		assertNotNull(car);

		final List<Car> cars = this.carManufacturer.retrieveCars();
		assertNotNull(cars);
		assertEquals(cars.size(), 1);

		LockSupport.parkNanos(6000000000L);
	}

	@Test
	public void should_search_car() {
		final Specification spec01 = new Specification();
		spec01.setColor(Color.BLUE);
		spec01.setEngineType(EngineType.DIESEL);
		this.carManufacturer.createCar(spec01);

		final Specification spec02 = new Specification();
		spec02.setColor(Color.RED);
		spec02.setEngineType(EngineType.ELECTRIC);
		this.carManufacturer.createCar(spec02);
		this.carManufacturer.createCar(spec02);

		final List<Car> cars = this.carManufacturer.retrieveCars("color",
				Color.BLUE.name());
		assertNotNull(cars);
		assertEquals(1, cars.size());

		final List<Car> cars2 = this.carManufacturer.retrieveCars("color",
				Color.RED.name());
		assertNotNull(cars2);
		assertEquals(2, cars2.size());

		final List<Car> cars3 = this.carManufacturer.retrieveCars();
		assertNotNull(cars3);
		assertEquals(3, cars3.size());

		LockSupport.parkNanos(7000000000L);
	}
}
