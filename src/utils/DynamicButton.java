package utils;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DynamicButton extends JButton {

	public DynamicButton() {
	}

	public DynamicButton(String text) {
		super(text);
	}

	public void setAutoSizedIcon(JButton button, ImageIcon icon) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(button.getPreferredSize().width,
				button.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
	}

	public void setAutoSizedIcon(JButton button, int width, int height,
			ImageIcon icon) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(width, height,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
	}
}
