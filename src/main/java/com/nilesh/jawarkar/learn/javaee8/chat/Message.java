package com.nilesh.jawarkar.learn.javaee8.chat;

public class Message {
	private final String author;
	private final String content;

	public Message(final String author, final String content) {
		this.author = author;
		this.content = content;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getContent() {
		return this.content;
	}
}
