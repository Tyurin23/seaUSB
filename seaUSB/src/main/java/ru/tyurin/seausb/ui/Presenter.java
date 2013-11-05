package ru.tyurin.seausb.ui;


import javax.swing.*;
import java.awt.event.*;

public interface Presenter<E extends JComponent> extends
		ActionListener,
		MouseListener,
		MouseMotionListener,
		MouseWheelListener,
		FocusListener {
	public void setComponent(E component);
}
