package run;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

import utility.LoadIcon;

public class JustFrame extends JFrame {
	public int HEIGHT;
	public int WIDTH;

	private String pathToIcon;
	private ImageIcon icon;
	private int fontSize = 17;

	@Override
	public Font getFont() {
		return new Font(Font.DIALOG_INPUT, Font.BOLD, fontSize);
	}

	public JustFrame(JComponent component) {
		this();
		this.add(component);
	}

	public JustFrame() {
		HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
		WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
		UIManager.put("TabbedPane.font", getFont());
		// pathToIcon = "src/Images/nero_3.png";
		// icon = new ImageIcon(pathToIcon);
		this.setTitle("œŒ∆¿–œ–Œ“≈ “");
		this.setIconImage(new LoadIcon().setIcons("nero_3.png").getImage());// (Painter.getResizedImage(icon.getImage(),130,200).getImage());
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void setFrameLocationOnTheCenter() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2 - 15);
	}

	public void closeFrames() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
