package ru.tyurin.seausb;


import ru.tyurin.seausb.ui.SeaUI;

import javax.swing.*;

public class Sea {


	public static void createAndShowGUI() {
		try {
			SeaUI ui = new SeaUI();
		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	//todo error handler
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});

	}
}
