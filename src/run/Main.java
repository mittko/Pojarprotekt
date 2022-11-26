package run;

import enterdialog.PasswordComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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


