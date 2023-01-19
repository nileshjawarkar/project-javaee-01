package com.nilesh.jawarkar.learn.javaee8.chat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void destroy() {

	}

	@Override
	public void init(final EndpointConfig arg0) {
	}

	@Override
	public String encode(final Message message) throws EncodeException {
		return Json.createObjectBuilder().add("author", message.getAuthor())
				.add("content", message.getContent()).build().toString();
	}

}
