package ru.tyurin.seausb.ui;


import com.google.common.eventbus.Subscribe;
import ru.tyurin.seausb.Model;
import ru.tyurin.seausb.sync.Task;
import ru.tyurin.seausb.ui.events.TaskEvent;

import java.util.HashMap;
import java.util.Map;

public class TaskListPanelPresenter extends ComponentPresenter<TaskListPanel> {

	private Model model;
	private Map<Task, TaskPanel> taskPanels;

	public TaskListPanelPresenter(Model model) {
		this.model = model;
		this.model.getEventBus().register(this);
		taskPanels = new HashMap<>();
	}

	public void update() {
		for (Task task : model.getTasks()) {
			if (getTaskPanel(task) == null) {
				addTask(task);
			}
		}
	}

	public void addTask(Task task) {
		TaskPanelPresenter presenter = new TaskPanelPresenter(model, task);
		TaskPanel taskPanel = new TaskPanel(presenter);
		component.add(taskPanel);
		component.revalidate();
		taskPanel.requestFocus();
		taskPanels.put(task, taskPanel);
	}

	public TaskPanel getTaskPanel(Task task) {
		return taskPanels.get(task);
	}

	@Subscribe
	public void tasksChanged(TaskEvent e) {
		if (e.getEvent() == TaskEvent.Event.ADD) {
			if (getTaskPanel(e.getTask()) == null) {
				addTask(e.getTask());
			}
		} else if (e.getEvent() == TaskEvent.Event.REMOVE) {
			TaskPanel panel = getTaskPanel(e.getTask());
			component.remove(panel);
			taskPanels.remove(panel);
			component.repaint();
		}
	}
}
