package ru.tyurin.seausb.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;


public class TaskListPanel extends JPanel {

	Map<Long, TaskPanel> taskPanels;
	private JScrollPane scrollPane;
	private Presenter presenter;

	public TaskListPanel(Presenter presenter) {
		this.presenter = presenter;
		presenter.setComponent(this);

		taskPanels = new HashMap<>();
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);


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

}
