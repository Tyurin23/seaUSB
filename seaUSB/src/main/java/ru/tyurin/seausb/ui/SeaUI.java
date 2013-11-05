package ru.tyurin.seausb.ui;


import ru.tyurin.seausb.Model;

import javax.swing.*;
import java.awt.*;

public class SeaUI extends JFrame {

	private static final String TITLE = "SeaUSB";
	private static final String TRAY_ICON_PATH = "/img/tray.png";
	private static final int WINDOW_WIDHT = 300;
	private static final int WINDOW_HEIGHT = 200;


	private SeaPanel panel;
	private Model model;


	public SeaUI() throws Exception {
		super(TITLE);
		model = new Model();
		this.panel = addSeaPanel();
		setResizable(false);
		setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		createTray();
		setUndecorated(true);
		setVisible(true);
	}

	private SeaPanel addSeaPanel() {
		SeaPanelPresenter presenter = new SeaPanelPresenter(model);
		SeaPanel panel = new SeaPanel(presenter);
		setContentPane(panel);
		return panel;
	}

	//todo if tray is not supported???
	private void createTray() throws Exception {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(TRAY_ICON_PATH));

			final TrayIcon icon = new TrayIcon(img, "Sync your USB with SEA-USB! :)");
			icon.setImageAutoSize(true);
			// todo show window
			tray.add(icon);
		} else {
			throw new Exception("Tray not supported");
		}
	}
}
