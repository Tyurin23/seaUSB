package ru.tyurin.seausb.sync;


import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Storage<E> {

	private Path path;
	private Map<Path, E> elements;

	public Storage(Path path) {
		this.path = path;
		elements = new HashMap<>();

	}

	public Path getPath() {
		return path;
	}

	public E get(Path path) {
		Path relative;
		if (path.isAbsolute()) {
			relative = this.path.relativize(path);
		} else {
			relative = path;
		}
		return elements.get(relative);
	}

	public E put(Path path, E element) {
		Path relative = this.path.relativize(path);
		return elements.put(relative, element);
	}

	public Set<Path> getDiff(Storage<E> other) {
		Set<Path> diffs = new HashSet<>();
		for (Map.Entry<Path, E> entry : elements.entrySet()) {
			Object otherElement = other.get(entry.getKey());
			if (otherElement == null || !otherElement.equals(entry.getValue())) {
				diffs.add(path.resolve(entry.getKey()));
			}
		}
		return diffs;
	}
}
