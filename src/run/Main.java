package run;

import enterdialog.PasswordComponent;

import javax.swing.*;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

    	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				JustFrame start = new JustFrame(new PasswordComponent());
				start.pack();
				start.setResizable(false);
				start.setFrameLocationOnTheCenter();
			}
		});


	}
}


