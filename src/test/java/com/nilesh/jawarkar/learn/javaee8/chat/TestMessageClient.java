package com.nilesh.jawarkar.learn.javaee8.chat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.locks.LockSupport;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestMessageClient {
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "carman.war")
				.addPackage("com.nilesh.jawarkar.learn.javaee8.chat")
				.addAsWebInfResource("beans.xml", "beans.xml");
	}

	@ArquillianResource
	private URL url;

	private String strURL = null;

	@After
	public void cleanUp() {
	}

	@Before
	public void init() {
		this.strURL = "ws://localhost:8080/carman/chat";
		final String startPort = System.getProperty("tomee.httpPort");
		if (this.url != null) {
			this.strURL = this.url.toString().replace("http:", "ws:") + "/chat";
		} else if (startPort != null) {
			this.strURL = "ws://localhost:" + startPort + "/carman/chat";
		}
		System.out.println("URL = " + this.strURL);
	}

	@Test
	public void testClientAndServer() {
		MessageClient client = new MessageClient();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			container.connectToServer(client, new URI(this.strURL));

			Message msg1 = new Message("Nilesh", "This is test ping.");
			Message msg2 = new Message("Pankaj", "This is test ping.");
			Message msg3 = new Message("Gaurav", "This is test ping.");

			System.out.println("Sending message from - " + msg1.getAuthor() + ", msg = "
					+ msg1.getContent());
			client.send(msg1);

			System.out.println("Sending message from - " + msg2.getAuthor() + ", msg = "
					+ msg2.getContent());
			client.send(msg2);

			System.out.println("Sending message from - " + msg3.getAuthor() + ", msg = "
					+ msg3.getContent());
			client.send(msg3);

			LockSupport.parkNanos(2000000000L);

		} catch (DeploymentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
