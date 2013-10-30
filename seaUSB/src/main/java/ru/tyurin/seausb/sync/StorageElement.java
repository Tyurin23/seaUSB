package ru.tyurin.seausb.sync;


import java.nio.file.attribute.FileTime;

public class StorageElement {

	public enum Type {
		FILE,
		DIRECTORY
	}

	private Type type;
	private long hash;
	private long size;
	private FileTime dateModified;

	public static StorageElement file() {
		return new StorageElement(Type.FILE);
	}

	public static StorageElement directory() {
		return new StorageElement(Type.DIRECTORY);
	}

	protected StorageElement(Type type) {
		this.type = type;
	}

	public FileTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(FileTime dateModified) {
		this.dateModified = dateModified;
	}

	public long getHash() {
		return hash;
	}

	public void setHash(long hash) {
		this.hash = hash;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
