package Exceptions;

import java.awt.Dimension;

import javax.swing.*;

public class ErrorDialog {
	public static void showErrorMessage(Exception string) {
		JTextArea ta = new JTextArea();
		ta.append(string.getMessage() +"\n");
		StackTraceElement[] stack = string.getStackTrace();
		for (StackTraceElement stackTraceElement : stack) {
			ta.append(" in " + stackTraceElement.getFileName() + " " +
					stackTraceElement.getClassName() + " " + stackTraceElement.getMethodName()
					+ "\n at line " + stackTraceElement.getLineNumber());
		}
		JScrollPane scroll = new JScrollPane(ta);
		 scroll.setPreferredSize(new Dimension(400,200));
		 JOptionPane.showMessageDialog(null, scroll,"Грешка",JOptionPane.ERROR_MESSAGE);
	}
	public static void showErrorMessage(String msg) {
		JLabel label = new JLabel();
		label.setText(msg);
		JOptionPane.showMessageDialog(null, label,"Грешка",JOptionPane.ERROR_MESSAGE);
	}
}
