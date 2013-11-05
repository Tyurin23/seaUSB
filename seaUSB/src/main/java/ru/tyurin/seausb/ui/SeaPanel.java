package ru.tyurin.seausb.ui;

import javax.swing.*;
import java.awt.*;

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
			5, 1,
			1, 0.8,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private Presenter presenter;

	private TaskListPanel taskListPanel;
	private JButton closeButton;
	private JButton hideButton;
	private JButton menuButton;
	private JButton removeButton;
	private JButton addButton;

	public SeaPanel(Presenter presenter) {
		super(new GridBagLayout());
		this.presenter = presenter;
		this.presenter.setComponent(this);

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);

		closeButton = addCloseButton();
		hideButton = addHideButton();
		menuButton = addMenuButton();
		menuButton.setEnabled(false);
		removeButton = addRemoveButton();
		removeButton.setEnabled(false);
		addButton = addAddButton();
	}

	public TaskListPanel getTaskListPanel() {
		return taskListPanel;
	}

	public void setTaskListPanel(TaskListPanel panel) {
		this.taskListPanel = addTaskListPanel(panel);
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public JButton getHideButton() {
		return hideButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JButton getMenuButton() {
		return menuButton;
	}

	public JButton getAddButton() {
		return addButton;
	}

	private JButton addHideButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(HIDE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		final JButton hide = createButton(icon);
		hide.setActionCommand(SeaPanelPresenter.HIDE_WINDOW_COMMAND);
		add(hide, HIDE_BUTTON_CONSTRAINTS);
		return hide;
	}

	private JButton addCloseButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(CLOSE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		final JButton close = createButton(icon);
		close.setActionCommand(SeaPanelPresenter.EXIT_COMMAND);
		add(close, CLOSE_BUTTON_CONSTRAINTS);
		return close;
	}

	private JButton addMenuButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(MENU_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton menu = createButton(icon);

		add(menu, MENU_BUTTON_CONSTRAINTS);
		return menu;
	}

	private JButton addRemoveButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(REMOVE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton remove = createButton(icon);
		remove.setActionCommand(SeaPanelPresenter.REMOVE_TASK_COMMAND);
		add(remove, REMOVE_BUTTON_CONSTRAINTS);
		return remove;
	}

	private JButton addAddButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ADD_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton add = createButton(icon);
		add.setActionCommand(SeaPanelPresenter.ADD_TASK_COMMAND);
		add(add, ADD_BUTTON_CONSTRAINTS);
		return add;
	}

	private JButton createButton(Image icon) {
		final JButton button = new JButton(new ImageIcon(icon));
		button.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		button.addActionListener(presenter);
		return button;
	}

	private TaskListPanel addTaskListPanel(TaskListPanel panel) {
		JScrollPane scrollPane = panel.getScrollPane();
		add(scrollPane, LIST_CONSTRAINTS);
		return panel;
	}
}
