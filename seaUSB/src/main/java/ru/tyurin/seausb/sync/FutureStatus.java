package ru.tyurin.seausb.sync;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FutureStatus {

	private Lock lock = new ReentrantLock();

	private int status = 0;

	public int getStatus() {
		try {
			lock.lock();
			return status;
		} finally {
			lock.unlock();
		}
	}

	public void setStatus(int status) {
		try {
			lock.lock();
			this.status = status;
		} finally {
			lock.unlock();
		}
	}
}
