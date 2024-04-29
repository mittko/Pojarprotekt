package invoice.renderers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

public class MyCustomMetalCheckBoxIcon extends MetalCheckBoxIcon {

	public MyCustomMetalCheckBoxIcon() {
		
	}
	
	@Override
	protected void drawCheck(Component c,Graphics g,int x,int y) {
		g.setColor(Color.decode("0x007F0E"));
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(7));
		super.drawCheck(c, g, x*2, y);
		}
	@Override
	protected int getControlSize() {
		  UIDefaults defs = UIManager.getDefaults();
		  Font temp = (Font) defs.get("CheckBox.font");
		  return temp.getSize() * 2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
