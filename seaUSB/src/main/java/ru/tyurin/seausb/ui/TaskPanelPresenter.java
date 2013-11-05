package ru.tyurin.seausb.ui;


import ru.tyurin.seausb.Model;
import ru.tyurin.seausb.sync.FutureStatus;
import ru.tyurin.seausb.sync.Storage;
import ru.tyurin.seausb.sync.Task;
import ru.tyurin.seausb.ui.events.TaskEvent;
import ru.tyurin.seausb.util.USBDrive;
import ru.tyurin.seausb.util.USBTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class TaskPanelPresenter extends ComponentPresenter<TaskPanel> {

	private static final String UPDATE_COMMAND = "update";

	private Model model;
	private Task task;
	private boolean focus;
	private boolean hover;
	Timer timer;

	public TaskPanelPresenter(Model model, Task task) {
		this.model = model;
		this.task = task;
		this.focus = false;
		this.hover = false;
		timer = new Timer(50, this);
		timer.setActionCommand(UPDATE_COMMAND);
		timer.start();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		component.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		hover = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
	}

	@Override
	public void focusGained(FocusEvent e) {
		focus = true;
		model.getEventBus().post(new TaskEvent(task, TaskEvent.Event.FOCUS));
	}

	@Override
	public void focusLost(FocusEvent e) {
		focus = false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		Object source = e.getSource();
		if (command.equals(TaskPanel.CHOOSE_OTHER_USB_COMMAND)) {
			chooseTaskUsb();
		} else if (command.equals(TaskPanel.SYNC_COMMAND)) {
			sync();
		} else if (command.equals(UPDATE_COMMAND)) {
			update();
		}
		if (source == component.getDiskButton()) {
			chooseTaskFolder();
		} else if (source == component.getFlashButton()) {
			showUsbChooseMenu();
		} else if (source == component.getOkButton()) {
			saveTask();
		}
	}


	private void statusReady() {
		component.getOkButton().setEnabled(true);
	}


	private void statusNew() {

	}

	private void statusNotSync() {
		component.getOkButton().setEnabled(false);
		component.getOkButton().setVisible(false);
		component.getSyncButton().setVisible(true);
		component.getAutoRadio().setVisible(true);
	}

	private void sync() {
		System.out.println("sync");
		FutureStatus status = task.synchronize();
		while (status.getStatus() < 100) {
			System.out.println(status.getStatus());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
	}

	private void chooseTaskFolder() {
		File file = getFileFromChooser();
		if (file != null) {
			Storage storage = new Storage(file.toPath());
			task.setDst(storage);
			update();
		}
	}

	private void chooseTaskUsb() {
		File file = getFileFromChooser();
		if (file != null) {
			Storage storage = new Storage(file.toPath());
			task.setSrc(storage);
			update();
		}
	}

	private File getFileFromChooser() {
		int result = component.getFileChooser().showDialog(null, "OK");
		if (result == JFileChooser.APPROVE_OPTION) {
			return component.getFileChooser().getSelectedFile();
		}
		return null;
	}

	// todo тест отключение usb во время вызова меню
	private void showUsbChooseMenu() {
		JButton button = component.getFlashButton();
		JPopupMenu menu = component.getFlashChooseMenu();
		updateUsbChooseMenu(menu);
		menu.show(button, button.getWidth(), button.getHeight());
	}

	// todo одинаковые папки
	// todo
	private void updateUsbChooseMenu(JPopupMenu menu) {
		for (MenuElement element : menu.getSubElements()) {
			JMenuItem item = (JMenuItem) element;
			if (menu.getComponentIndex(item) == 0) {
				continue;
			}
			USBDrive drive = USBTool.find(item.getName());
			if (drive == null) {
				menu.remove(item);
			}
		}
	}

	private void saveTask() {
		try {
			model.saveTask(task);
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		statusNotSync();
		try {
			task.index();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("updated");

	}


	private void update() {
		updateDiskLabel();
		updateFlashLabel();
		updateOkButton();
		updateBorder();
	}

	private void updateDiskLabel() {
		String labelText = task.getDst() != null ? task.getDst().getPath().getFileName().toString() : "";
		String labelToolTip = task.getDst() != null ? task.getDst().getPath().toString() : "";
		setLabelText(component.getDiskLabel(), labelText, 100);
		component.getDiskLabel().setToolTipText(labelToolTip);
	}

	private void updateFlashLabel() {
		String labelText = task.getSrc() != null ? task.getSrc().getPath().getFileName().toString() : "";
		String labelToolTip = task.getSrc() != null ? task.getSrc().getPath().toString() : "";
		setLabelText(component.getFlashLabel(), labelText, 100);
		component.getFlashLabel().setToolTipText(labelToolTip);
	}

	private void updateOkButton() {
		JButton okButton = component.getOkButton();
		if (task.isSaved()) {
			okButton.setEnabled(false);
			okButton.setVisible(false);
		} else {
			okButton.setVisible(true);
			if (task.isReady()) {
				okButton.setEnabled(true);
			} else {
				okButton.setEnabled(false);
			}
		}
	}

	private void updateBorder() {
		int thickness = TaskPanel.BORDER_THICKNESS_NOT_FOCUSED;
		Color color = TaskPanel.BORDER_COLOR_NO_DIFF;
		if (focus || hover) {
			thickness = TaskPanel.BORDER_THICKNESS_FOCUSED;
		}
		if (!task.isSaved()) {
			color = TaskPanel.BORDER_COLOR_NEW;
		} else {
			if (task.isDifferent()) {
				color = TaskPanel.BORDER_COLOR_DIFF;
			}
		}
		component.setBorder(BorderFactory.createLineBorder(color, thickness));
	}

	private void setLabelText(JLabel label, String text, int maxLength) {
		if (text.length() > maxLength) {
			text = text.substring(0, maxLength);
		}
		label.setText(text);
	}

}
