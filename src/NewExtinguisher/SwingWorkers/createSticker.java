/*package NewExtinguisher.SwingWorkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import CommonResources.MainPanel;

public class createSticker extends MainPanel {

	public createSticker() {}
	
	public void writeInImage(String barcod) {
		BufferedImage buffImg = null;
		try {
		  buffImg = ImageIO.read(new File(LOGO_PATH + "\\test.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics g = buffImg.getGraphics();
		g.setColor(Color.black);
		Font font = new Font(Font.SERIF,Font.TYPE1_FONT,15);
		g.setFont(font.deriveFont(15f));
		g.drawString(barcod, 30,30);
		g.dispose();
		
		// resize image
		buffImg.getScaledInstance(50,50, Image.SCALE_DEFAULT);
		
		try {
			ImageIO.write(buffImg, "jpg", new File(STICKER_PATH  + "\\" + barcod+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
*/