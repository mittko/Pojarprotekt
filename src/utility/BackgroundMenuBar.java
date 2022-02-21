package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;

public class BackgroundMenuBar extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color bgColor = null;
	
	public BackgroundMenuBar() {
		bgColor = getBackground();//Color.decode("0x678198");
	}
	public void setColor(Color color) {
		bgColor = color;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, 1200, 800);
	}
	
}
