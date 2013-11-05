package ru.tyurin.seausb.ui.events;


import ru.tyurin.seausb.sync.Task;

public class TaskEvent {

	public enum Event {
		ADD,
		REMOVE,
		FOCUS
	}

	private Task task;
	private Event event;

	public TaskEvent(Task task, Event event) {
		this.task = task;
		this.event = event;
	}

	public Task getTask() {
		return task;
	}

	public Event getEvent() {
		return event;
	}
}
