/*package JPrinter.PrintSticker;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintableClass implements Printable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
	    // We have only one page, and 'page'
	    // is zero-based
	    if (page > 0) {
	         return NO_SUCH_PAGE;
	    }

	    // User (0,0) is typically outside the
	    // imageable area, so we must translate
	    // by the X and Y values in the PageFormat
	    // to avoid clipping.
	    Graphics2D g2d = (Graphics2D)graphics;
	    g2d.translate(pageFormat.getImageableX() , pageFormat.getImageableY());

	    AffineTransform original = g2d.getTransform();
	   // rotated by -90o degree (counterclockwise)
        AffineTransform at = g2d.getTransform();
        at.rotate(-Math.PI / 4);
         g2d.setTransform(at);
         // or 
       AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(2);
        
        g2d.setTransform(at2);
	    // Now we perform our rendering
         g2d.drawString("-15 XXXX  30", -15,30);
    	  g2d.drawString("1000 XXXX  50", 1000,50);
         g2d.drawString("-50 XXXX  30", -50,30);
    	  g2d.drawString("100 XXXX  500", 100,500);
         g2d.drawString("315 XXXX  300", 315,300);
    	  g2d.drawString("-50 XXXX  500", -50,500);
 	  g2d.drawString("500 XXXX  300", 500,300);
 	  g2d.drawString("100 XXXX  -150", 100,-150);
 	
	//    g2d.setTransform(original);

	    // tell the caller that this page is part
	    // of the printed document
	    return PAGE_EXISTS;
	}

}
*/