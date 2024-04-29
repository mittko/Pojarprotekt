package enterdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import utils.MainPanel;

public class PasswordComponent extends MainPanel {

	public PasswordComponent() {

		this.setLayout(new BorderLayout());

		PasswordDialog front = new PasswordDialog();

		this.add(front, BorderLayout.CENTER);

		JPanel north = new JPanel();
		north.setOpaque(false);
		this.add(north, BorderLayout.NORTH);

		JPanel south = new JPanel();
		this.add(south, BorderLayout.SOUTH);
		south.setOpaque(false);

		JPanel east = new JPanel();
		east.setOpaque(false);
		this.add(east, BorderLayout.EAST);

		JPanel west = new JPanel();
		west.setOpaque(false);
		this.add(west, BorderLayout.WEST);

	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Framer f = new Framer(new PasswordChecker());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// beauty fire gradient background
		Graphics2D g2d = (Graphics2D) g;
		Color color1 = Color.decode("0xFF2014");
		Color color2 = Color.decode("0xFF1C60");// ("0xFFD800");
		int w = getWidth();
		int h = getHeight();
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}
}
