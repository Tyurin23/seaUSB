package ru.tyurin.seausb.sync;


import java.io.IOException;
import java.io.Serializable;

public class Task implements Serializable {

	private Long id;
	private TaskStatus status;
	private Storage src;
	private Storage dst;
	private boolean saved;

	public Task(Long id) {
		this.id = id;
		this.status = TaskStatus.READY;
		this.saved = false;
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
		validStatus();
		return status;
	}

	public boolean isSaved() {
		return saved;
	}

	public boolean isDifferent() {
		return src.equalsStorage(dst);
	}

	public boolean isReady() {
		if (src != null && dst != null) {
			return src.isReady() && dst.isReady();
		} else {
			return false;
		}
	}

	public void index() throws IOException {
		Synchronizer.index(src);
		Synchronizer.index(dst);
	}

	public FutureStatus synchronize() {
		Synchronizer synchronizer = new Synchronizer(this);
		FutureStatus status = synchronizer.getStatus();
		synchronizer.start();
		this.status = TaskStatus.SYNCING;
		return status;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	private void validStatus() {
		if (src != null && dst != null) {
			if (src.equalsStorage(dst)) {
				status = TaskStatus.SYNC;
			} else {
				status = TaskStatus.READY;
			}
		}
	}
}
