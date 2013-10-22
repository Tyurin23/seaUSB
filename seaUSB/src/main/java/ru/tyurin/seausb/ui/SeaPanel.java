package ru.tyurin.seausb.ui;

import ru.tyurin.seausb.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeaPanel extends JPanel {

	private static final String HIDE_ICON = "/img/hide.png";
	private static final String CLOSE_ICON = "/img/close.png";
	private static final String MENU_ICON = "/img/menu.png";
	private static final String ADD_ICON = "/img/add.png";
	private static final String REMOVE_ICON = "/img/remove.png";

	private final GridBagConstraints CLOSE_BUTTON_CONSTRAINTS = new GridBagConstraints(
			4, 0,
			1, 1,
			0, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints HIDE_BUTTON_CONSTRAINTS = new GridBagConstraints(
			3, 0,
			1, 1,
			0, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints MENU_BUTTON_CONSTRAINTS = new GridBagConstraints(
			2, 0,
			1, 1,
			0, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints REMOVE_BUTTON_CONSTRAINTS = new GridBagConstraints(
			1, 0,
			1, 1,
			0, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints ADD_BUTTON_CONSTRAINTS = new GridBagConstraints(
			0, 0,
			1, 1,
			1, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints LIST_CONSTRAINTS = new GridBagConstraints(
			0, 1,
			4, 1,
			1, 0.8,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private Controller controller;

	private TaskListPanel taskListPanel;

	public SeaPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);
		addCloseButton();
		addHideButton();
		addMenuButton();
		addRemoveButton();
		addAddButton();

		taskListPanel = addTaskListPanel();
	}

	public TaskListPanel getTaskListPanel() {
		return taskListPanel;
	}

	private JButton addHideButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(HIDE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		final JButton hide = createButton(icon);

		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.hideWindow();
			}
		});


		add(hide, HIDE_BUTTON_CONSTRAINTS);
		return hide;
	}

	private JButton addCloseButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(CLOSE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		final JButton close = createButton(icon);

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});


		add(close, CLOSE_BUTTON_CONSTRAINTS);
		return close;
	}

	private JButton addMenuButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(MENU_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton menu = createButton(icon);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		add(menu, MENU_BUTTON_CONSTRAINTS);
		return menu;
	}

	private JButton addRemoveButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(REMOVE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton remove = createButton(icon);
		add(remove, REMOVE_BUTTON_CONSTRAINTS);
		return remove;
	}

	private JButton addAddButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ADD_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton add = createButton(icon);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addTask();
			}
		});
		add(add, ADD_BUTTON_CONSTRAINTS);
		return add;
	}

	private JButton createButton(Image icon) {
		final JButton button = new JButton(new ImageIcon(icon));
		button.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		return button;
	}

	private TaskListPanel addTaskListPanel() {
		TaskListPanel panel = new TaskListPanel(controller);
		JScrollPane scrollPane = panel.getScrollPane();
		add(scrollPane, LIST_CONSTRAINTS);
		return panel;
	}
}
