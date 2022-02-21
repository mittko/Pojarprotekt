package utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JToolTip;

public class CustomTooltip extends JToolTip{
  
	private Color tooltipColor = null;
	
	public CustomTooltip() {
		super();
	    this.setOpaque(false);
	    tooltipColor = super.getBackground();
	}
	
	@Override
	public void paintComponent(Graphics g)  {
		 Component parent = this.getParent( );
		 if(parent != null) {
		  if(parent instanceof JComponent) {
		   JComponent jparent = (JComponent)parent;
		   if(jparent.isOpaque( )) {
		  jparent.setOpaque(false);
		   }
		  }
		 }
		// create a round rectangle
		 Shape round = new RoundRectangle2D.Float(4,4,
		        this.getWidth( )-1-8,
		        this.getHeight( )-1-8,
		  25,25);

		 // draw the background
		 Graphics2D g2 = (Graphics2D)g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		  RenderingHints.VALUE_ANTIALIAS_ON);
		 g2.setColor(tooltipColor);//(Color.lightGray);
		 g2.fill(round);

		 // draw the gray border
		 g2.setColor(new Color(0,0,0));
		 g2.setStroke(new BasicStroke(2));
		 g2.draw(round);
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		  RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		 
		 // draw the text
		 String text = this.getComponent().getToolTipText( );
		 if(text != null) {
		           FontMetrics fm = g2.getFontMetrics( );
		           int h = fm.getAscent( );
		                    g2.setColor(Color.black);//(new Color(255,255,255));
		                    g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, MainPanel.getFontSize())); // Comic Sans MS
		                    g2.drawString(text,13,(this.getHeight( )+h)/2);
		  }
	}
	@Override
	  public Dimension getPreferredSize( ) {
		  Dimension dim = super.getPreferredSize( );
		  return new Dimension((int)dim.getWidth( )+50,
		           (int)dim.getHeight( )+20);
		 }
	
}
