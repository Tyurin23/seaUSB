package ru.tyurin.seausb.util;


import java.nio.file.Path;

public class USBDrive {


	private Path path;
	private String name;

	public USBDrive(Path path) {
		this.path = path;
	}


	public Path getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
