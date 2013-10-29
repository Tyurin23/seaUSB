package ru.tyurin.seausb;


import ru.tyurin.seausb.sync.Task;
import ru.tyurin.seausb.ui.SeaUI;
import ru.tyurin.seausb.ui.TaskListPanel;
import ru.tyurin.seausb.ui.TaskPanel;

public class View {

	private SeaUI ui;
	private Controller controller;

	public View(Controller controller) throws Exception {
		this.ui = new SeaUI(controller);
		this.controller = controller;
	}

	public void addTask(Task task) {
		TaskPanel taskPanel = new TaskPanel(controller, task.getId());
		TaskListPanel taskList = ui.getPanel().getTaskListPanel();
		taskList.addTask(taskPanel);
	}

	public void refreshTask(Task task) {
		TaskListPanel taskList = ui.getPanel().getTaskListPanel();
		TaskPanel panel = taskList.getTaskPanel(task.getId());
		panel.setFlashLabelText((task.getFlash() != null ? task.getFlash().toString() : ""));
		panel.setDiscLabelText(task.getDisk() != null ? task.getDisk().toString() : "");
	}

	public void hide() {
		ui.setVisible(false);
	}

	public void show() {
		ui.setVisible(true);
	}
}
