package ru.tyurin.seausb;


import ru.tyurin.seausb.sync.Storage;
import ru.tyurin.seausb.sync.Synchronizer;
import ru.tyurin.seausb.sync.Task;

import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {

	private Model model;
	private View view;

	long counter = 0;

	public Controller(Model model) {
		if (model == null) {
			throw new NullPointerException("Model is null");
		}
		this.model = model;
	}

	public void setView(View view) {
		if (view == null) {
			throw new NullPointerException("View is null");
		}
		this.view = view;
	}

	public void addDestinationDirectory(Long id, Path dst) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		if (dst == null) {
			throw new NullPointerException("Directory is null");
		}
		if (!Files.isDirectory(dst)) {
			throw new IllegalArgumentException(dst + " is not directory");
		}
		if (!Files.isWritable(dst)) {
			throw new IllegalArgumentException(dst + " is not writable");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		Storage storage = new Storage(dst);
		task.setDst(storage);
		view.refreshTask(task);
	}

	public void addSourceDirectory(Long id, Path src) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		if (src == null) {
			throw new NullPointerException("Directory is null");
		}
		if (!Files.isDirectory(src)) {
			throw new IllegalArgumentException(src + " is not directory");
		}
		if (!Files.isDirectory(src)) {
			throw new IllegalArgumentException(src + " is not writable");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		task.setSrc(new Storage(src));
		view.refreshTask(task);
	}

	public void sync(Long id) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		Synchronizer sync = new Synchronizer();
		sync.synchronize(task);
	}

	public void addTask() {
		view.addTask(model.createTask());
	}

	public void hideWindow() {
		view.hide();
	}

	public void showWindow() {
		view.show();
	}

	public void exit() {
		System.exit(0);
	}

	private Long getID() {
		return counter++;
	}
}
