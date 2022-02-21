package Exceptions;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PDFException {

	public static void showPDFException(Exception e) {
		JTextArea ta = new JTextArea();
		ta.append(e.getMessage() +"\n");
		StackTraceElement[] stack = e.getStackTrace();
		 for(int i = 0;i < stack.length;i++) {
    		 ta.append(" in "+stack[i].getFileName()+ " " +
    				 stack[i].getClassName()+ " "  + stack[i].getMethodName()
    				 +"\n at line " + stack[i].getLineNumber());
    	 }
		JScrollPane scroll = new JScrollPane(ta);
		 scroll.setPreferredSize(new Dimension(400,200));
		 JOptionPane.showMessageDialog(null, scroll,"Грешка",JOptionPane.ERROR_MESSAGE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	}

}
