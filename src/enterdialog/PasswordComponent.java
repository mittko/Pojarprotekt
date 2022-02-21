package enterdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import utility.MainPanel;

public class PasswordComponent extends MainPanel {
	// private Image img = null;
	private final JPanel north = new JPanel();
	private final JPanel east = new JPanel();
	private final JPanel west = new JPanel();
	private final JPanel south = new JPanel();

	public PasswordComponent() {

		this.setLayout(new BorderLayout());

		PasswordDialog front = new PasswordDialog();

		this.add(front, BorderLayout.CENTER);
		// north.setPreferredSize(new Dimension(this.WIDTH-20,this.HEIGHT/2
		// +10));
		north.setOpaque(false);
		this.add(north, BorderLayout.NORTH);
		// south.setPreferredSize(new Dimension(this.WIDTH - 20, this.HEIGHT / 3
		// - 60));
		this.add(south, BorderLayout.SOUTH);
		south.setOpaque(false);
		// east.setPreferredSize(new Dimension(this.WIDTH / 8, this.HEIGHT));
		east.setOpaque(false);
		this.add(east, BorderLayout.EAST);
		// west.setPreferredSize(new Dimension(this.WIDTH / 8, this.HEIGHT));
		west.setOpaque(false);
		this.add(west, BorderLayout.WEST);

		// this.setPreferredSize(new Dimension(this.WIDTH - 20, this.HEIGHT -
		// 90));
	}

	/*
	 * @Override public void paintComponent(Graphics g) {
	 * super.paintComponent(g);
	 * 
	 * g.drawImage(img,this.WIDTH/4,(this.HEIGHT/3) + 30,100,100,null); }
	 */

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
