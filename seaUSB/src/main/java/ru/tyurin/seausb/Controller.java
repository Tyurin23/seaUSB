package ru.tyurin.seausb;


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

	public void addDirectory(Long id, Path dir) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		if (dir == null) {
			throw new NullPointerException("Directory is null");
		}
		if (!Files.isDirectory(dir)) {
			throw new IllegalArgumentException(dir + " is not directory");
		}
		if (!Files.isWritable(dir)) {
			throw new IllegalArgumentException(dir + " is not writable");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		task.setDisk(dir);
		view.refreshTask(task);
	}

	public void addFlashDirectory(Long id, Path flash) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		if (flash == null) {
			throw new NullPointerException("Directory is null");
		}
		if (!Files.isDirectory(flash)) {
			throw new IllegalArgumentException(flash + " is not directory");
		}
		if (!Files.isDirectory(flash)) {
			throw new IllegalArgumentException(flash + " is not writable");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		task.setFlash(flash);
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
