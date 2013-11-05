package ru.tyurin.seausb.ui;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskPanel extends JPanel {


	private static final String DISC_IMAGE = "/img/hdd.png";
	private static final String FLASH_IMAGE = "/img/flash.png";
	private static final String SYNC_IMAGE = "/img/sync.png";
	private static final String OK_IMAGE = "/img/ok.png";

	protected static final int BORDER_THICKNESS_FOCUSED = 2;
	protected static final int BORDER_THICKNESS_NOT_FOCUSED = 1;


	protected static final Color BORDER_COLOR_NEW = Color.GREEN;
	protected static final Color BORDER_COLOR_ERROR = Color.RED;
	protected static final Color BORDER_COLOR_DIFF = Color.ORANGE;
	protected static final Color BORDER_COLOR_NO_DIFF = Color.BLACK;
	protected static final Color BORDER_COLOR_NO_DEVICE = Color.GRAY;

	protected static final String CHOOSE_OTHER_USB_COMMAND = "choose other usb";
	protected static final String SYNC_COMMAND = "sync";

	protected static final int MARGIN = 3;

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

	private final GridBagConstraints OK_BUTTON_CONSTRAINTS = new GridBagConstraints(
			3, 0,
			2, 2,
			1, 0.8,
			GridBagConstraints.EAST, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5),
			0, 0
	);

	private Presenter presenter;

	private JFileChooser fileChooser;
	private JLabel diskLabel;
	private JLabel flashLabel;
	private JButton syncButton;
	private JButton okButton;
	private JButton diskButton;
	private JButton flashButton;
	private JRadioButton autoRadio;
	private JPopupMenu flashChooseMenu;


	public TaskPanel(Presenter presenter) {
		super(new GridBagLayout());
		this.presenter = presenter;
		this.presenter.setComponent(this);
		addMouseListener(presenter);
		addFocusListener(presenter);

		setFocusable(true);
		setBackground(Color.WHITE);

		fileChooser = createFileChooser();
		diskButton = addDiscButton();
		diskLabel = addDiskLabel();
		flashButton = addFlashButton();
		flashLabel = addFlashLabel();
		autoRadio = addAutoRadio();
		autoRadio.setVisible(false);
		autoRadio.setFocusable(false);
		syncButton = addSyncButton();
		syncButton.setVisible(false);
		okButton = addOkButton();
		okButton.setEnabled(false);
		flashChooseMenu = addFlashChooseMenu();


		setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) getPreferredSize().getHeight()));

	}

	@Override
	public void setBorder(Border border) {
		Insets insets;
		if (border != null) {
			insets = border.getBorderInsets(this);
		} else {
			insets = new Insets(0, 0, 0, 0);
		}
		super.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(
								MARGIN - insets.top,
								MARGIN - insets.left,
								MARGIN - insets.bottom,
								MARGIN - insets.right),
						border
				)
		);

	}


	public JButton getDiskButton() {
		return diskButton;
	}

	public JButton getFlashButton() {
		return flashButton;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public JLabel getDiskLabel() {
		return diskLabel;
	}

	public JLabel getFlashLabel() {
		return flashLabel;
	}

	public JPopupMenu getFlashChooseMenu() {
		return flashChooseMenu;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getSyncButton() {
		return syncButton;
	}

	public JRadioButton getAutoRadio() {
		return autoRadio;
	}

	private JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		return fileChooser;
	}

	private JButton addDiscButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(DISC_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);
		add(button, DISC_IMAGE_CONSTRAINTS);
		return button;
	}

	private JButton addFlashButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(FLASH_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		final JButton button = createButton(img);
		add(button, FLASH_IMAGE_CONSTRAINTS);
		return button;
	}

	public JPopupMenu addFlashChooseMenu() {
		JPopupMenu usbChooseMenu = createPopupMenu();
		JMenuItem other = new JMenuItem("Other...");
		other.addActionListener(presenter);
		other.setActionCommand(CHOOSE_OTHER_USB_COMMAND);
		usbChooseMenu.add(other);
		usbChooseMenu.addSeparator();
		return usbChooseMenu;
	}

	private JLabel addDiskLabel() {
		JLabel label = createLabel();
		add(label, DISC_LABEL_CONSTRAINTS);
		return label;
	}

	private JLabel addFlashLabel() {
		JLabel label = createLabel();
		add(label, FLASH_LABEL_CONSTRAINTS);
		return label;
	}

	private JRadioButton addAutoRadio() {
		JRadioButton radio = createRadioButton();
		add(radio, AUTO_RADIO_CONSTRAINTS);
		return radio;
	}

	private JButton addSyncButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(SYNC_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);
		button.setActionCommand(SYNC_COMMAND);
		add(button, SYNC_BUTTON_CONSTRAINTS);
		return button;
	}

	private JButton addOkButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(OK_IMAGE)).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		JButton button = createButton(img);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		add(button, OK_BUTTON_CONSTRAINTS);
		return button;
	}

	private JPopupMenu createPopupMenu() {
		return initComponent(new JPopupMenu());
	}


	private JRadioButton createRadioButton() {
		return initComponent(new JRadioButton());
	}

	private JLabel createLabel() {
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(128, 16));
		return initComponent(label);
	}

	private JButton createButton(Image icon) {
		final JButton button = new JButton(new ImageIcon(icon));
		button.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		button.addActionListener(presenter);
		return initComponent(button);
	}

	private <E extends JComponent> E initComponent(E component) {
		component.addMouseListener(presenter);
		return component;
	}

}
