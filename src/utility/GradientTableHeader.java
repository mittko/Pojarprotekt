package utility;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.table.JTableHeader;

public class GradientTableHeader extends JTableHeader {
	   GradientPaint gp = null;
	   int w = 0;
	   int h = 0;
	   
      public GradientTableHeader() {
    	  Color color1 = getBackground();
          Color color2 = color1.darker();
          w = getWidth();
          h = getHeight();
         gp = new GradientPaint(
              0, 0, color1, 0, h, color2);
      }

	   @Override
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setPaint(gp);
	      g2.fillRect(0, 0, w, h);
	   }
	}

