package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;

import run.JustFrame;

public class OvalButton extends JButton {

	Shape shape = null;
	int a = 100;
	int b = 80;
	int c = 50;
	int d = 50;
	GradientPaint overColor;
	GradientPaint exitColor;
	GradientPaint pressedColor;
	private Color startOver;
	private Color endOver;

	int h;
	String text;
	
    public void setStartAndExitColor(Color c,Color c2) {
    	this.startOver = c;
    	this.endOver = c2;
    	exitColor = new GradientPaint(0, 0, startOver, 0,
				this.getPreferredSize().height * 6, endOver);

		overColor = new GradientPaint(0, 0, startOver.darker(), 0,
				this.getPreferredSize().height * 6, endOver.brighter());

		pressedColor = new GradientPaint(0, 0, startOver, 0,
				this.getPreferredSize().height * 6, endOver.brighter().brighter());
    }
   
	public OvalButton(String text) {
		super(text);
		this.text = text;
		this.setContentAreaFilled(false);
	    setStartAndExitColor(new JButton().getBackground(),super.getBackground());//(Color.decode("0x23AB5D"),Color.decode("0x233421"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setPaint(exitColor);
		ButtonModel buttonModel = this.getModel();
        a = getPreferredSize().width;
		b = getPreferredSize().height;
		c = a / 2;
		d = c;

		if (buttonModel.isRollover()) {
			g2d.setPaint(Color.black);
			g2d.drawRoundRect(0, 0, a, b, c - 5, d - 5);

			g2d.setPaint(overColor);
			g2d.fillRoundRect(0, 0, a, b, c, d);

		} else {
			this.setBorderPainted(false);

			g2d.setPaint(exitColor);
			g2d.fillRoundRect(0, 0, a, b, c, d);
		}
		if (buttonModel.isPressed()) {
			g2d.setPaint(pressedColor);
			g2d.fillRoundRect(0, 0, a, b, c, d);
		}
		g2d.fillRoundRect(0, 0, a, b, c, d);

		g2d.setPaint(Color.orange);
		g2d.drawString(this.text, a / 4, (b * 2)  / 3);

		g2d.dispose();
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OvalButton ovb = new OvalButton("pussy");
		ovb.setPreferredSize(new Dimension(80, 40));
		OvalButton ovb2 = new OvalButton("fuck you");
		ovb2.setPreferredSize(new Dimension(100, 80));
		JPanel pane = new JPanel();
		pane.add(ovb);
		pane.add(ovb2);
		JustFrame f = new JustFrame(pane);
		f.pack();
	}

}
