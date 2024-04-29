//package JPrinter.print;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import Exceptions.InOutException;
//
//public class DrawTextOnImage {
//
//	private BufferedImage img;
//
//	public DrawTextOnImage() {}
//
//	private BufferedImage readImage() {
//		try {
//			img = ImageIO.read(new File("C:\\Users\\mitko_lazarov\\Downloads\\test.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			InOutException.showIOException(e);
//		}
//		return img;
//	}
//	public BufferedImage process(BufferedImage old) {
//        int w = old.getWidth();
//        int h = old.getHeight();
//        BufferedImage img = new BufferedImage(
//                w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = img.createGraphics();
//        g2d.drawImage(old, 0, 0, null);
//        g2d.setPaint(Color.black);
//        g2d.setFont(new Font("Serif", Font.BOLD, 20));
//        String s = "Hello, world!";
//        FontMetrics fm = g2d.getFontMetrics();
//        int x = img.getWidth() - fm.stringWidth(s) - 5;
//        int y = fm.getHeight();
//        g2d.drawString(s, x, y);
//        g2d.dispose();
//
//        try {
//			ImageIO.write(img, "jpg", new File(
//					"C:\\Users\\mitko_lazarov\\Downloads\\result.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Image Created");
//        return img;
//    }
//	void drawImage2() {
//		BufferedImage img = readImage();
//		 Graphics graphics = img.getGraphics();
//	     //   graphics.setColor(Color.LIGHT_GRAY);
//	     //   graphics.fillRect(0, 0, 200, 50);
//		 Graphics2D g2d = (Graphics2D)graphics;
//	        graphics.setColor(Color.BLACK);
//	        graphics.setFont(new Font("Arial Black", Font.BOLD, 20));
//
//	        // rotated by 90o degree (clockwise)
//	        /*AffineTransform at =   g2d.getTransform(); //new AffineTransform();
//	        at.rotate(Math.PI / 2);
//
//	        g2d.setTransform(at);
//	        g2d.drawString("pussy", 100, -250);*/
//
//	        // rotated by -90o degree (counterclockwise)
//	        AffineTransform at = new AffineTransform();
//	        at.rotate(-Math.PI / 2);
//	         g2d.setTransform(at);
//	         // or
//	        AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(3);
//
//	        g2d.setTransform(at2);
//	        g2d.drawString("Hello World", -200, 50);
//	        try {
//				ImageIO.write(img, "jpg", new File(
//						"C:\\Users\\mitko_lazarov\\Downloads\\result2.jpg"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        System.out.println("Image Created");
//	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//     //DrawTextOnImage draw = new DrawTextOnImage();
//    // BufferedImage img = draw.readImage();
//   //  draw.drawImage2();
//	}
//
//}
