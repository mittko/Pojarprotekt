package exceptions;

import utils.MainPanel;

import javax.swing.*;
import java.awt.*;

public class Exc extends MainPanel {

	public Exc() {
		setProperties();
		
	}
	public static void Beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public static void showErrorMessage(String header, Exception ex) {
		JTextArea messager = new JTextArea();
		messager.append(ex.getMessage() +"\n");
		StackTraceElement[] stack = ex.getStackTrace();
		for (StackTraceElement stackTraceElement : stack) {
			messager.append(" in " + stackTraceElement.getFileName() + " " +
					stackTraceElement.getClassName() + " " + stackTraceElement.getMethodName()
					+ "\n at line " + stackTraceElement.getLineNumber());
		}
		JScrollPane scroll = new JScrollPane(messager);
		scroll.setPreferredSize(new Dimension(400,200));
		JOptionPane.showMessageDialog(null, scroll,header,JOptionPane.ERROR_MESSAGE);

	}
	public static void showErrorMessage(Exception exception) {
		showErrorMessage("Грешка",exception);
	}
	public static void showErrorMessage(String msg) {
		JLabel label = new JLabel();
		label.setText(msg);
		JOptionPane.showMessageDialog(null, label,"Грешка",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
