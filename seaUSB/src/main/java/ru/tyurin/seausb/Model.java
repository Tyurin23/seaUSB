package ru.tyurin.seausb;


import ru.tyurin.seausb.sync.Task;

import java.util.HashMap;
import java.util.Map;

public class Model {

	private Map<Long, Task> tasks;
	private Long taskCounter = 0L;


	public Model() {
		tasks = new HashMap<>();
	}

	public Task getTask(Long id) {
		return tasks.get(id);
	}

	public Task createTask() {
		Task task = new Task(taskCounter++);
		tasks.put(task.getId(), task);
		return task;
	}
}
