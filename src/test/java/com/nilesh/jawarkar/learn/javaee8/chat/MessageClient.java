package com.nilesh.jawarkar.learn.javaee8.chat;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class MessageClient {

	private Session userSession = null;

	@OnOpen
	public void onOpen(final Session userSession) {
		System.out.println("opening websocket");
		this.userSession = userSession;
	}

	@OnClose
	public void onClose(final Session userSession, final CloseReason reason) {
		System.out.println("closing websocket");
		this.userSession = null;
	}

	@OnMessage
	public void onMessage(final Message message, final Session session) {
		System.out.println("Message - " + message.getContent());
	}

	public void send(final Message message) {
		if (this.userSession != null) {
			try {
				this.userSession.getBasicRemote().sendObject(message);
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		}
	}
}
