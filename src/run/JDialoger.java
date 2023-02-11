package run;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import utils.LoadIcon;

public class JDialoger extends JDialog {

	private String pathToIcon;
	private ImageIcon icon;

	public JDialoger() {
		pathToIcon = "src/Images/nero_3.png";
		icon = new ImageIcon(pathToIcon);
		this.setIconImage(new LoadIcon().setIcons("nero_3.png").getImage());
	}

	public void Show() {
		this.setModal(true);
		this.pack();
		this.setJDialogLocationOnTheCenter();
		this.setVisible(true);
	}

	public void setJDialogLocationOnTheCenter() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2 - 20);
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		super.setTitle(title + " œŒ∆¿–œ–Œ“≈ “");
	}

}
