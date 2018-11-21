package main.java.com.att.eg.common.platform.kubekleaner.model;

import lombok.Data;

@Data
public class HelloWorld {

	private String message;

	public HelloWorld() {
		// needed for deserializer
	}
	
	public HelloWorld(String message) {
		this.message = message;
	}
}
