package utils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BevelLabel extends JLabel {

	private String title = null;
	private String name = null;
	private float labelHeight = 0;// default

	final Font newLabelFont=new Font(this.getFont().getName(),Font.ITALIC,this.getFont().getSize());
	public BevelLabel(final float labelHeight) {
		setText(getTitle());
		this.labelHeight = labelHeight;
		this.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub

				BevelLabel.this.setPreferredSize(new Dimension(
						new MainPanel().fm.stringWidth(BevelLabel.this
								.getText() + 10), (int) labelHeight));

			}

		});
	}

	public BevelLabel() {
		setText(getTitle());
	}

	public BevelLabel(String text) {
		super(text);
	}

	/*
	 * public static void setFixedSize(BevelLabel label) {
	 * label.setText(label.getText() + label.getName());
	 * label.setPreferredSize(new Dimension(new
	 * MainPanel().fm.stringWidth(label.getText()+10),50));
	 * label.setBorder(BorderFactory.createRaisedBevelBorder()); }
	 */
	// @Override
	@Override
	public String getName() {
		return this.name;
	}

	public void setTitle(String text) {
		this.title = text;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		this.setText(this.getTitle() + this.name);
	//	this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setFont(newLabelFont);
	}

	public void setAutoSizedIcon(BevelLabel label, ImageIcon icon) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(label.getPreferredSize().width,
				label.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		label.setIcon(icon);
	}
}
