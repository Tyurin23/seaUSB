package ru.tyurin.seausb.ui;


import com.google.common.eventbus.Subscribe;
import ru.tyurin.seausb.Model;
import ru.tyurin.seausb.sync.Task;
import ru.tyurin.seausb.ui.events.TaskEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SeaPanelPresenter extends ComponentPresenter<SeaPanel> {

	protected static final String ADD_TASK_COMMAND = "add_task";
	protected static final String REMOVE_TASK_COMMAND = "remove_task";
	protected static final String EXIT_COMMAND = "exit";
	protected static final String HIDE_WINDOW_COMMAND = "hide";


	private Model model;
	private Task focused;

	public SeaPanelPresenter(Model model) {
		this.model = model;
		this.model.getEventBus().register(this);
	}

	@Override
	protected void init() {
		TaskListPanelPresenter presenter = new TaskListPanelPresenter(model);
		TaskListPanel panel = new TaskListPanel(presenter);
		component.setTaskListPanel(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(ADD_TASK_COMMAND)) {
			addTask();
		} else if (command.equals(EXIT_COMMAND)) {
			exit();
		} else if (command.equals(HIDE_WINDOW_COMMAND)) {

		} else if (command.equals(REMOVE_TASK_COMMAND)) {
			removeTask();
		}
	}

	public void setFocused(Task focused) {
		this.focused = focused;
		if (focused != null) {
			component.getRemoveButton().setEnabled(true);
		} else {
			component.getRemoveButton().setEnabled(false);
		}
	}


	private void addTask() {
		if (model.hasNew() == null) {
			model.createTask();
		}
	}

	private void removeTask() {

		if (focused != null) {
			System.out.println("remove1");
			int result = JOptionPane.showConfirmDialog(component, "Are you sure?", "Remove task", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				boolean isRemoved = model.removeTask(focused);
				if (isRemoved) {
					setFocused(null);
					System.out.println("removed2");
				}
			}
		}
	}


	private void hideWindow() {
		//todo bus hide
	}

	private void exit() {
		System.exit(0);
	}

	@Subscribe
	public void taskFocusEvent(TaskEvent event) {
		if (event.getEvent() == TaskEvent.Event.FOCUS) {
			setFocused(event.getTask());
		}
	}


}
