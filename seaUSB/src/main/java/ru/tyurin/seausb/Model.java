package ru.tyurin.seausb;


import com.google.common.eventbus.EventBus;
import ru.tyurin.seausb.sync.Task;
import ru.tyurin.seausb.ui.events.TaskEvent;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Model {

	private static final String DATA_FILE = "data.db";

	private Map<Long, Task> tasks;
	private Long taskCounter = 0L;
	private EventBus eventBus;


	public Model() {
		tasks = new HashMap<>();
		eventBus = new EventBus();
		try {
			loadTasks();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public Task getTask(Long id) {
		return tasks.get(id);
	}


	public Collection<Task> getTasks() {
		return tasks.values();
	}

	public Task createTask() {
		Task task = new Task(taskCounter++);
		tasks.put(task.getId(), task);
		eventBus.post(new TaskEvent(task, TaskEvent.Event.ADD));
		return task;
	}

	public boolean removeTask(Task task) {
		Task removed = tasks.remove(task.getId());
		if (removed != null) {
			eventBus.post(new TaskEvent(removed, TaskEvent.Event.REMOVE));
			return true;
		} else {
			return false;
		}
	}

	public void saveTask(Task task) throws IOException {
		task.setSaved(true);
		File data = new File(DATA_FILE);
		if (!data.exists()) {
			data.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(data));
		out.writeObject(tasks);
		out.flush();
		out.close();
		//todo
	}

	public void loadTasks() throws IOException, ClassNotFoundException {
		File data = new File(DATA_FILE);
		if (!data.exists()) {
			return;
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(data));
		Map<Long, Task> tasks = (Map<Long, Task>) in.readObject();
		in.close();
		//todo
	}


	public Task hasNew() {
		for (Task task : tasks.values()) {
			if (task.isSaved()) {
				return task;
			}
		}
		return null;
	}

}
