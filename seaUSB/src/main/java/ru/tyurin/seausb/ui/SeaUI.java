package ru.tyurin.seausb.ui;


import ru.tyurin.seausb.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeaUI extends JFrame {

	private static final String TITLE = "SeaUSB";
	private static final String TRAY_ICON_PATH = "/img/tray.png";
	private static final int WINDOW_WIDHT = 300;
	private static final int WINDOW_HEIGHT = 200;

	private Controller controller;

	private SeaPanel panel;


	public SeaUI(Controller controller) throws Exception {
		super(TITLE);
		this.controller = controller;
		this.panel = new SeaPanel(controller);
		setResizable(false);
		setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		createTray();
		setUndecorated(true);
		setContentPane(this.panel);
		setVisible(true);
	}

	public SeaPanel getPanel() {
		return panel;
	}

	private void createTray() throws Exception {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(TRAY_ICON_PATH));

			final TrayIcon icon = new TrayIcon(img, "Sync your USB with SEA-USB! :)");
			icon.setImageAutoSize(true);
			icon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.showWindow();
				}
			});
			tray.add(icon);
		} else {
			throw new Exception("Tray not supported");
		}
	}
}
