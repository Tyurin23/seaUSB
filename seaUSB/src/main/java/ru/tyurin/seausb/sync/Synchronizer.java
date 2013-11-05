package ru.tyurin.seausb.sync;


import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.zip.CRC32;


public class Synchronizer extends Thread {

	private Task task;
	private FutureStatus status;

	public Synchronizer(Task task) {
		this.task = task;
		this.status = new FutureStatus();
	}

	public FutureStatus getStatus() {
		return status;
	}

	private boolean synchronize(final Storage src, final Storage dst) throws IOException {
		System.out.println("Indexing...");
		index(src);
		index(dst);
		status.setStatus(30);
		System.out.println("Diffing...");
		Set<Path> srcDiff = src.getDiff(dst);
		Set<Path> dstDiff = dst.getDiff(src);
		status.setStatus(60);
		System.out.println("Copying...");
		copy(srcDiff, src.getPath(), dst.getPath());
		copy(dstDiff, dst.getPath(), src.getPath());
		status.setStatus(90);
		return isEqualsDir(src.getPath(), dst.getPath());
	}

	public static void index(final Storage storage) throws IOException {
		Files.walkFileTree(storage.getPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if (attrs.isSymbolicLink() || Files.isHidden(dir)) {
					return FileVisitResult.SKIP_SUBTREE;
				} else {
					if (!dir.equals(storage.getPath())) {
						StorageElement element = StorageElement.directory();
						element.setHash(getHash(attrs.size() + " " + attrs.lastModifiedTime()));
						element.setSize(attrs.size());
						element.setDateModified(attrs.lastModifiedTime());
						storage.put(dir, element);
					}
					return FileVisitResult.CONTINUE;
				}
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!Files.isHidden(file) && !Files.isDirectory(file)) {
					System.out.println(file);
					StorageElement element = StorageElement.file();
					element.setHash(getHash(file));
					element.setSize(attrs.size());
					element.setDateModified(attrs.lastModifiedTime());
					storage.put(file, element);
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}


	private void copy(Set<Path> files, Path src, Path dst) throws IOException {
		for (Path file : files) {
			Path relative = src.relativize(file);
			if (relative.getParent() != null) {
				Files.createDirectories(dst.resolve(relative.getParent()));
			}
			Files.copy(file, dst.resolve(relative), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private int compare(Path src, Path dst) throws IOException {
		if (Files.notExists(dst)) {
			return 1;
		}
		if (!com.google.common.io.Files.asByteSource(src.toFile()).contentEquals(com.google.common.io.Files.asByteSource(dst.toFile()))) {
			return Files.getLastModifiedTime(src).compareTo(Files.getLastModifiedTime(dst));
		} else {
			return 0;
		}
	}

	private boolean isEqualsDir(Path src, Path dst) throws IOException {
		if (Files.exists(src) && Files.exists(dst) &&
//				Files.size(src) == Files.size(dst) &&
				Files.getLastModifiedTime(src).compareTo(Files.getLastModifiedTime(dst)) == 0) {
			return true;
		} else {
			return false;
		}
	}

	private void copy(Path from, Path to) throws IOException {
		Files.createDirectories(to.getParent());
		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	}

	private static long getHash(Path file) throws IOException {
		return FileUtils.checksumCRC32(file.toFile());
	}

	private static long getHash(String str) {
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		return crc32.getValue();
	}

	@Override
	public void run() {
		try {
			synchronize(task.getSrc(), task.getDst());
			status.setStatus(100);
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
