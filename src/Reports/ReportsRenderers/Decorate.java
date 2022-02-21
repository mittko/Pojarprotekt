//package Reports.ReportsRenderers;
//
//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//
//import javax.swing.JPanel;
//
//public class Decorate extends JPanel{
//	Color startOver = null;
//	Color endOver = null;
//	public Decorate() {
//		startOver = Color.decode("0xFF3A1C");//getBackground();//Color.WHITE;
//		endOver = Color.decode("0xFFBD0A");
//	}
//	@Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        Color color1 = endOver;
//        Color color2 = startOver.brighter();
//        int w = getWidth();
//        int h = getHeight();
//        GradientPaint gp = new GradientPaint(
//            0, 0, color1, 0, h, color2);
//        g2d.setPaint(gp);
//        
//        g2d.fillRect(0, 0, w, h);
//    }
//	
//
//}
