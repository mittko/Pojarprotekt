package utility;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class YourUI extends BasicScrollBarUI {
	
		@Override
		protected JButton createDecreaseButton(int orientation) {
			JButton button = super.createDecreaseButton(orientation);
			button.setBackground(Color.blue);
			return button;
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			JButton button = super.createIncreaseButton(orientation);
			button.setBackground(Color.blue);
			return button;
		}
	};	
