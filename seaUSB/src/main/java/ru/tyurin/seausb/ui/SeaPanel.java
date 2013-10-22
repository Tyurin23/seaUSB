package ru.tyurin.seausb.ui;

import javax.swing.*;
import java.awt.*;

public class SeaPanel extends JPanel {

	private static final String HIDE_ICON = "/img/close.png";
	private static final String MENU_ICON = "/img/menu.png";
	private static final String ADD_ICON = "/img/add.png";
	private static final String REMOVE_ICON = "img/remove.png";


	private final GridBagConstraints HIDE_BUTTON_CONSTRAINTS = new GridBagConstraints(
			3, 0,
			1, 1,
			0, 0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0),
			10, 10
	);

	private final GridBagConstraints MENU_BUTTON_CONSTRAINTS = new GridBagConstraints(
			2, 0,
			1, 1,
			0, 1,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0),
			0, 0
	);

	private final GridBagConstraints REMOVE_BUTTON_CONSTRAINTS = new GridBagConstraints(
			1, 0,
			1, 1,
			0, 1,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0),
			0, 0
	);

	private final GridBagConstraints ADD_BUTTON_CONSTRAINTS = new GridBagConstraints(
			0, 0,
			1, 1,
			1, 1,
			GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0),
			0, 0
	);


	public SeaPanel() {
		super(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		addHideButton();
		addMenuButton();
		addRemoveButton();
		addAddButton();
	}

	private JButton addHideButton() {
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(HIDE_ICON)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton hide = new JButton(new ImageIcon(icon));
		hide.setBorder(BorderFactory.createEmptyBorder());
		hide.setContentAreaFilled(false);
		add(hide, HIDE_BUTTON_CONSTRAINTS);
		return hide;
	}

	private JButton addMenuButton() {
		JButton menu = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MENU_ICON)));
		add(menu, MENU_BUTTON_CONSTRAINTS);
		return menu;
	}

	private JButton addRemoveButton() {
		JButton remove = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(REMOVE_ICON)));
		add(remove, REMOVE_BUTTON_CONSTRAINTS);
		return remove;
	}

	private JButton addAddButton() {
		JButton add = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ADD_ICON)));
		add(add, ADD_BUTTON_CONSTRAINTS);
		return add;
	}
}
