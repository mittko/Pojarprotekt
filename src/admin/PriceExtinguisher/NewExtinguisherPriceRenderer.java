package admin.PriceExtinguisher;

import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class NewExtinguisherPriceRenderer extends DefaultTableCellRenderer{
	private Color background = null;
	private Color selectedBackground = null;
	private Font priceFont = null;
	public NewExtinguisherPriceRenderer() {
		background = Color.decode("0xD8E6FF");
		selectedBackground = Color.decode("0xAAB4FF");
		priceFont = new Font(Font.DIALOG,Font.BOLD,18);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected
			,boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);
		table.setRowHeight(MainPanel.getFontSize()+15);
		if(row % 2 == 0) {
			this.setBackground(background);
		} else {
			this.setBackground(Color.white);
		}
		if(isSelected) {
			this.setBackground(selectedBackground);
			if(column == table.getSelectedColumn()) {
				this.setBackground(Color.yellow);
			}
		}
		if(column == 3) {
		this.setForeground(Color.red);
		} else {
		this.setForeground(Color.black);
		}
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(priceFont);
		return c;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
