package ru.tyurin.seausb;


import ru.tyurin.seausb.sync.Task;

import java.io.File;

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

	public void addDirectory(Long id, File dir) {
		if (id == null) {
			throw new NullPointerException("Task ID is null");
		}
		if (dir == null) {
			throw new NullPointerException("Directory is null");
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dir + " is not directory");
		}
		if (!dir.canWrite()) {
			throw new IllegalArgumentException(dir + " is not writable");
		}
		Task task = model.getTask(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		view.refreshTask(task);
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
