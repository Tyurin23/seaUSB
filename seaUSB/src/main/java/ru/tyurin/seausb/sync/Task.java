package ru.tyurin.seausb.sync;


public class Task {

	private Long id;
	private TaskStatus status;
	private Storage src;
	private Storage dst;

	public Task(Long id) {
		this.id = id;
		this.status = TaskStatus.NOT_INIT;
	}

	public Long getId() {
		return id;
	}

	public Storage getSrc() {
		return src;
	}

	public void setSrc(Storage src) {
		this.src = src;
	}

	public Storage getDst() {
		return dst;
	}

	public void setDst(Storage dst) {
		this.dst = dst;
	}

	public TaskStatus getStatus() {
		return status;
	}
}
