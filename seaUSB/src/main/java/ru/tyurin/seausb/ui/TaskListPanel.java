package ru.tyurin.seausb.ui;

import ru.tyurin.seausb.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;


public class TaskListPanel extends JPanel {

	Map<Long, TaskPanel> taskPanels;
	private Controller controller;
	private JScrollPane scrollPane;

	public TaskListPanel(Controller controller) {
		taskPanels = new HashMap<>();
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);

		this.controller = controller;

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(layout);

		scrollPane = new JScrollPane(this);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + e.getWheelRotation() * 20);
			}
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void addTask(TaskPanel task) {
		Dimension size = new Dimension(getWidth(), getHeight() / 3);
		task.setSize(size);
		task.setMaximumSize(size);
		task.setMaximumSize(size);
		add(task);
		revalidate();
		getScrollPane().revalidate();
		taskPanels.put(task.getId(), task);
	}

	public TaskPanel getTaskPanel(Long id) {
		return taskPanels.get(id);
	}
}
