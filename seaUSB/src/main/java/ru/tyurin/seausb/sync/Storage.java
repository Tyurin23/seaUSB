package ru.tyurin.seausb.sync;


import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Storage implements Serializable {

	private File path;
	private Map<Path, StorageElement> elements;

	public Storage(Path path) {
		this.path = path.toFile();
		elements = new HashMap<>();
	}

	public Path getPath() {
		return path.toPath();
	}

	public StorageElement get(Path path) {
		Path relative;
		if (path.isAbsolute()) {
			relative = this.path.toPath().relativize(path);
		} else {
			relative = path;
		}
		return elements.get(relative);
	}

	public StorageElement put(Path path, StorageElement element) {
		Path relative = this.path.toPath().relativize(path);
		return elements.put(relative, element);
	}

	public Set<Path> getDiff(Storage other) {
		Set<Path> diffs = new HashSet<>();
		for (Map.Entry<Path, StorageElement> entry : elements.entrySet()) {
			Object otherElement = other.get(entry.getKey());
			if (otherElement == null || !otherElement.equals(entry.getValue())) {
				diffs.add(path.toPath().resolve(entry.getKey()));
			}
		}
		return diffs;
	}

	public boolean equalsStorage(Storage other) {
		return getDiff(other).size() == 0;
	}

	public boolean isReady() {
		return path.exists() && path.canWrite();
	}


}
