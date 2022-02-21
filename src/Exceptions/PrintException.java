package Exceptions;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PrintException extends Exc {
	public static void Print_Exception(Exception ex) {
		JTextArea messager = new JTextArea();
		messager.append(ex.getMessage() +"\n");
		StackTraceElement[] stack = ex.getStackTrace();
		for(int i = 0;i < stack.length;i++) {
    		 messager.append(" in "+stack[i].getFileName()+ " " +
    				 stack[i].getClassName()+ " "  + stack[i].getMethodName()
    				 +"\n at line " + stack[i].getLineNumber());
    	 }
		 JScrollPane scroll = new JScrollPane(messager);
		 scroll.setPreferredSize(new Dimension(500,200));
			 JOptionPane.showMessageDialog(null, scroll,"Грешка",JOptionPane.ERROR_MESSAGE);
		  Beep();
	}
	

}
