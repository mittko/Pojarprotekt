package acquittance.renderers;

import javax.swing.*;
import javax.swing.plaf.metal.MetalCheckBoxIcon;
import java.awt.*;

public class MyCustomMetalCheckBoxIcon2 extends MetalCheckBoxIcon {

	public MyCustomMetalCheckBoxIcon2() {
		
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
