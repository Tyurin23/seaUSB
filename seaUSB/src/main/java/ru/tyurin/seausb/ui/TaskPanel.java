package ru.tyurin.seausb.ui;


import ru.tyurin.seausb.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TaskPanel extends JPanel {

	private static final String DISC_IMAGE = "/img/hdd.png";
	private static final String FLASH_IMAGE = "/img/flash.png";
	private static final String SYNC_IMAGE = "/img/sync.png";

	private final GridBagConstraints DISC_IMAGE_CONSTRAINTS = new GridBagConstraints(
			0, 0,
			1, 1,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints DISC_LABEL_CONSTRAINTS = new GridBagConstraints(
			1, 0,
			1, 1,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints FLASH_IMAGE_CONSTRAINTS = new GridBagConstraints(
			0, 1,
			1, 1,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints FLASH_LABEL_CONSTRAINTS = new GridBagConstraints(
			1, 1,
			1, 1,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints AUTO_RADIO_CONSTRAINTS = new GridBagConstraints(
			3, 0,
			1, 2,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private final GridBagConstraints SYNC_BUTTON_CONSTRAINTS = new GridBagConstraints(
			4, 0,
			1, 2,
			0.1, 0,
			GridBagConstraints.NORTH, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private Long id;
	private Controller controller;
	private JFileChooser fileChooser;
	private JLabel diskLabel;
	private JLabel flashLabel;


	public TaskPanel(Controller controller, Long id) {
		super(new GridBagLayout());
		this.controller = controller;
		this.id = id;

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.WHITE);
		setFocusable(true);

		fileChooser = createFileChooser();
		addDiscButton();
		diskLabel = addDiskLabel();
		addFlashButton();
		flashLabel = addFlashLabel();
		addAutoRadio();
		addSyncButton();

	}

	public Long getId() {
		return id;
	}

	private JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		return fileChooser;
	}

	private JButton addDiscButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(DISC_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showDialog(null, "OK");
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.addDirectory(id, file);
				}
			}
		});
		add(button, DISC_IMAGE_CONSTRAINTS);
		return button;
	}

	private JButton addFlashButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(FLASH_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showDialog(null, "OK");
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					flashLabel.setText(file.getName());
				}
			}
		});
		add(button, FLASH_IMAGE_CONSTRAINTS);
		return button;
	}

	private JLabel addDiskLabel() {
		JLabel label = new JLabel();
		add(label, DISC_LABEL_CONSTRAINTS);
		return label;
	}

	private JLabel addFlashLabel() {
		JLabel label = new JLabel();
		add(label, FLASH_LABEL_CONSTRAINTS);
		return label;
	}

	private JRadioButton addAutoRadio() {
		JRadioButton radio = new JRadioButton();
		add(radio, AUTO_RADIO_CONSTRAINTS);
		return radio;
	}

	private JButton addSyncButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(SYNC_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);

		add(button, SYNC_BUTTON_CONSTRAINTS);
		return button;
	}

	private JButton createButton(Image icon) {
		final JButton button = new JButton(new ImageIcon(icon));
		button.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		return button;
	}

}
