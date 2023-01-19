package com.nilesh.jawarkar.learn.javaee8.chat;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public Message decode(final String jsonString) throws DecodeException {
		JsonObject obj = Json.createReader(new StringReader(jsonString)).readObject();
		return new Message(obj.getString("author"), obj.getString("content"));
	}

	@Override
	public boolean willDecode(final String arg0) {
		return true;
	}

}
