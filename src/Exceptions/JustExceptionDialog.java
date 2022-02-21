package Exceptions;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JustExceptionDialog {
	public static void showException(Exception string) {
		JTextArea ta = new JTextArea();
		ta.append(string.getMessage() +"\n");
		StackTraceElement[] stack = string.getStackTrace();
		 for(int i = 0;i < stack.length;i++) {
    		 ta.append(" in "+stack[i].getFileName()+ " " +
    				 stack[i].getClassName()+ " "  + stack[i].getMethodName()
    				 +"\n at line " + stack[i].getLineNumber());
    	 }
		JScrollPane scroll = new JScrollPane(ta);
		 scroll.setPreferredSize(new Dimension(400,200));
		 JOptionPane.showMessageDialog(null, scroll,"Грешка",JOptionPane.ERROR_MESSAGE);
	}
}
