package ru.tyurin.seausb.sync;


import java.nio.file.Path;

public class Task {

	private Long id;
	private TaskStatus status;
	private Path flash;
	private Path disk;

	public Task(Long id) {
		this.id = id;
		this.status = TaskStatus.NOT_INIT;
	}

	public Long getId() {
		return id;
	}

	public Path getDisk() {
		return disk;
	}

	public void setDisk(Path disk) {
		this.disk = disk;
	}

	public Path getFlash() {
		return flash;
	}

	public void setFlash(Path flash) {
		this.flash = flash;
	}

	public TaskStatus getStatus() {
		return status;
	}
}
