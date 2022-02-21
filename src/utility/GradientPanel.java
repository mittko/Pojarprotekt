package utility;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GradientPanel extends MainPanel {

	public GradientPanel() {
		
	}
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = getBackground();
        Color color2 = color1.darker();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
            0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        
        g2d.fillRect(0, 0, w, h);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
   //     Framer f = new Framer(new GradientPanel());
	}

}
