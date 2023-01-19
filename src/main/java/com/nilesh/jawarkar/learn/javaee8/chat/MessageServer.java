package com.nilesh.jawarkar.learn.javaee8.chat;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class MessageServer {

	@OnMessage
	public void onMessgae(final Message message, final Session session) {

		// -- Prepare response
		Message res = buildResponse(message);
		try {
			session.getBasicRemote().sendObject(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}

	private Message buildResponse(final Message message) {
		StringBuilder resBuilder = new StringBuilder().append("Hi ")
				.append(message.getAuthor()).append(" - ");

		if (message.getContent().contains("ping")) {
			resBuilder.append("PONG");
		}

		return new Message("System", resBuilder.toString());
	}
}
